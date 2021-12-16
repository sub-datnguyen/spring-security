package vn.elca.training.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    TECHNICAL_ERROR_CODE(1),
    BUSINESS_ERROR_CODE(3);
    
    private int code;
    
    ErrorCodeEnum(int code) {
        this.code = code;
    }
}
