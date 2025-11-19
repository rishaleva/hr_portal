package ru.ivsk.hrportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ivsk.hrportal.repository.entity.Manager;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ManagerRepository 
        extends JpaRepository<Manager, Long> {

    Optional<Manager> findByLogin(String login);

    List<Manager> findByLoginIn(Collection<String> logins);

    @Query("SELECT m.login FROM Manager m WHERE m.login IN :logins")
    List<String> findExistingLogins(@Param("logins") List<String> logins);

}