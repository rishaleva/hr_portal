package ru.ivsk.hrportal.service;

import ru.ivsk.hrportal.controller.role.dto.RolesResponseDto;
import ru.ivsk.hrportal.controller.scope.dto.ScopesResponseDto;

public interface ScopeService {

    ScopesResponseDto getScopes();
}
