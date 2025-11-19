package ru.ivsk.hrportal.controller.role;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ivsk.hrportal.controller.advice.BaseController;
import ru.ivsk.hrportal.controller.role.dto.RolesResponseDto;
import ru.ivsk.hrportal.service.RoleService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoleController extends BaseController {

    private final RoleService roleService;
    @GetMapping("/roles")
    public ResponseEntity<RolesResponseDto> getRoles() {
        return ResponseEntity.ok().body(roleService.getRoles());
    }
}
