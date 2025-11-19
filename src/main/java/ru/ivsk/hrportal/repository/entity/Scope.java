package ru.ivsk.hrportal.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "scope")
public class Scope extends BaseEntity{

    @Column
    private String name;

    @Column
    private String uid;

    @Column
    private String code;
}
