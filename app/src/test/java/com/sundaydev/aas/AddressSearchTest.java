package com.sundaydev.aas;

import com.sundaydev.aas.service.SearchAddressService;
import com.sundaydev.aas.utils.ExtractionAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressSearchTest {

    private SearchAddressService searchAddressService;

    @BeforeEach
    public void init(){
        this.searchAddressService = new SearchAddressService();
    }

    @Test
    public void extractTest(){
        String addr = "안녕하세요 제가 경기도 광주시 성남시 순암로 36번길 87에 살아요 치킨한마리 보내주세요";
        String result = searchAddressService.extractAddress(addr);
        System.out.println(result);
        Assertions.assertEquals("순암로 36번길", result);
    }

    @Test
    public void guRoadTest(){
        String addr = "인천 연 수구 컨 벤 시 아 대로 1 3 0번길";
        String result = searchAddressService.extractAddress(addr);
        System.out.println(result);
        Assertions.assertEquals("연수구 컨벤시아대로130번길", result);
    }

    @Test
    public void roTest(){
        String addr = "경기 광주시 순암로36번길 87";
        String result = searchAddressService.extractAddress(addr);
        Assertions.assertEquals("순암로 36번길", result);
    }

}
