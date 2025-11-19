package ru.ivsk.hrportal.controller.scope;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ivsk.hrportal.controller.scope.dto.ScopesResponseDto;
import ru.ivsk.hrportal.service.ScopeService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScopeController {

    private final ScopeService scopeService;
    @GetMapping("/scopes")
    public ResponseEntity<ScopesResponseDto> getRoles() {
        return ResponseEntity.ok().body(scopeService.getScopes());
    }
}
