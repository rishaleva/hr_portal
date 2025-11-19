package ru.ivsk.hrportal.repository.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //если хотим батчинг, то сиквенс
    private Long id;
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
    }