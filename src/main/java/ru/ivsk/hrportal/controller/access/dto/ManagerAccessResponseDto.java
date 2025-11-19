package ru.ivsk.hrportal.controller.access.dto;

import java.util.List;

public record ManagerAccessResponseDto(
        ManagerLightDto manager,
        List<AccessDto> accesses
) {}