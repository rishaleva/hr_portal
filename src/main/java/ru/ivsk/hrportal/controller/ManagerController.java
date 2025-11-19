package ru.ivsk.hrportal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ivsk.hrportal.controller.dto.ManagersCreateRequest;
import ru.ivsk.hrportal.service.ManagerService;

@RestController
@RequestMapping("/api/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/manager")
    public ResponseEntity<Void> createManagersBulk(
            @Valid @RequestBody ManagersCreateRequest request) {
        managerService.createManagers(request);
        return ResponseEntity.status(201).build();
    }
}