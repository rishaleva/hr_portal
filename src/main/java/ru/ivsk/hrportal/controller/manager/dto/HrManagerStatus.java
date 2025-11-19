package ru.ivsk.hrportal.controller.manager.dto;

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