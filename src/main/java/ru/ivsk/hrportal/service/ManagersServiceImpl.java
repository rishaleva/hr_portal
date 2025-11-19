package ru.ivsk.hrportal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivsk.hrportal.controller.manager.dto.ManagerCreateRequest;
import ru.ivsk.hrportal.controller.manager.dto.ManagersCreateRequest;
import ru.ivsk.hrportal.exception.ManagerAlreadyExistsException;
import ru.ivsk.hrportal.exception.ResourceNotFoundException;
import ru.ivsk.hrportal.repository.ManagerRepository;
import ru.ivsk.hrportal.repository.RoleRepository;
import ru.ivsk.hrportal.repository.StatusRepository;
import ru.ivsk.hrportal.repository.entity.FullName;
import ru.ivsk.hrportal.repository.entity.Manager;
import ru.ivsk.hrportal.repository.entity.Role;
import ru.ivsk.hrportal.repository.entity.Status;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagersServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private static final String activeStatusCode = "ACTIVE";

    @Override
    @Transactional
    public void createManagers(ManagersCreateRequest request) {

        Status defaultStatus = statusRepository.findByCode(activeStatusCode)
                .orElseThrow(() -> new ResourceNotFoundException("Статус %s не найден", activeStatusCode));

        validateLoginsNotExist(request);
        validateNoDuplicateLoginsInRequest(request);
        List<Manager> managersToSave = new ArrayList<>();

        for (ManagerCreateRequest man : request.getManagers()) {
            Manager manager = new Manager();
            manager.setLogin(man.getLogin().trim());
            manager.setEmail(man.getEmail());
            manager.setPhone(man.getPhone());
            manager.setPosition(man.getPosition());
            manager.setComment(man.getComment());

            FullName fullName = new FullName();
            fullName.setFirstName(man.getFirstName());
            fullName.setLastName(man.getLastName());
            fullName.setMiddleName(man.getMiddleName());
            manager.setFullName(fullName);

            Role role = roleRepository.findByCode(man.getRoleCode())
                    .orElseThrow(() -> new ResourceNotFoundException("Роль %s не найдена для логина %s",
                            man.getRoleCode(), man.getLogin()));

            manager.setStatus(defaultStatus);
            manager.setRole(role);

            managersToSave.add(manager);
        }

        managerRepository.saveAll(managersToSave);
    }

    private void validateNoDuplicateLoginsInRequest(ManagersCreateRequest request) {
        List<String> logins = request.getManagers().stream()
                .map(ManagerCreateRequest::getLogin)
                .map(String::trim)
                .toList();

        Set<String> uniqueLogins = new HashSet<>(logins);

        if (logins.size() != uniqueLogins.size()) {
            Set<String> duplicates = logins.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());

            throw new IllegalArgumentException("Обнаружены дублирующиеся логины в запросе: " + duplicates);
        }
    }

    private void validateLoginsNotExist(ManagersCreateRequest request) {
        List<String> requestedLogins = request.getManagers().stream()
                .map(ManagerCreateRequest::getLogin)
                .map(String::trim)
                .toList();

        List<String> existingLogins = managerRepository.findExistingLogins(requestedLogins);

        if (!existingLogins.isEmpty()) {
            throw new ManagerAlreadyExistsException("Менеджеры с логинами %s уже существуют!", existingLogins);
        }
    }
}
