package ru.ivsk.hrportal.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "hibernate_sequence_generator"
    )
    @SequenceGenerator(
        name = "hibernate_sequence_generator",
        sequenceName = "hibernate_sequence",
        allocationSize = 1
    )
    private Long id;

}