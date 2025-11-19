package ru.ivsk.hrportal.controller.advice;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.ivsk.hrportal.exception.ResourceNotFoundException;

import java.time.OffsetDateTime;

/**
 * Класс реализует централизованную обработку исключений, возникающих в REST-контроллере.
 */
@Slf4j
@RestControllerAdvice
public abstract class BaseController {

    public static final String INTERNAL_ERROR_STATUS = "INTERNAL_SERVER_ERROR";
    public static final String INTERNAL_ERROR_MESSAGE = "Внутренняя ошибка сервера";
    public static final String BAD_REQUEST_STATUS = "BAD_REQUEST";
    public static final String BAD_REQUEST_MESSAGE = "Некорректный запрос";
    public static final String NOT_FOUND_STATUS = "NOT_FOUND";
    public static final String NOT_FOUND_MESSAGE = "Запрашиваемый ресурс не найден";


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
            EntityNotFoundException.class,
            MissingServletRequestParameterException.class
    })
    @ApiResponse(responseCode = "400", description = "Ошибки со стороны запроса",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    public ErrorMessage handleBadRequest(Exception ex) {
        log.error(BAD_REQUEST_MESSAGE, ex);

        return ErrorMessage.builder()
                .status(BAD_REQUEST_STATUS)
                .timestamp(OffsetDateTime.now())
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            Exception.class,
    })
    @ApiResponse(responseCode = "500", description = "Ошибки со стороны сервера во время обработки запроса",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    public ErrorMessage handleInternalError(Exception ex) {

        log.error(INTERNAL_ERROR_MESSAGE, ex);

        return ErrorMessage.builder()
                .status(INTERNAL_ERROR_STATUS)
                .timestamp(OffsetDateTime.now())
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Данные не найдены",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    public ErrorMessage handleNotFound(ResourceNotFoundException ex) {

        log.error(NOT_FOUND_MESSAGE, ex);

        return ErrorMessage.builder()
                .status(NOT_FOUND_STATUS)
                .timestamp(OffsetDateTime.now())
                .message(ex.getMessage())
                .build();
    }
}