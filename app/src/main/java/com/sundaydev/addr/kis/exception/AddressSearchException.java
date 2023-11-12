package com.sundaydev.addr.kis.exception;

public class AddressSearchException extends RuntimeException{
    String reason;
    ErrorType errorType;

    public AddressSearchException(){
        super();
    }

    public AddressSearchException(ErrorType errorType){
        super();
        this.errorType = errorType;
    }

    public AddressSearchException(ErrorType errorType, String reason){
        super();
        this.errorType = errorType;
        this.reason = reason;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getReason(){
        return reason;
    }
}
