package ru.ivsk.hrportal.controller.manager.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ManagerResponse {
    private String login;
    private String email;
    private String phone;
    private String position;
    private String firstName;
    private String lastName;
    private String middleName;
    private StatusDto status;
    private String comment;
    private Set<RoleDto> roles;

    @Data
    public static class StatusDto {
        private String name;
        private String code;
    }

    @Data
    public static class RoleDto {
        private String name;
        private String code;
    }
}