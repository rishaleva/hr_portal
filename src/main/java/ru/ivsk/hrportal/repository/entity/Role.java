package ru.ivsk.hrportal.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role extends BaseEntity{

    @Column
    private String name;

    @Column
    private String code;
}
