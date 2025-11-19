package ru.ivsk.hrportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivsk.hrportal.repository.entity.ManagerAccess;

public interface ManagerAccessRepository extends JpaRepository<ManagerAccess, Long> {

    boolean existsByManagerIdAndScopeIdAndValue(Long managerId, Long scopeId, String value);
}
