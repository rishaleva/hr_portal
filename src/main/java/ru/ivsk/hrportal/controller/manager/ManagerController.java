package ru.ivsk.hrportal.controller.manager;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivsk.hrportal.controller.advice.BaseController;
import ru.ivsk.hrportal.controller.manager.dto.ManagerResponse;
import ru.ivsk.hrportal.controller.manager.dto.ManagersCreateRequest;
import ru.ivsk.hrportal.controller.manager.dto.ManagersResponse;
import ru.ivsk.hrportal.mapper.ManagerMapper;
import ru.ivsk.hrportal.repository.entity.Manager;
import ru.ivsk.hrportal.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ManagerController extends BaseController {

    private final ManagerService managerService;
    private final ManagerMapper managerMapper;

    @PostMapping("/managers")
    public ResponseEntity<Void> createManagersBulk(
            @Valid @RequestBody ManagersCreateRequest request) {
        managerService.createManagers(request);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/managers")
    public ResponseEntity<ManagersResponse> getManagers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Manager> managersPage = managerService.getManagers(pageable);

        List<ManagerResponse> managerResponses = managersPage.getContent()
                .stream()
                .map(managerMapper::toManagerResponse)
                .toList();

        ManagersResponse.PageMetadata pageMetadata = ManagersResponse.PageMetadata.from(managersPage);

        return ResponseEntity.ok(new ManagersResponse(managerResponses, pageMetadata));
    }

}