package com.sundaydev.aas;

import com.sundaydev.aas.utils.ExtractionAddress;
import com.sundaydev.aas.utils.KoreaRoadAddressRegxType;
import org.junit.jupiter.api.Test;

import java.util.*;
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
    public void example1Test(){
        String in = "성남, 분당 백 현 로 265, 푸른마을 아파트로 보내주세요!! ";
        ExtractionAddress.extractAddressWithTokenizer(in);
    }

    @Test
    public void example2Test(){
        String in = "마포구 도화-2길 코끼리분식 ";
        ExtractionAddress.extractAddressWithTokenizer(in);
    }

    @Test
    public void example3Test(){
        String in = "종로구 종 로 1 피자배달이요  ";
        ExtractionAddress.extractAddressWithTokenizer(in);
    }

    @Test
    public void example4Test(){
        String in = "서울시 종로구 인사 동 길 44 쌈지길";
        ExtractionAddress.extractAddressWithTokenizer(in);
    }

    @Test
    public void example5Test(){
        String in = "서울 종로구 대학로11길 9-2";
        ExtractionAddress.extractAddressWithTokenizer(in);
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
//        String regx = "\\b(?:([가-힣\\s*]+구))\\b|(?:([가-힣\\s*]+구)\\s*([가-힣])+)\\b";
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

    @Test
    public void tokenROTest(){
        String in = "분당 백현로";
        String regx = "\\b(?:([가-힣A-Za-z·\\d~\\-\\.]+로)\\b)|(?:([가-힣A-Za-z·\\d~\\-\\.]+로))\\b|(?:([가-힣A-Za-z·\\d~\\-\\.\\s*]+로)\\s*([가-힣A-Za-z·\\d~\\-\\.]+))\\b";
//        String regx = "\\b([가-힣A-Za-z·\\d~\\-\\.]+로)\\b";
        Pattern p = Pattern.compile(regx);
        Matcher m = p.matcher(in);

        if(m.find()){
            System.out.println(m.group(1));
            System.out.println(m.group(2));
            System.out.println(m.group(3));
        }
    }

    @Test
    public void test3(){
//        String in = "저희집 전구 가 고장나서, 배송해주시겠어요? 주소는 연수구 130번길";
        String in = "저희집 전구 가 고장나서, 이대로 둘수 없어요. 배송좀 해주시겠어요? 주소는 연수구 컨 벤 시 아 대 로 1 3 0 번길 자이아파트로 보내주시면 되요.";
        in = in.replaceAll("[^a-zA-Z0-9가-힣\\s]", "");
        in = in.replaceAll(" ", ", ");
        List<String> words = Arrays.asList(in.split(","));
        List<String> seqList = new ArrayList<>();
        System.out.println(words);

        StringJoiner stringJoiner = new StringJoiner(" ");
        String mergeWord = "";

        for(String word: words) {

            if(word.length() > 2){
                mergeWord += word;
            }else{
                mergeWord += word.trim();
            }

            System.out.println(mergeWord);
            String result = this.checkAddressRegx(mergeWord);
            if(!result.isBlank()){
                result = result.replaceAll(" ", "");
                seqList.add(result);
                stringJoiner.add(result);
                mergeWord = "";
            }
        }

        System.out.println(seqList);
        String candidateAddress = stringJoiner.toString();
        Set<String> candidateSet = new LinkedHashSet<>();
        String seqTmp ="";

        for(String seqStr:seqList){
            seqTmp += String.format("%s ",seqStr);
            String result = this.checkAddressRegx(seqTmp);
            if(!result.isBlank()){
                candidateSet.add(result);
                continue;
            }
            seqTmp = "";
        }

        System.out.println(candidateSet);
    }

    private String checkAddressRegx(String candidateAddress){
        Pattern pAll = Pattern.compile("\\b([가-힣]+구)\\s*([가-힣A-Za-z·\\d~\\-\\.]+로)\\s*([가-힣A-Za-z·\\d~\\-\\.]+길)\\b");
        Pattern pGuRo = Pattern.compile("\\b([가-힣]+구)\\s*([가-힣A-Za-z·\\d~\\-\\.]+로)\\b");
        Pattern pGuGil = Pattern.compile("\\b([가-힣]+구)\\s*([가-힣A-Za-z·\\d~\\-\\.]+길)\\b");
        Pattern pRoGil = Pattern.compile("\\b([가-힣A-Za-z·\\d~\\-\\.]+로)\\s*([가-힣A-Za-z·\\d~\\-\\.]+길)\\b");
        Pattern pGu = Pattern.compile("\\b([가-힣]+구)\\b");
        Pattern pRo = Pattern.compile("\\b([가-힣A-Za-z·\\d~\\-\\.\\s]+로)\\b");
        Pattern pGil = Pattern.compile("\\b([가-힣A-Za-z·\\d~\\-\\.\\s]+길)\\b");

        Matcher mAll = pAll.matcher(candidateAddress);
        Matcher mGuRo = pGuRo.matcher(candidateAddress);
        Matcher mGuGil = pGuGil.matcher(candidateAddress);
        Matcher mRoGil = pRoGil.matcher(candidateAddress);
        Matcher mGu = pGu.matcher(candidateAddress);
        Matcher mRo = pRo.matcher(candidateAddress);
        Matcher mGil = pGil.matcher(candidateAddress);

        String result = "";

        if(mAll.find()){
            result = mAll.group();
        }else if(mGuRo.find()){
            result = mGuRo.group();
        }else if(mGuGil.find()){
            result = mGuGil.group();
        }else if(mRoGil.find()){
            result = mRoGil.group();
        }
        else if(mGu.find()) {
            result = mGu.group();
        }
        else if(mRo.find()){
            result = mRo.group();
        }
        else if(mGil.find()){
            result = mGil.group();
        }

        return result;
    }

    @Test
    public void test4(){
        String in = "전구 연수구 컨벤시아대로130번길 자이아파트로";
        Pattern pAll = Pattern.compile("\\b([가-힣]+구)\\s*([가-힣A-Za-z·\\d~\\-\\.]+로)\\s*([가-힣A-Za-z·\\d~\\-\\.]+길)\\b");
        Pattern pGuRo = Pattern.compile("\\b([가-힣]+구)\\s*([가-힣A-Za-z·\\d~\\-\\.]+로)\\b");
        Pattern pGuGil = Pattern.compile("\\b([가-힣]+구)\\s*([가-힣A-Za-z·\\d~\\-\\.]+길)\\b");
        Pattern pRoGil = Pattern.compile("\\b([가-힣A-Za-z·\\d~\\-\\.]+로)\\s*([가-힣A-Za-z·\\d~\\-\\.]+길)\\b");
        Matcher mAll = pAll.matcher(in);
        Matcher mGuRo = pGuRo.matcher(in);
        Matcher mGuGil = pGuGil.matcher(in);
        Matcher mRoGil = pRoGil.matcher(in);

        String result = "";
        if(mAll.find()){
            System.out.println(mAll.group(1));
            System.out.println(mAll.group(2));
            System.out.println(mAll.group(3));
            result = mAll.group();
        }else if(mGuRo.find()){
            result = mGuRo.group();
        }else if(mGuGil.find()){
            result = mGuGil.group();
        }else if(mRoGil.find()){
            result = mRoGil.group();
        }

        System.out.println(result);
    }

}
