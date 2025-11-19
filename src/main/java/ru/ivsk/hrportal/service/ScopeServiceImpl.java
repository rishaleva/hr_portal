package ru.ivsk.hrportal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ivsk.hrportal.controller.scope.dto.ScopeDto;
import ru.ivsk.hrportal.controller.scope.dto.ScopesResponseDto;
import ru.ivsk.hrportal.mapper.ScopeMapper;
import ru.ivsk.hrportal.repository.ScopeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScopeServiceImpl implements ScopeService {

    private final ScopeRepository scopeRepository;
    private final ScopeMapper scopeMapper;

    @Override
    public ScopesResponseDto getScopes() {
        List<ScopeDto> scopes = scopeRepository.findAll()
                .stream()
                .map(scopeMapper::toDto)
                .toList();
        return new ScopesResponseDto(scopes);
    }
}
