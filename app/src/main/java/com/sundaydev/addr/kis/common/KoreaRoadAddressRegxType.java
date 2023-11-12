package com.sundaydev.addr.kis.common;

public enum KoreaRoadAddressRegxType {

    P_RO_GIL("\\b([가-힣A-Za-z·\\d~\\-\\.\\s*]+로)\\s*([가-힣A-Za-z·\\d~\\-\\.\\s*]+길)\\b"),
    P_GU("\\b(?:([가-힣\\s]+구))\\b|(?:([가-힣\\s*]+구)\\s*([가-힣A-Za-z·\\d~\\-\\.]+))\\b"),
    P_RO("\\b(?:([가-힣A-Za-z·\\d~\\-\\.\\s]+로)\\b)|(?:([가-힣A-Za-z·\\d~\\-\\.\\s*]+로)\\s*([가-힣A-Za-z·\\d~\\-\\.]+))\\b"),
    P_GIL("\\b(?:([가-힣A-Za-z·\\d~\\-\\.\\s]+길)\\b)|(?:([가-힣A-Za-z·\\d~\\-\\.\\s]+길)\\s*([가-힣A-Za-z·\\d~\\-\\.]+))\\b"),
    P_GU_OR_RO("\\b(?:([가-힣\\s]+구))\\b|(?:([가-힣A-Za-z·\\d~\\-\\.\\s]+로)\\b)"),

    P_A_GU_RO_GIL("\\b([가-힣]+구)\\s*([가-힣A-Za-z·\\d~\\-\\.]+로)\\s*([가-힣A-Za-z·\\d~\\-\\.]+길)\\b"),
    P_A_GU_RO("\\b(?:([가-힣]+구)\\s*([가-힣A-Za-z·\\d~\\-\\.]+로))\\b"),
    P_A_GU_GIL("\\b([가-힣]+구)\\s*([가-힣A-Za-z·\\d~\\-\\.]+길)\\b"),
    P_A_RO_GIL("\\b([가-힣A-Za-z·\\d~\\-\\.]+로)\\s*([가-힣A-Za-z·\\d~\\-\\.]+길)\\b"),
    P_A_RO("\\b(?:([가-힣A-Za-z·\\d~\\-\\.]+로)\\b)")
    ;

    KoreaRoadAddressRegxType(String regx){
        this.regx = regx;
    }
    private String regx;

    public String getRegx(){
        return this.regx;
    }
}
