package com.lee.api.controller;

import com.lee.api.model.ApiResponse;
import com.lee.api.model.PhoneValidationRequest;
import com.lee.api.model.PhoneValidationResponse;
import com.lee.api.service.PhoneValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/validation/phone")
@RequiredArgsConstructor
@Tag(name = "Phone Validation", description = "Global phone number validation & formatting")
public class PhoneValidationController {

    private final PhoneValidationService phoneValidationService;

    @PostMapping
    @Operation(summary = "Validate Phone Number", description = "Parses, validates and formats phone numbers from any region.")
    public ApiResponse<PhoneValidationResponse> validatePhone(@RequestBody @Valid PhoneValidationRequest request) {
        PhoneValidationResponse response = phoneValidationService.validatePhone(request);
        return ApiResponse.success(response);
    }
}
