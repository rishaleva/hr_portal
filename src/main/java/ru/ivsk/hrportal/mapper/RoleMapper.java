package ru.ivsk.hrportal.mapper;

import org.mapstruct.Mapper;
import ru.ivsk.hrportal.controller.role.dto.RoleDto;
import ru.ivsk.hrportal.repository.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toDto(Role role);
}
