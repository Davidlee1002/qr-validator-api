package com.lee.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "邮箱验证详细结果")
public class EmailValidationResponse {

    @Schema(description = "邮箱是否完全有效 (综合判断)", example = "true")
    private boolean valid;

    @Schema(description = "被验证的邮箱地址", example = "test@gmail.com")
    private String email;

    @Schema(description = "格式是否正确", example = "true")
    private boolean formatValid;

    @Schema(description = "MX 记录是否存在 (域名可达性)", example = "true")
    private boolean mxFound;

    @Schema(description = "是否为一次性/临时邮箱", example = "false")
    private boolean disposable;

    @Schema(description = "是否为角色/公共账号 (如 admin, support)", example = "false")
    private boolean roleAccount;

    @Schema(description = "邮箱域名", example = "gmail.com")
    private String domain;

    @Schema(description = "验证结果描述", example = "Email is valid")
    private String message;
}
