package ru.ivsk.hrportal.controller.dto;

import lombok.Getter;

@Getter
public enum HrManagerStatus {

    ACTIVE("Активный"),
    INACTIVE("Неактивный");

    private final String description;

    HrManagerStatus(String description) {
        this.description = description;
    }
}