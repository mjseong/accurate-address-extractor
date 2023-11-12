package com.sundaydev.addr.kis.service;

import com.sundaydev.addr.kis.common.KoreaRoadAddressRegxType;
import com.sundaydev.addr.kis.common.utils.ExtractionAddress;
import com.sundaydev.addr.kis.common.utils.ExtractionCsv;
import com.sundaydev.addr.kis.exception.AddressSearchException;
import com.sundaydev.addr.kis.exception.ErrorType;
import com.sundaydev.addr.kis.repository.AddressRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchAddressService {
    private final AddressRepository addressRepository;
    private final Pattern pGU = Pattern.compile(KoreaRoadAddressRegxType.P_GU.getRegx());

    public SearchAddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    public void extractAddresses(String csvPath) throws IOException {

        File file = new File(csvPath);
        Path path = Paths.get(file.getAbsolutePath());
        List<String> candidateAddresses = ExtractionCsv.extractCsv(path);

        if(candidateAddresses.isEmpty()){
            throw new AddressSearchException(ErrorType.NOT_FOUND);
        }

        for(String address: candidateAddresses){
            try {
                String result = this.extractAddress(address);
                System.out.println(String.format("입력주소: %s \n" +
                        "출력주소: %s", address, result));
            }catch (AddressSearchException ase){
                System.out.println("오류주소: "+address);
                ase.getReason();
            }
        }
    }

    public String extractAddress(String candidateAddress){
        //pre-processing
        Set<String> candidateAddrSet = ExtractionAddress.extractAddressWithTokenizer(candidateAddress);
        List<String> vaildatedList = new ArrayList<>();

        //validation
        candidateAddrSet.forEach(cAddr -> {
            if(this.validateAddress(cAddr)){
                vaildatedList.add(cAddr);
            }
        });

        if(vaildatedList.isEmpty()){
            throw new AddressSearchException(ErrorType.NOT_FOUND, "candidate Address set is empty");
        }

        return vaildatedList.get(0);
    }

    //TODO: 데이터베이스 조회 또는 도로명 주소 openApi 호출로 유효성 검사
    private boolean validateAddress(String address){

        String result = this.getAddress(address);
        if(result != null && !result.isBlank()){
            return true;
        }
        return false;
    }

    private String getAddress(String address){

        /**
         * TODO: communication Address api or findByAddress DB
         * DB 또는 API검색 기능을 넣지 않아 임시로 대한민국 자치구 검색만 해본다.
         */
        Matcher m = pGU.matcher(address);
        if(m.find()){
            String gu = m.group(1);
            gu = addressRepository.findByGu(gu);
            if(gu != null && gu.isBlank()){
                return "";
            }else{
                return gu;
            }
        }
        return "exist";
    }


}
