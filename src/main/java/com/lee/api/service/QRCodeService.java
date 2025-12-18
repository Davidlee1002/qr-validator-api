package com.lee.api.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lee.api.model.QRCodeRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeService {

    public String generateQRCode(QRCodeRequest request) throws Exception {
        // 1. 配置参数 (容错率等)
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 根据请求设置容错率 (L, M, Q, H)
        hints.put(EncodeHintType.ERROR_CORRECTION, getErrorCorrectionLevel(request.getErrorCorrection()));
        // 设置边距 (0 表示无白边，或者设为 1)
        hints.put(EncodeHintType.MARGIN, 1);

        // 2. 生成 BitMatrix (二维码矩阵)
        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                request.getContent(),
                BarcodeFormat.QR_CODE,
                request.getSize(),
                request.getSize(),
                hints
        );

        // 3. 处理颜色 (前景色 & 背景色)
        // 将 Hex 字符串 (e.g. "FF0000") 转为 ARGB int 值
        int onColor = 0xFF000000 | Integer.parseInt(request.getFgColor(), 16);
        int offColor = 0xFF000000 | Integer.parseInt(request.getBgColor(), 16);
        MatrixToImageConfig config = new MatrixToImageConfig(onColor, offColor);

        // 4. 输出图片流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, request.getFormat(), outputStream, config);

        // 5. 转为 Base64 返回
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // 辅助方法：转换容错级别字符串
    private ErrorCorrectionLevel getErrorCorrectionLevel(String level) {
        return switch (level != null ? level.toUpperCase() : "M") {
            case "L" -> ErrorCorrectionLevel.L;
            case "Q" -> ErrorCorrectionLevel.Q;
            case "H" -> ErrorCorrectionLevel.H;
            default -> ErrorCorrectionLevel.M;
        };
    }
}
