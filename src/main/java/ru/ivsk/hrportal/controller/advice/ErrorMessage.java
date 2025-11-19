package ru.ivsk.hrportal.controller.advice;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record ErrorMessage(String status, OffsetDateTime timestamp, String message) {
}
