package ru.ivsk.hrportal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.ivsk.hrportal.controller.manager.dto.ManagersCreateRequest;
import ru.ivsk.hrportal.repository.entity.Manager;

public interface ManagerService {

    void createManagers(ManagersCreateRequest request);

    Page<Manager> getManagers(Pageable pageable);
}
