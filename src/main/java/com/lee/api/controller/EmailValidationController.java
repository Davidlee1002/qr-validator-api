package com.lee.api.controller;

import com.lee.api.model.ApiResponse;
import com.lee.api.model.EmailValidationRequest;
import com.lee.api.model.EmailValidationResponse;
import com.lee.api.service.EmailValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/validation/email")
@RequiredArgsConstructor
@Tag(name = "Email Validation", description = "Deep validation for email addresses")
public class EmailValidationController {

    private final EmailValidationService emailValidationService;

    @PostMapping
    @Operation(summary = "Validate Email Address", description = "Checks format, MX records, disposable domains, and role accounts.")
    public ApiResponse<EmailValidationResponse> validateEmail(@RequestBody @Valid EmailValidationRequest request) {
        EmailValidationResponse response = emailValidationService.validateEmail(request);
        return ApiResponse.success(response);
    }
}
