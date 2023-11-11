package com.sundaydev.aas.service;

import com.sundaydev.aas.utils.ExtractionAddress;
import com.sundaydev.aas.utils.KoreaRoadAddressRegxType;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchAddressService {

    private final Pattern P_GU_GIL = Pattern.compile(KoreaRoadAddressRegxType.P_GU_GIL.getRegx());
    private final Pattern P_GU_RO = Pattern.compile(KoreaRoadAddressRegxType.P_GU_RO.getRegx());
    private final Pattern P_GU_RO_GIL = Pattern.compile(KoreaRoadAddressRegxType.P_GU_RO_GIL.getRegx());
    private final Pattern P_RO_GIL = Pattern.compile(KoreaRoadAddressRegxType.P_RO_GIL.getRegx());


    public void extractAddresses(List<String> addresses){

        for(String address: addresses){

        }
    }

    public String extractAddress(String candidateAddress){
        //pre-processing
        Set<String> candidateAddrSet = ExtractionAddress.extractAddressWithTokenizer(candidateAddress);
        StringJoiner stringJoiner = new StringJoiner(" ");
        System.out.println("candidateSet: " + candidateAddrSet);

        //validation
        candidateAddrSet.forEach(address -> {
            if(this.validateAddress(address)){
               stringJoiner.add(address);
            }
        });

        return stringJoiner.toString();
    }

    //TODO: 데이터베이스 조회 또는 도로명 주소 openApi 호출로 유효성 검사
    private boolean validateAddress(String address){

        if(this.getPrintAddressPattern(address).isBlank()){
            return false;
        }
        return true;
    }

    private String getPrintAddressPattern(String address){
        Matcher mGUGIL = P_GU_GIL.matcher(address);
        Matcher mGURO = P_GU_RO.matcher(address);
        Matcher mGUROGIL = P_GU_RO_GIL.matcher(address);
        Matcher mROGIL = P_RO_GIL.matcher(address);

        if(mGUROGIL.find()){
            return mGUROGIL.group();
        }
        if(mGURO.find()){
            return mGURO.group();
        }
        if(mGUGIL.find()){
            return mGUGIL.group();
        }
        if(mROGIL.find()){
            return mROGIL.group();
        }

        return "";
    }

}
