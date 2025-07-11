package com.schoolerp.mysaas.common.response.repository;

import com.schoolerp.mysaas.common.response.ResponseMessage;
import org.springframework.stereotype.Repository;

@Repository
public class ResponseMessageDataAccess {
    public ResponseMessage getMessageByCode(String tenantCode, String locale, int code) {
        return null;
    }
}
