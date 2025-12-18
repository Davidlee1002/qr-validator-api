package com.lee.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "手机号验证结果")
public class PhoneValidationResponse {

    @Schema(description = "号码是否有效", example = "true")
    private boolean valid;

    @Schema(description = "标准化格式 (E.164)", example = "+14155552671")
    private String formattedE164;

    @Schema(description = "国际格式", example = "+1 415-555-2671")
    private String formattedInternational;

    @Schema(description = "号码类型 (MOBILE, FIXED_LINE, VOIP等)", example = "MOBILE")
    private String type;

    @Schema(description = "所属国家/地区", example = "US")
    private String region;

    @Schema(description = "验证信息", example = "Valid mobile number")
    private String message;
}
