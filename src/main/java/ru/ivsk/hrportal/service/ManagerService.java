package ru.ivsk.hrportal.service;

import ru.ivsk.hrportal.controller.manager.dto.ManagersCreateRequest;

public interface ManagerService {

    void createManagers(ManagersCreateRequest request);
}
