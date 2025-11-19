package ru.ivsk.hrportal.controller.manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerCreateRequest {

    @NotBlank(message = "Имя обязательно")
    @Schema(description = "Имя пользователя", example = "Иван")
    private String firstName;

    @NotBlank(message = "Фамилия обязательна")
    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String lastName;

    @Schema(description = "Отчество пользователя", example = "Иванович")
    private String middleName;

    @NotBlank(message = "Должность обязательна")
    @Schema(description = "Должность пользователя", example = "Начальник отдела")
    private String position;

    @NotBlank(message = "Учетная запись (login) обязательна")
    @Schema(description = "Логин", example = "iivanov")
    private String login;

    @NotBlank(message = "Электронная почта обязательна")
    @Schema(description = "Электронная почта", example = "iivanov@example.com")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$", message = "Некорректный email")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{1,15}$", message = "Телефон должен содержать только цифры и начинаться с +")
    @Schema(description = "Телефон", example = "+71234567890")
    private String phone;

    @Schema(description = "Комментарий к менеджеру", example = "Временный сотрудник")
    private String comment;

    @NotBlank(message = "Роль обязательна")
    @Schema(description = "Код роли", example = "HR")
    private String roleCode;
}