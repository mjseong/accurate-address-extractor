package com.sundaydev.aas;


import com.sundaydev.aas.common.utils.ExtractionAddress;
import com.sundaydev.aas.common.KoreaRoadAddressRegxType;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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



    @Test
    public void sentenceTest(){
        String in = "저희집 전구 가 고장나서, 이대로 둘수 없어요. 배송좀 해주시겠어요? 주소는 연수구 컨 벤 시 아 대 로 1 3 0 번길 자이아파트로 보내주시면 되요.";
        var result = ExtractionAddress.extractAddressWithTokenizer(in);
    }


    @Test
    public void tokenTest2(){
        String in = "저희집 주소는 연수구 컨 벤 시아대 로 1 3 0번 길 자이아파트로 보내주시면 되요.";
        var result = ExtractionAddress.extractAddressWithTokenizer(in);
        System.out.println(result);
    }

    @Test
    public void tokenGUTest(){
        String in = "나는 연 수 구 컨벤시아대로";
//        String regx = "\\b(?:([가-힣\\s*]+구))\\b|(?:([가-힣\\s*]+구)\\s*([가-힣])+)\\b";
        Pattern p = Pattern.compile(KoreaRoadAddressRegxType.P_GU.getRegx());
        Matcher m = p.matcher(in);

        if(m.find()){
            var result = m.group(1) != null ? m.group(1): m.group(2);
            System.out.println(result);
        }
    }

    @Test
    public void tokenGUROTest(){
        String in = "연수구컨벤시구 아대구로";
        Pattern p = Pattern.compile(KoreaRoadAddressRegxType.P_A_GU_RO.getRegx());
        Matcher m = p.matcher(in);

        if(m.find()){
            var result = m.group(1) +" "+m.group(2);
            System.out.println(result);
        }
    }

    @Test
    public void tokenROGILTest(){
        String in = "수 컨벤시아대로 30번길";
        String regx = "\\b([가-힣A-Za-z·\\d~\\-\\.]+로)\\s*([가-힣A-Za-z·\\d~\\-\\.\\s*]+길)\\b";
        Pattern p = Pattern.compile(regx);
        Matcher m = p.matcher(in);

        if(m.find()){
            var result = m.group(1)+" "+m.group(2);
            System.out.println(result);
        }
    }



}
