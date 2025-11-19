package ru.ivsk.hrportal.mapper;

import org.mapstruct.Mapper;
import ru.ivsk.hrportal.controller.scope.dto.ScopeDto;
import ru.ivsk.hrportal.repository.entity.Scope;

@Mapper(componentModel = "spring")
public interface ScopeMapper {

    ScopeDto toDto(Scope scope);
}
