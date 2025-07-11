package com.schoolerp.mysaas.common.emum;

import lombok.Getter;

public class MessageCodeEnum {
    
    @Getter
    public enum STUDENT_MC {
        MC_100(100), MC_101(101), MC_102(102);

        private final int code;

        STUDENT_MC(int code) {
            this.code = code;
        }

    }
}
