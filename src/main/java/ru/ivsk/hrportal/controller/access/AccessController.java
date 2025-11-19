package ru.ivsk.hrportal.controller.access;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ivsk.hrportal.controller.access.dto.AccessRequestDto;
import ru.ivsk.hrportal.controller.access.dto.AccessResponseDto;
import ru.ivsk.hrportal.service.AccessService;

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
}
