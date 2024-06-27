package com.ysj.cloudmonologue.global.exceptions;

import com.ysj.cloudmonologue.global.rsData.RsData;
import com.ysj.cloudmonologue.standard.Empty;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final RsData<Empty> rsData;

    public GlobalException(String resultCode, String msg) {
        super("resultCode=" + resultCode + ", msg=" + msg);
        this.rsData = RsData.of(resultCode, msg);
    }

}
