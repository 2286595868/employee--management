package com.example.employee.exception;

import com.example.employee.common.ApiResponse;
import com.example.employee.common.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, ErrorCode.PARAM_ERROR, emptyToDefault(message, ErrorCode.PARAM_ERROR.getMessage()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Void>> handleBindException(BindException exception) {
        String message = exception.getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, ErrorCode.PARAM_ERROR, emptyToDefault(message, ErrorCode.PARAM_ERROR.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, ErrorCode.PARAM_ERROR, emptyToDefault(message, ErrorCode.PARAM_ERROR.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameter(MissingServletRequestParameterException exception) {
        String message = exception.getParameterName() + "不能为空";
        return buildResponse(HttpStatus.BAD_REQUEST, ErrorCode.PARAM_ERROR, message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        String message = exception.getName() + "参数格式错误";
        return buildResponse(HttpStatus.BAD_REQUEST, ErrorCode.PARAM_ERROR, message);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException exception) {
        HttpStatus status = exception.getErrorCode() == ErrorCode.NOT_FOUND ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        return buildResponse(status, exception.getErrorCode(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
        log.error("Unhandled backend exception type={}", exception.getClass().getName(), exception);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.SYSTEM_ERROR, ErrorCode.SYSTEM_ERROR.getMessage());
    }

    private ResponseEntity<ApiResponse<Void>> buildResponse(HttpStatus status, ErrorCode errorCode, String message) {
        return ResponseEntity.status(status).body(ApiResponse.failure(errorCode, message));
    }

    private String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + emptyToDefault(fieldError.getDefaultMessage(), ErrorCode.PARAM_ERROR.getMessage());
    }

    private String emptyToDefault(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }
}
