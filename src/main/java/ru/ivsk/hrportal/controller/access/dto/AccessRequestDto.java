package ru.ivsk.hrportal.controller.access.dto;

import java.util.List;
import java.util.Map;

public record AccessRequestDto(List<String> logins, List<AccessDto> access) {
}
