package com.lee.api.exception;

import com.lee.api.model.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 处理 @Valid 参数校验失败的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 获取所有字段的错误信息，并用逗号拼接
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        
        return ApiResponse.error(errorMessage);
    }

    // 2. 处理所有其他未知的异常 (兜底方案)
    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleAllExceptions(Exception ex) {
        return ApiResponse.error("Server Error: " + ex.getMessage());
    }
}
