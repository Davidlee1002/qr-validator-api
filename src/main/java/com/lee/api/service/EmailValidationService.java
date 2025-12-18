package com.lee.api.service;

import com.lee.api.model.EmailValidationRequest;
import com.lee.api.model.EmailValidationResponse;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

@Service
public class EmailValidationService {

    // 常见的临时邮箱域名
    private static final Set<String> DISPOSABLE_DOMAINS = new HashSet<>(Arrays.asList(
            "tempmail.com", "10minutemail.com", "guerrillamail.com", "mailinator.com",
            "throwaway.email", "maildrop.cc", "temp-mail.org", "getnada.com"
    ));

    // 角色邮箱前缀
    private static final Set<String> ROLE_PREFIXES = new HashSet<>(Arrays.asList(
            "admin", "info", "support", "sales", "contact", "help", "noreply",
            "webmaster", "postmaster", "abuse", "marketing"
    ));

    public EmailValidationResponse validateEmail(EmailValidationRequest request) {
        String email = request.getEmail();
        
        if (email == null || email.trim().isEmpty()) {
            return buildErrorResponse("Email cannot be empty");
        }

        email = email.trim().toLowerCase();

        // 1. 格式验证
        EmailValidator validator = EmailValidator.getInstance();
        boolean formatValid = validator.isValid(email);

        if (!formatValid) {
            return EmailValidationResponse.builder()
                    .valid(false)
                    .email(email)
                    .formatValid(false)
                    .message("Invalid email format")
                    .build();
        }

        // 提取域名
        String domain = email.substring(email.indexOf('@') + 1);
        String localPart = email.substring(0, email.indexOf('@'));

        // 2. 检查是否是临时邮箱
        boolean isDisposable = false;
        if (request.getCheckDisposable()) {
            isDisposable = DISPOSABLE_DOMAINS.contains(domain);
        }

        // 3. 检查是否是角色邮箱
        boolean isRoleAccount = ROLE_PREFIXES.contains(localPart);

        // 4. 检查MX记录
        boolean mxFound = false;
        if (request.getCheckMx()) {
            mxFound = checkMxRecord(domain);
        }

        // 综合判断
        boolean valid = formatValid && !isDisposable && (mxFound || !request.getCheckMx());

        return EmailValidationResponse.builder()
                .valid(valid)
                .email(email)
                .formatValid(formatValid)
                .mxFound(mxFound)
                .disposable(isDisposable)
                .roleAccount(isRoleAccount)
                .domain(domain)
                .message(valid ? "Email is valid" : "Email validation failed")
                .build();
    }

    private boolean checkMxRecord(String domain) {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
            DirContext ctx = new InitialDirContext(env);
            
            Attributes attrs = ctx.getAttributes(domain, new String[]{"MX"});
            Attribute attr = attrs.get("MX");
            
            return attr != null && attr.size() > 0;
        } catch (NamingException e) {
            return false;
        }
    }

    private EmailValidationResponse buildErrorResponse(String message) {
        return EmailValidationResponse.builder()
                .valid(false)
                .message(message)
                .build();
    }
}