package ru.ivsk.hrportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivsk.hrportal.repository.entity.Scope;

public interface ScopeRepository extends JpaRepository<Scope, Long> {

}