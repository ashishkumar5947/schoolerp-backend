package com.schoolerp.mysaas.common.response;

import com.schoolerp.mysaas.common.response.repository.ResponseMessageDataAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ResponseMessageService {

    private final ResponseMessageDataAccess responseMessageDataAccess;

    @Autowired
    public ResponseMessageService(ResponseMessageDataAccess responseMessageDataAccess) {
        this.responseMessageDataAccess = responseMessageDataAccess;
    }

    /**
     * Resolve the localized message based on:
     * - error/success code
     * - tenant code
     * - requested language (fallback to English if not found)
     */
    public String getMessage(String tenantCode, String locale, int code) {
        locale = (locale == null || locale.isBlank()) ? "en" : locale.toLowerCase();

        ResponseMessage responseMessage = responseMessageDataAccess.getMessageByCode(tenantCode, locale, code);
        if (ObjectUtils.isEmpty(responseMessage)) {
            log.warn("⚠️ Message not found for code [{}] and tenant [{}]", code, tenantCode);
            return "Unknown message code: " + code;
        }

        Map<String, String> localizedMap = responseMessage.getMessages();

        // Try language match
        if (localizedMap.containsKey(locale)) {
            return localizedMap.get(locale);
        }

        // Fallback to English
        if (localizedMap.containsKey("en")) {
            return localizedMap.get("en");
        }

        log.warn("⚠️ Message for code [{}], tenant [{}] not found in lang [{}] or fallback [en]", code, tenantCode, locale);
        return "Message not available in requested language.";
    }

//    /**
//     * Overloaded: resolve message directly from HttpServletRequest headers.
//     * Can be used inside ExceptionHandlers or Filters.
//     */
//    public String getMessageFromRequest(String code, HttpServletRequest request) {
//        String lang = request.getHeader("Accept-Language");
//        String tenant = request.getHeader("X-Tenant-Code");
//
//        if (tenant == null || tenant.isBlank()) {
//            tenant = "default";
//        }
//
//        return getMessage(code, lang, tenant);
//    }
//
//    /**
//     * Optionally return full DTO with metadata (for advanced use)
//     */
//    public LocalizedMessageDto getLocalizedMessage(String code, String language, String tenantCode) {
//        String message = getMessage(code, language, tenantCode);
//        return new LocalizedMessageDto(code, message, language, tenantCode);
//    }
}
