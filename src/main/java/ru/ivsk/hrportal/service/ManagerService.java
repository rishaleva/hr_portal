package ru.ivsk.hrportal.service;

import ru.ivsk.hrportal.controller.dto.ManagersCreateRequest;

public interface ManagerService {

    void createManagers(ManagersCreateRequest request);
}
