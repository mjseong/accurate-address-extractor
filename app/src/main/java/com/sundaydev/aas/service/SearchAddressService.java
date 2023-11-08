package com.sundaydev.aas.service;

import com.sundaydev.aas.utils.ExtractionAddress;

import java.util.List;

public class SearchAddressService {

    public void extractAddress(List<String> addresses){
        for(String address: addresses){
//            System.out.println(ExtractionAddress.extractAddressWithRegx(address));
            ExtractionAddress.extractAddressWithRoadRegx(address);
        }

    }
}
