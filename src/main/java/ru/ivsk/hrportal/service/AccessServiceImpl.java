package ru.ivsk.hrportal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivsk.hrportal.controller.access.dto.*;
import ru.ivsk.hrportal.repository.ManagerAccessRepository;
import ru.ivsk.hrportal.repository.ManagerRepository;
import ru.ivsk.hrportal.repository.ScopeRepository;
import ru.ivsk.hrportal.repository.entity.Manager;
import ru.ivsk.hrportal.repository.entity.ManagerAccess;
import ru.ivsk.hrportal.repository.entity.Scope;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {

    private final ManagerRepository managerRepository;
    private final ScopeRepository scopeRepository;
    private final ManagerAccessRepository accessRepository;

    @Override
    @Transactional
    public AccessResponseDto createManagersAccess(AccessRequestDto request) {

        validateRequest(request);

        List<Manager> managers = managerRepository.findByLoginIn(request.logins());
        Map<String, Manager> loginToManager = managers.stream()
                .collect(Collectors.toMap(Manager::getLogin, Function.identity()));

        validateManagersFound(request.logins(), loginToManager);

        Set<String> scopeCodes = request.access().stream()
                .map(AccessDto::scopeCode)
                .collect(Collectors.toSet());
        List<Scope> scopes = scopeRepository.findByCodeIn(scopeCodes);
        Map<String, Scope> codeToScope = scopes.stream()
                .collect(Collectors.toMap(Scope::getCode, Function.identity()));

        validateScopesFound(scopeCodes, codeToScope);


        List<ManagerAccess> newAccesses = new ArrayList<>();

        for (String login : request.logins()) {
            Manager manager = loginToManager.get(login);
            for (AccessDto accessDto : request.access()) {
                Scope scope = codeToScope.get(accessDto.scopeCode());
                for (String objectUid : accessDto.uuids()) {
                    boolean exists = accessRepository.existsByManagerIdAndScopeIdAndValue(
                            manager.getId(), scope.getId(), objectUid);
                    if (!exists) {
                        ManagerAccess access = new ManagerAccess();
                        access.setManager(manager);
                        access.setScope(scope);
                        access.setValue(objectUid);
                        access.setUid(UUID.randomUUID().toString());
                        newAccesses.add(access);
                    }
                }
            }
        }

        if (!newAccesses.isEmpty()) {
            accessRepository.saveAll(newAccesses);
        }

        return buildResponse(request, loginToManager);

    }

    private AccessResponseDto buildResponse(AccessRequestDto request,
                                            Map<String, Manager> loginToManager) {

        List<ManagerLightDto> managerLogins = request.logins().stream()
                .map(login -> {
                    Manager manager = loginToManager.get(login);
                    return new ManagerLightDto(
                            manager.getLogin(),
                            manager.getFullName().getFullName(),
                            manager.getRole().getCode()
                    );
                })
                .toList();

        List<AccessDto> accessToGrant = new ArrayList<>(request.access());

        return new AccessResponseDto(managerLogins, accessToGrant);
    }


    @Override
    @Transactional(readOnly = true)
    public ManagerAccessResponseDto getManagerAccess(String login) {

        Manager manager = managerRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Менеджер не найден по логину: " + login));

        List<ManagerAccess> managerAccesses = accessRepository.findByManagerId(manager.getId());

        Map<String, List<String>> accessesByScope = managerAccesses.stream()
                .collect(Collectors.groupingBy(
                        access -> access.getScope().getCode(),
                        Collectors.mapping(ManagerAccess::getValue, Collectors.toList())
                ));

        List<AccessDto> accessList = accessesByScope.entrySet().stream()
                .map(entry -> new AccessDto(entry.getKey(), entry.getValue(), entry.getValue().size()))
                .toList();

        ManagerLightDto managerDto = new ManagerLightDto(
                manager.getLogin(),
                manager.getFullName().getFullName(),
                manager.getRole().getCode()
        );

        return new ManagerAccessResponseDto(managerDto, accessList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManagerAccessResponseDto> getMultipleManagersAccess(List<String> logins) {

        if (logins == null || logins.isEmpty()) {
            throw new IllegalArgumentException("Список логинов не может быть пустым");
        }

        List<Manager> managers = managerRepository.findByLoginIn(logins);
        Map<String, Manager> loginToManager = managers.stream()
                .collect(Collectors.toMap(Manager::getLogin, Function.identity()));

        validateManagersFound(logins, loginToManager);

        Set<Long> managerIds = managers.stream()
                .map(Manager::getId)
                .collect(Collectors.toSet());

        List<ManagerAccess> allAccesses = accessRepository.findByManagerIdIn(managerIds);

        Map<Long, List<ManagerAccess>> accessesByManagerId = allAccesses.stream()
                .collect(Collectors.groupingBy(access -> access.getManager().getId()));

        return logins.stream()
                .map(login -> {
                    Manager manager = loginToManager.get(login);
                    List<ManagerAccess> managerAccesses = accessesByManagerId.getOrDefault(
                            manager.getId(), Collections.emptyList());
                    Map<String, List<String>> accessesByScope = managerAccesses.stream()
                            .collect(Collectors.groupingBy(
                                    access -> access.getScope().getCode(),
                                    Collectors.mapping(ManagerAccess::getValue, Collectors.toList())
                            ));

                    List<AccessDto> accessList = accessesByScope.entrySet().stream()
                            .map(entry -> new AccessDto(entry.getKey(), entry.getValue(), entry.getValue().size()))
                            .toList();

                    ManagerLightDto managerDto = new ManagerLightDto(
                            manager.getLogin(),
                            manager.getFullName().getFullName(),
                            manager.getRole().getCode()
                    );

                    return new ManagerAccessResponseDto(managerDto, accessList);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccessDto> getManagerAccessByScope(String login, String scopeCode) {

        Manager manager = managerRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Менеджер не найден по логину: " + login));

        Scope scope = scopeRepository.findByCode(scopeCode)
                .orElseThrow(() -> new IllegalArgumentException("Scope не найден по коду: " + scopeCode));

        List<ManagerAccess> accesses = accessRepository.findByManagerIdAndScopeId(
                manager.getId(), scope.getId());

        List<String> values = accesses.stream()
                .map(ManagerAccess::getValue)
                .toList();

        return values.isEmpty()
                ? Collections.emptyList()
                : List.of(new AccessDto(scopeCode, values, values.size()));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasManagerAccess(String login, String scopeCode, String value) {

        Manager manager = managerRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Менеджер не найден по логину: " + login));

        Scope scope = scopeRepository.findByCode(scopeCode)
                .orElseThrow(() -> new IllegalArgumentException("Scope не найден по коду: " + scopeCode));

        return accessRepository.existsByManagerIdAndScopeIdAndValue(
                manager.getId(), scope.getId(), value);
    }


    private void validateRequest(AccessRequestDto request) {
        if (request.logins() == null || request.logins().isEmpty()) {
            throw new IllegalArgumentException("Список логинов не может быть пустым");
        }
        if (request.access() == null || request.access().isEmpty()) {
            throw new IllegalArgumentException("Список доступов не может быть пустым");
        }
        for (AccessDto dto : request.access()) {
            if (dto.scopeCode() == null || dto.scopeCode().isBlank()) {
                throw new IllegalArgumentException("scopeCode не может быть пустым");
            }
            if (dto.uuids() == null || dto.uuids().isEmpty()) {
                throw new IllegalArgumentException("Список uuids не может быть пустым для scope: " + dto.scopeCode());
            }
        }
    }

    private void validateManagersFound(List<String> requestedLogins,
                                       Map<String, Manager> foundManagers) {
        List<String> notFoundLogins = requestedLogins.stream()
                .filter(login -> !foundManagers.containsKey(login))
                .toList();
        if (!notFoundLogins.isEmpty()) {
            throw new IllegalArgumentException("Менеджеры не найдены по логинам: " + notFoundLogins);
        }
    }

    private void validateScopesFound(Set<String> requestedCodes,
                                     Map<String, Scope> foundScopes) {
        List<String> notFoundScopes = requestedCodes.stream()
                .filter(code -> !foundScopes.containsKey(code))
                .toList();
        if (!notFoundScopes.isEmpty()) {
            throw new IllegalArgumentException("Scope не найдены по кодам: " + notFoundScopes);
        }
    }
}