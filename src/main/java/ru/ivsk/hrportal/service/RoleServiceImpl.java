package ru.ivsk.hrportal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ivsk.hrportal.controller.role.dto.RoleDto;
import ru.ivsk.hrportal.controller.role.dto.RolesResponseDto;
import ru.ivsk.hrportal.mapper.RoleMapper;
import ru.ivsk.hrportal.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    @Override
    public RolesResponseDto getRoles() {
       List<RoleDto> roles = roleRepository.findAll()
               .stream()
               .map(roleMapper::toDto)
               .toList();
        return new RolesResponseDto(roles);
    }
}
