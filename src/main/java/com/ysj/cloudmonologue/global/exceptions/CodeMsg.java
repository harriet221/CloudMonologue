package com.ysj.cloudmonologue.global.exceptions;

import lombok.Getter;

@Getter
public enum CodeMsg {
    E400_Bad_Request("400", "잘못된 요청입니다."),
    E401_Unauthorized("401", "로그인이 필요합니다."),
    E403_Forbidden("403", "접근 권한이 없습니다."),
    E404_Not_Found("404", "해당 리소스를 찾을 수 없습니다."),
    E405_Method_Not_Allowed("405", "해당 작업을 지원하지 않습니다.");

    private  final String code;
    private final String message;

    CodeMsg(String code, String message){
        this.code = code;
        this.message = message;
    }
}
