package com.sundaydev.aas;

import com.sundaydev.aas.repository.AddressRepository;
import com.sundaydev.aas.repository.MockAddressDataRepository;
import com.sundaydev.aas.service.SearchAddressService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressSearchTest {

    private SearchAddressService searchAddressService;

    @BeforeEach
    public void init(){
        AddressRepository addressRepository = new MockAddressDataRepository();
        this.searchAddressService = new SearchAddressService(addressRepository);
    }

    @Test
    public void exampleAddressTest1(){
        String in = "성남, 분당 백 현 로 265, 푸른마을 아파트로 보내주세요!! ";
        String result = searchAddressService.extractAddress(in);
        Assertions.assertEquals("백현로", result);
    }

    @Test
    public void exampleAddressTest2(){
        String in = "마포구 도화-2길 코끼리분식 ";
        String result = searchAddressService.extractAddress(in);
        Assertions.assertEquals("마포구 도화2길", result);
    }

    @Test
    public void exampleAddressTest3(){
        String in = "종로구 종 로 1 피자배달이요  ";
        String result = searchAddressService.extractAddress(in);
        Assertions.assertEquals("종로구 종로", result);
    }

    @Test
    public void exampleAddressTest4(){
        String in = "서울시 종로구 인사 동 길 44 쌈지길";
        String result = searchAddressService.extractAddress(in);
        Assertions.assertEquals("종로구 인사동길", result);
    }

    @Test
    public void exampleAddressTest5(){
        String in = "서울 종로구 대학로11길 9-2";
        String result = searchAddressService.extractAddress(in);
        Assertions.assertEquals("종로구 대학로11길", result);
    }

    @Test
    public void exampleAddressTest6(){
        String in = "경기 성남시 중원구 둔촌대로 295 (하대원동)";
        String result = searchAddressService.extractAddress(in);
        Assertions.assertEquals("중원구 둔촌대로", result);
    }

    @Test
    public void exampleAddressTest7(){
        String in = "경기 성남시 분당 동판교로52번길 26";
        String result = searchAddressService.extractAddress(in);
        Assertions.assertEquals("동판교로 52번길", result);
    }

    @Test
    public void sentenceExtractAddressTest1(){
        String in = "저희집 전구 가 고장나서, 이대로 둘수 없어요. 배송좀 해주시겠어요? 주소는 연수구 소 용 돌 이 대 로 1 0 번길 아파트로 보내주시면 되요.";
        String result = searchAddressService.extractAddress(in);
        Assertions.assertEquals("연수구 소용돌이대로 10번길", result);
    }

    @Test
    public void sentenceExtractAddressTest2(){
        String in = "안녕하세요 제가 경기도 성남시 순암로 36번길 87에 살아요 치킨한마리 보내주세요";
        String result = searchAddressService.extractAddress(in);
        Assertions.assertEquals("순암로 36번길", result);
    }


}
