package ru.ivsk.hrportal.controller.access.dto;

import java.util.List;

public record AccessResponseDto(List<ManagerLightDto> managerLogins, List<AccessDto> accessToGrant) {
}
