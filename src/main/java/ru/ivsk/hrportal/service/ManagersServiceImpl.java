package ru.ivsk.hrportal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivsk.hrportal.controller.manager.dto.ManagerCreateRequest;
import ru.ivsk.hrportal.controller.manager.dto.ManagersCreateRequest;
import ru.ivsk.hrportal.exception.ResourceNotFoundException;
import ru.ivsk.hrportal.repository.ManagerRepository;
import ru.ivsk.hrportal.repository.RoleRepository;
import ru.ivsk.hrportal.repository.StatusRepository;
import ru.ivsk.hrportal.repository.entity.FullName;
import ru.ivsk.hrportal.repository.entity.Manager;
import ru.ivsk.hrportal.repository.entity.Role;
import ru.ivsk.hrportal.repository.entity.Status;

import java.util.ArrayList;
import java.util.List;

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
}
