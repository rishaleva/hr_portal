package ru.ivsk.hrportal.controller.manager.dto;

import jakarta.validation.constraints.NotBlank;

public class HrManagerDto {
    @NotBlank(message = "Логин менеджера не может быть пустым или null")
    private String login;
    @NotBlank(message = "Имя менеджера не может быть пустым или null")
    String firstName;
    @NotBlank(message = "Фамилия менеджера не может быть пустым или null")
    String lastName;
    String middleName;
    private String email; 
    private String phone;
    private String position;
    private HrManagerStatus status; // enum: ACTIVE, INACTIVE
    private String comment;
    
    }