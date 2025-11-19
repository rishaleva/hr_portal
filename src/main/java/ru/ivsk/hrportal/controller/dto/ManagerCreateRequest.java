package ru.ivsk.hrportal.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerCreateRequest {

    @NotBlank(message = "Фамилия обязательна")
    private String lastName;

    @NotBlank(message = "Имя обязательно")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Должность обязательна")
    private String position;

    @NotBlank(message = "Учетная запись (login) обязательна")
    private String login;

    @NotBlank(message = "Электронная почта обязательна")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$", message = "Некорректный email")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{1,15}$", message = "Телефон должен содержать только цифры и начинаться с +")
    private String phone;

    private String comment;

    @NotBlank(message = "Роль обязательна")
    private String roleCode;
}