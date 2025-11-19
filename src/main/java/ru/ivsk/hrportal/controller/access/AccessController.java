package ru.ivsk.hrportal.controller.access;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivsk.hrportal.controller.access.dto.AccessDto;
import ru.ivsk.hrportal.controller.access.dto.AccessRequestDto;
import ru.ivsk.hrportal.controller.access.dto.AccessResponseDto;
import ru.ivsk.hrportal.controller.access.dto.ManagerAccessResponseDto;
import ru.ivsk.hrportal.service.AccessService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccessController {

    private final AccessService accessService;
    @PostMapping("/access")
    public ResponseEntity<AccessResponseDto> createManagerAccess(
            @Valid @RequestBody AccessRequestDto request) {
       var result = accessService.createManagersAccess(request);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/manager-access")
    public List<ManagerAccessResponseDto> getMultipleManagersAccess(
            @RequestBody List<String> logins) {
        return accessService.getMultipleManagersAccess(logins);
    }

    @GetMapping("/manager-access/{login}/scope/{scopeCode}")
    public List<AccessDto> getManagerAccessByScope(
            @PathVariable String login,
            @PathVariable String scopeCode) {
        return accessService.getManagerAccessByScope(login, scopeCode);
    }

    @GetMapping("/manager-access/{login}/scope/{scopeCode}/value/{value}")
    public boolean hasManagerAccess(
            @PathVariable String login,
            @PathVariable String scopeCode,
            @PathVariable String value) {
        return accessService.hasManagerAccess(login, scopeCode, value);
    }
}
