package com.sundaydev.addr.kis;


import com.sundaydev.addr.kis.common.utils.ExtractionAddress;
import org.junit.jupiter.api.Test;

public class ExtractionAddressUtilTest {

    @Test
    public void test(){
        var result = ExtractionAddress.extractAddressWithTokenizer("제가 천안시 동남구 풍세면 남 관 길 77-14에 사는대요, 경기도 광주시 순암로 36번길 87로 피자 하나 보내주세요");
        System.out.println(result);
    }

    @Test
    public void test1(){
        var result = ExtractionAddress.extractAddressWithTokenizer("창원시 마산합포구  TK 컨 벤 시아대 로13 0번 길 32");
        System.out.println(result);
    }

    @Test
    public void test2(){
        var result = ExtractionAddress.extractAddressWithTokenizer("제가 천안시 동남구 풍세면 남 관 길 77-14에 사는대요, 경기도 광주시 순 암로 36번 길 87로 피자 하나 보내주세요");
        System.out.println(result);
    }




}
