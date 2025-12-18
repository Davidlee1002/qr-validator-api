package com.lee.api.controller;

import com.lee.api.model.ApiResponse;
import com.lee.api.model.QRCodeRequest;
import com.lee.api.service.QRCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/qrcode")
@RequiredArgsConstructor
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @PostMapping("/generate")
    public ApiResponse<String> generateQRCode(@RequestBody @Valid QRCodeRequest request) throws Exception {
        String qrCodeImage = qrCodeService.generateQRCode(request);
        return ApiResponse.success(qrCodeImage);
    }

    @GetMapping("/health")
    public ApiResponse<String> health() {
        return ApiResponse.success("QR Code API is running");
    }
}