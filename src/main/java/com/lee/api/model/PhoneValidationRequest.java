package com.lee.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "手机号验证请求参数")
public class PhoneValidationRequest {

    @Schema(description = "手机号码 (建议带国家代码，如 +8613800000000)", example = "+14155552671")
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;

    @Schema(description = "默认国家代码 (ISO 2位代码)，如果手机号不带+号时使用", example = "US")
    private String countryCode;
}
