package com.lee.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "二维码生成请求参数")
public class QRCodeRequest {

    @Schema(description = "要编码进二维码的文本或URL", example = "https://www.google.com")
    @NotBlank(message = "内容不能为空")
    private String content;

    @Schema(description = "二维码图片的尺寸（像素），范围 100-1000", example = "300")
    @Min(value = 100, message = "尺寸最小为 100 像素")
    @Max(value = 1000, message = "尺寸最大为 1000 像素")
    private Integer size = 300;

    @Schema(description = "图片输出格式 (PNG, JPG, GIF)", example = "PNG")
    @Pattern(regexp = "(?i)^(PNG|JPG|JPEG|GIF)$", message = "目前仅支持 PNG, JPG, JPEG, GIF 格式")
    private String format = "PNG";

    @Schema(description = "前景色（二维码颜色），6位十六进制代码，无需#号", example = "000000")
    @Pattern(regexp = "^[A-Fa-f0-9]{6}$", message = "前景色必须是6位十六进制颜色代码 (例如: 000000)")
    private String fgColor = "000000";

    @Schema(description = "背景色，6位十六进制代码，无需#号", example = "FFFFFF")
    @Pattern(regexp = "^[A-Fa-f0-9]{6}$", message = "背景色必须是6位十六进制颜色代码 (例如: FFFFFF)")
    private String bgColor = "FFFFFF";

    @Schema(description = "容错级别 (L=7%, M=15%, Q=25%, H=30%)", example = "M")
    @Pattern(regexp = "^[LMQH]$", message = "容错级别必须是 L, M, Q 或 H")
    private String errorCorrection = "M";
}
