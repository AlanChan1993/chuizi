package com.chuizi.enums;

public enum ResultEnum implements CodeEnum{
    SUCCESS(200, "success"),

    FALSE(500,"fail"),
    ;
    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    public String getMsg(){
        return msg;
    }

    public String getCodeString(){
        return code+"";
    }
}
