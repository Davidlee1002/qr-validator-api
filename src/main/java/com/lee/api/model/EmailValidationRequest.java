package com.lee.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "邮箱验证请求参数")
public class EmailValidationRequest {

    @Schema(description = "需要验证的邮箱地址", example = "user@example.com")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确") // Spring Boot 自带的基础格式检查
    private String email;

    @Schema(description = "是否检查 MX 记录 (连接 DNS 服务器)", example = "true")
    private Boolean checkMx = true;

    @Schema(description = "是否检查一次性临时邮箱", example = "true")
    private Boolean checkDisposable = true;
}
