package ru.ivsk.hrportal.controller.manager.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
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
    private RoleDto role;

    @Getter
    @Setter
    public static class StatusDto {
        private String name;
        private String code;
    }

    @Getter
    @Setter
    public static class RoleDto {
        private String name;
        private String code;
    }
}