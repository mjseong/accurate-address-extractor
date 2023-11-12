package com.sundaydev.addr.kis.repository;

import com.sundaydev.addr.kis.common.AddressConstant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MockAddressDataRepository implements AddressRepository {

    private final Map<String, String> KOREA_GU_MAP =
            Stream.of(AddressConstant.KoreaDistrict.split(","))
            .collect(Collectors.toMap(p -> p, p-> p, (p1, p2) -> p1));

    public String findByGu(String gu){
        return KOREA_GU_MAP.get(gu);
    }
}
