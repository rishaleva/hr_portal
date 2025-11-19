package ru.ivsk.hrportal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivsk.hrportal.controller.access.dto.AccessDto;
import ru.ivsk.hrportal.controller.access.dto.AccessRequestDto;
import ru.ivsk.hrportal.controller.access.dto.AccessResponseDto;
import ru.ivsk.hrportal.controller.access.dto.ManagerLightDto;
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