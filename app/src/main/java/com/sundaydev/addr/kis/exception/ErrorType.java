package com.sundaydev.addr.kis.exception;

public enum ErrorType {
    NOT_FOUND("ADDR_0001","address find not found");

    ErrorType(String code, String message){
        this.code = code;
        this.message = message;
    }

    String code;
    String message;

    public String getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }
}
