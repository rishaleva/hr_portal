package ru.ivsk.hrportal.service;

import ru.ivsk.hrportal.controller.access.dto.AccessDto;
import ru.ivsk.hrportal.controller.access.dto.AccessRequestDto;
import ru.ivsk.hrportal.controller.access.dto.AccessResponseDto;
import ru.ivsk.hrportal.controller.access.dto.ManagerAccessResponseDto;

import java.util.List;

public interface AccessService {

    AccessResponseDto createManagersAccess(AccessRequestDto request);

    ManagerAccessResponseDto getManagerAccess(String login);

    List<ManagerAccessResponseDto> getMultipleManagersAccess(List<String> logins);

    List<AccessDto> getManagerAccessByScope(String login, String scopeCode);

    boolean hasManagerAccess(String login, String scopeCode, String value);
}
