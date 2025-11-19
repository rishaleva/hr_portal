package ru.ivsk.hrportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.ivsk.hrportal.controller.manager.dto.ManagerResponse;
import ru.ivsk.hrportal.repository.entity.Manager;
import ru.ivsk.hrportal.repository.entity.Role;
import ru.ivsk.hrportal.repository.entity.Status;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ManagerMapper {

    @Mapping(source = "fullName.firstName", target = "firstName")
    @Mapping(source = "fullName.lastName", target = "lastName")
    @Mapping(source = "fullName.middleName", target = "middleName")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "role", target = "role")
    ManagerResponse toManagerResponse(Manager manager);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "code", target = "code")
    ManagerResponse.StatusDto toStatusDto(Status status);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "code", target = "code")
    ManagerResponse.RoleDto toRoleDto(Role role);
}