package ru.ivsk.hrportal.controller.access.dto;

import java.util.List;

public record AccessDto(String scopeCode, List<String> uuids, int totalSum) {
}
