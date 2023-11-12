package com.sundaydev.addr.kis;

import com.sundaydev.addr.kis.common.ApplicationType;
import com.sundaydev.addr.kis.exception.AddressSearchException;
import com.sundaydev.addr.kis.repository.AddressRepository;
import com.sundaydev.addr.kis.repository.MockAddressDataRepository;
import com.sundaydev.addr.kis.service.SearchAddressService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddressSearchApplication {

    public static void main(String[] args){

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        AddressRepository addressRepository = new MockAddressDataRepository(); //임시 gu데이터 조회용 추가
        SearchAddressService addressService = new SearchAddressService(addressRepository);

        System.out.println("프로그램을 종료하려면 'exit'을 입력하세요.");

        try {
            System.out.print("프로그램 종류(csv: c 또는 console: 아무 키나 눌러주세요)를 입력을 하세요: ");
            String userInput = reader.readLine();

            ApplicationType type = ApplicationType.CONSOLE;

            for(ApplicationType appType:ApplicationType.values()){
                if(appType.name().equalsIgnoreCase(userInput)){
                    type = appType;
                }else if(userInput.equals("c")){
                    type = ApplicationType.CSV;
                }
            }

            switch (type){
                case CSV -> {
                    System.out.print("csv mode - 파일 경로 입력을 하세요: ");
                    userInput = reader.readLine();

                    if(userInput.equalsIgnoreCase("exit")){
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    }
                    addressService.extractAddresses(userInput);
                }
                default -> {
                    while(true){
                        try {
                            System.out.print("console mode - 주소를 입력 하세요: ");
                            userInput = reader.readLine(); // 콘솔에서 한 줄을 읽어옵니다.
                            if(userInput.equalsIgnoreCase("exit")){
                                System.out.println("프로그램을 종료합니다.");
                                break;
                            }

                            System.out.println("사용자 입력: " + userInput);
                            String address = addressService.extractAddress(userInput);
                            System.out.println("출력 결과: " + address);
                        } catch (AddressSearchException ase){
                            System.out.println("Error Message: " + ase.getErrorType().getMessage() + ", "+ ase.getReason());
                        }
                    }
                }
            }

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
