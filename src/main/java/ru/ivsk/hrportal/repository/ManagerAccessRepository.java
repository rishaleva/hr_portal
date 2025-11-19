package ru.ivsk.hrportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ivsk.hrportal.repository.entity.ManagerAccess;

import java.util.List;
import java.util.Set;

public interface ManagerAccessRepository extends JpaRepository<ManagerAccess, Long> {

    boolean existsByManagerIdAndScopeIdAndValue(Long managerId, Long scopeId, String value);

    List<ManagerAccess> findByManagerId(Long managerId);

    List<ManagerAccess> findByManagerIdIn(Set<Long> managerIds);

    List<ManagerAccess> findByManagerIdAndScopeId(Long managerId, Long scopeId);

    @Query("""
        SELECT ma FROM ManagerAccess ma 
        JOIN FETCH ma.manager m 
        JOIN FETCH ma.scope s 
        WHERE m.login = :login
        """)
    List<ManagerAccess> findByManagerLoginWithFetch(@Param("login") String login);

    @Query("""
        SELECT ma FROM ManagerAccess ma 
        JOIN FETCH ma.manager m 
        JOIN FETCH ma.scope s 
        WHERE m.login IN :logins
        """)
    List<ManagerAccess> findByManagerLoginsWithFetch(@Param("logins") List<String> logins);
}
