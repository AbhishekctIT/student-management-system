package com.platformcommons.student_management_system.studentms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T>{
    private boolean success;
    private String message;
    private T data;
    private String errors;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message("Operation successful")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> error(String errorCode, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .errors(errorCode)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> validationError(T errors) {
        return ApiResponse.<T>builder()
                .success(false)
                .message("Validation failed")
                .errors((String) errors)
                .build();
    }

    // Alternative validation error with custom message
    public static <T> ApiResponse<T> validationError(String message, T errors) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .errors((String) errors)
                .build();
    }
}
