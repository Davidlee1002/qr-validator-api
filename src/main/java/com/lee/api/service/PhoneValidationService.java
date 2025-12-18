package com.lee.api.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.lee.api.model.PhoneValidationRequest;
import com.lee.api.model.PhoneValidationResponse;
import org.springframework.stereotype.Service;

@Service
public class PhoneValidationService {

    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    public PhoneValidationResponse validatePhone(PhoneValidationRequest request) {
        String numberStr = request.getPhoneNumber();
        String countryCode = request.getCountryCode();

        try {
            // 1. 解析号码
            // 如果没传 countryCode，默认设为 "US" (或者你可以设为 "CN") 防止解析报错
            String defaultRegion = (countryCode != null && !countryCode.isEmpty()) ? countryCode.toUpperCase() : "US";
            Phonenumber.PhoneNumber number = phoneUtil.parse(numberStr, defaultRegion);

            // 2. 验证号码是否合法
            boolean isValid = phoneUtil.isValidNumber(number);

            if (!isValid) {
                return PhoneValidationResponse.builder()
                        .valid(false)
                        .message("Invalid phone number format or non-existent number")
                        .build();
            }

            // 3. 获取号码信息
            String type = phoneUtil.getNumberType(number).toString();
            String region = phoneUtil.getRegionCodeForNumber(number);
            String e164 = phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
            String international = phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);

            return PhoneValidationResponse.builder()
                    .valid(true)
                    .formattedE164(e164)
                    .formattedInternational(international)
                    .type(type)
                    .region(region)
                    .message("Valid phone number")
                    .build();

        } catch (NumberParseException e) {
            // 解析发生错误（比如用户传了 "hello world"）
            return PhoneValidationResponse.builder()
                    .valid(false)
                    .message("Error parsing phone number: " + e.getMessage())
                    .build();
        }
    }
}
