package com.sundaydev.aas;

import com.sundaydev.aas.repository.AddressRepository;
import com.sundaydev.aas.repository.MockAddressDataRepository;
import com.sundaydev.aas.service.SearchAddressService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddressSearchApplication {

    public static void main(String[] args){

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        AddressRepository addressRepository = new MockAddressDataRepository(); //임시 gu데이터 조회용 추가
        SearchAddressService addressService = new SearchAddressService(addressRepository);
        try {
            System.out.print("콘솔 입력을 하세요: ");
            String userInput = reader.readLine(); // 콘솔에서 한 줄을 읽어옵니다.

            System.out.println("사용자 입력: " + userInput);

            String address = addressService.extractAddress(userInput);
            System.out.println("출력 결과: " + address);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
