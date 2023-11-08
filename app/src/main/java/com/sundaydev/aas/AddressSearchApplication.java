package com.sundaydev.aas;

import com.sundaydev.aas.service.SearchAddressService;

import java.util.List;

public class AddressSearchApplication {

    public static void main(String[] args){

        List<String> list = List.of(
                "마 포구 도화-2길 코끼리분식",
                "분당 백현로",
                "디지털로30길 28",
                "분당구 대왕판교1로 여러분들로 기쁩니다",
                "분당구 야탑길",
                "성남, 분당 백 현 로 265, 푸른마을 아파트로 보내주세요!!");

        SearchAddressService addressService = new SearchAddressService();

        addressService.extractAddress(list);


    }
}
