package ru.ivsk.hrportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivsk.hrportal.repository.entity.Scope;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ScopeRepository extends JpaRepository<Scope, Long> {

    List<Scope> findByCodeIn(Collection<String> codes);

    Optional<Scope> findByCode(String code);

}