package ru.ivsk.hrportal.repository.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class FullName {

    @NotBlank(message = "Имя менеджера не может быть пустым или null")
    String firstName;

    @NotBlank(message = "Фамилия менеджера не может быть пустым или null")
    String lastName;

    String middleName;

    /**
     * Возвращает полное имя в формате "Фамилия Имя Отчество"
     * Если отчество отсутствует, возвращает "Фамилия Имя"
     *
     * @return отформатированное полное имя
     */
    public String getFullName() {
        if (lastName == null || firstName == null) {
            return "";
        }

        StringBuilder fullName = new StringBuilder();
        fullName.append(lastName.trim());
        fullName.append(" ");
        fullName.append(firstName.trim());

        if (middleName != null && !middleName.trim().isEmpty()) {
            fullName.append(" ");
            fullName.append(middleName.trim());
        }

        return fullName.toString();
    }
}
