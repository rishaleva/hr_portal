package ru.ivsk.hrportal.controller.manager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ManagersCreateRequest {

    private List<ManagerCreateRequest> managers;
    }