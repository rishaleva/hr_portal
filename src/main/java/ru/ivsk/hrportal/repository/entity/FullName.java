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
}
