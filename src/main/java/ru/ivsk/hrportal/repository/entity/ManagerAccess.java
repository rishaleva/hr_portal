package ru.ivsk.hrportal.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "manager_access")
public class ManagerAccess extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "scope_id")
    private Scope scope;

    @Column(name = "value")
    private String value;

    @Column
    private String uid;

}
