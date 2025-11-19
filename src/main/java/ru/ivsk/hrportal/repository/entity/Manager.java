package ru.ivsk.hrportal.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "manager")
public class Manager extends BaseEntity {

    @Column(name = "uid", nullable = false, updatable = false)
    private String uid;

    private String login;
    private String email; 
    private String phone;
    private String position;
    @Embedded
    private FullName fullName;

    @OneToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @PrePersist
    protected void initUid() {
        this.uid = UUID.randomUUID().toString();
    }
    }
