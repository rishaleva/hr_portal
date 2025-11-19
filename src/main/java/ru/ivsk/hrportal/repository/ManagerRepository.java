package ru.ivsk.hrportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivsk.hrportal.repository.entity.Manager;

import java.util.Optional;

public interface ManagerRepository 
        extends JpaRepository<Manager, Long> {

    Optional<Manager> findByLogin(String login);

}