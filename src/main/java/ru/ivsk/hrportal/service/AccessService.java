package ru.ivsk.hrportal.service;

import ru.ivsk.hrportal.controller.access.dto.AccessRequestDto;
import ru.ivsk.hrportal.controller.access.dto.AccessResponseDto;

public interface AccessService {

    AccessResponseDto createManagersAccess(AccessRequestDto request);
}
