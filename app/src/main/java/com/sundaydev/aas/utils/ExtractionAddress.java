package com.sundaydev.aas.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractionAddress {

//    private final static Pattern CITY_PATTERN = Pattern.compile(Constant.CITY_REGX);
//    private final static Pattern GU_PATTERN = Pattern.compile(Constant.GU_REGX);
//    private final static Pattern ROAD_DST_PATTERN = Pattern.compile(Constant.ROAD_DST_REGX);
//    private final static Pattern ROAD_GU_PATTERN = Pattern.compile(Constant.ROAD_RO_REGX);
//    private final static Pattern ROAD_GIL_PATTERN = Pattern.compile(Constant.ROAD_GIL_REGX);

    private final static Pattern pAGuRoGil = Pattern.compile(KoreaRoadAddressRegxType.P_A_GU_RO_GIL.getRegx());
    private final static Pattern pAGuRo = Pattern.compile(KoreaRoadAddressRegxType.P_A_GU_RO.getRegx());
    private final static Pattern pAGuGil = Pattern.compile(KoreaRoadAddressRegxType.P_A_GU_GIL.getRegx());
    private final static Pattern pARoGil = Pattern.compile(KoreaRoadAddressRegxType.P_A_RO_GIL.getRegx());
    private final static Pattern pARo = Pattern.compile(KoreaRoadAddressRegxType.P_A_RO.getRegx());

    private final static Pattern pRoGil = Pattern.compile(KoreaRoadAddressRegxType.P_RO_GIL.getRegx());
    private final static Pattern pGu = Pattern.compile(KoreaRoadAddressRegxType.P_GU.getRegx());
    private final static Pattern pRo = Pattern.compile(KoreaRoadAddressRegxType.P_RO.getRegx());
    private final static Pattern pGil = Pattern.compile(KoreaRoadAddressRegxType.P_GIL.getRegx());

    private final static Pattern pWord = Pattern.compile("\\b[구|로|길]+");


    public static Set<String> extractAddressWithTokenizer(String candidateAddress) {
        candidateAddress = candidateAddress.replaceAll("[^a-zA-Z0-9가-힣\\s]", "");
        List<String> words = Arrays.asList(candidateAddress.split(" "));
        List<String> seqList = new ArrayList<>();
        System.out.println(words);

        StringJoiner stringJoiner = new StringJoiner(" ");
        String mergeWord = "";
        String beforeWord = "";

        //preProcess
        for(String word: words) {

            mergeWord = mergeWordWithCondition(mergeWord, word, beforeWord);

            ProcessData processData = preProcess(mergeWord);

            if(processData != null
                    && !processData.result().isBlank()){
                seqList.add(processData.result());
                stringJoiner.add(processData.result());
                if(processData.remain()!=null){
                    mergeWord = processData.remain();
                }else {
                    mergeWord = "";
                }
            }
            beforeWord = word;
        }

        System.out.println("preProcess result: " + seqList);

        Set<String> candidateSet = new LinkedHashSet<>();

        //postProcess
        for(int i = seqList.size()-1; i>=0; i--){
            String subStr = "";

            for(int j=0; j <= i; j++){
                String seqWord = seqList.get(j);

                //앞 대상의 규칙에 따라 뒤에 글자의 공백들을 제거한다.
                if((subStr.endsWith("구") && seqWord.endsWith("로"))
                        ||(subStr.endsWith("로") && seqWord.endsWith("길"))
                        ||(subStr.endsWith("구") && seqWord.endsWith("길"))){
                    seqWord = seqWord.replaceAll(" ", "");
                }

                subStr = subStr +" "+ seqWord;
            }

            Set result = postProcess(subStr.trim());

            if(!result.isEmpty()){
                candidateSet.addAll(result);
            }
        }

        System.out.println("postProcess candidateSet: " + candidateSet);
        return candidateSet;
    }

    private static String mergeWordWithCondition(String mergeWord, String word, String beforeWord){

        Matcher m = pWord.matcher(word);
        String result = "";

        //마지막 글자가 한글자일 경우 공백이 있는지 검사.
        int i = mergeWord.lastIndexOf(" ");
        String whiteSpaceWord = "";
        if(i>1){
            whiteSpaceWord = mergeWord.substring(i, mergeWord.length());
            if(whiteSpaceWord.length() > 2){
                whiteSpaceWord = "";
            }
        }
        if(m.find()){ //구,로,길 앞에 글자가 없다면 merge함
            result = mergeWord + word;
        }else{
            //나머지는 글자 공백 그대로 유지
            if(!whiteSpaceWord.isBlank()){ //문장중 맨뒤 글자 앞 공백이있으면 병합함.
                result = mergeWord + word;
            }else if(beforeWord.length()==1){
                result = mergeWord + word;
            }
            else{ //나머지 2글자 이상 병합은 문자 공백을 준다.
                result = mergeWord +" "+ word;
            }
        }
        return result;
    }

    private static ProcessData preProcess(String candidateAddress){
        Matcher mGu = pGu.matcher(candidateAddress);
        Matcher mRo = pRo.matcher(candidateAddress);
        Matcher mGil = pGil.matcher(candidateAddress);
        Matcher mRoGil = pRoGil.matcher(candidateAddress);

        ProcessData processData = null;

        if(mRoGil.find()){
            String result = mRoGil.group(1).replaceAll(" ", "")
                    +" "+ mRoGil.group(2).replaceAll(" ", "");

            processData = new ProcessData(result, null);
        }
        else if(mGu.find()) {
            String result = mGu.group(1) != null ? mGu.group(1): mGu.group(2);
            String remain = mGu.group(3) != null ? mGu.group(3) : null;

            processData = new ProcessData(result, remain);
        }
        else if(mRo.find()){
            String result = mRo.group(1) != null ?
                    mRo.group(1) : mRo.group(2);
            String remain = mRo.group(3) != null ? mRo.group(3) : null;

            processData = new ProcessData(result, remain);
        }
        else if(mGil.find()){
            String result = mGil.group(1) != null ?
                    mGil.group(1): mGil.group(2);
            processData = new ProcessData(result, null);
        }

        return processData;
    }

    private static Set postProcess(String candidateAddress){

        Set postSet = new LinkedHashSet();
        Matcher mGUROGIL = pAGuRoGil.matcher(candidateAddress);
        Matcher mGuRo = pAGuRo.matcher(candidateAddress);
        Matcher mGuGil = pAGuGil.matcher(candidateAddress);
        Matcher mRoGil = pARoGil.matcher(candidateAddress);
        Matcher mRo = pARo.matcher(candidateAddress);

        if(mGUROGIL.find()){
            postSet.add(mGUROGIL.group());
        }

        if(mGuRo.find()){
            postSet.add(mGuRo.group());
        }

        if(mGuGil.find()){
            postSet.add(mGuGil.group());
        }

        if(mRoGil.find()){
            postSet.add(mRoGil.group());
        }

        if(mRo.find()){
            postSet.add(mRo.group());
        }

        return postSet;
    }



//    public static Map extractAddressWithRegx(String candidateAddress){
//        List<String> cityList = new ArrayList<>();
//        List<String> guList = new ArrayList<>();
//        List<String> roadRList = new ArrayList<>();
//        List<String> roadGList = new ArrayList<>();
//        List<String> sequenceList = new ArrayList<>();
//
////        candidateAddress = candidateAddress.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", " ");
//        String[] words = candidateAddress.split(" ");
//        String candidateAddr = "";
//
//        for(String word: words) {
//            Matcher cityMatcher = CITY_PATTERN.matcher(word);
//            Matcher guMatcher = GU_PATTERN.matcher(word);
//            Matcher roadMatcher = ROAD_DST_PATTERN.matcher(word);
//
//            //
//            if(cityMatcher.find()){
//                cityList.add(cityMatcher.group(1));
//                sequenceList.add(cityMatcher.group(1));
//            }
//
//            //
//            if(guMatcher.find()){
//                String gu = guMatcher.group(3).replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣]","");
//                guList.add(gu);
//                sequenceList.add(gu);
//            }
//
//            if(roadMatcher.find()){
//                String road = roadMatcher.group();
//                Matcher roMatcher = ROAD_GU_PATTERN.matcher(road);
//                Matcher gilMatcher = ROAD_GIL_PATTERN.matcher(road);
//                if(roMatcher.find()){
//                    roadRList.add(roMatcher.group(3));
//                    sequenceList.add(roMatcher.group(3));
//                }
//                if(gilMatcher.find()) {
//                    roadGList.add(gilMatcher.group(3));
//                    sequenceList.add(gilMatcher.group(3));
//                }
//            }
//
//            if(word.length() < 2){
//                candidateAddr += word;
//                if(word.endsWith(Constant.KO_RO)){
//                    roadRList.add(candidateAddr);
//                    sequenceList.add(candidateAddr);
//                    candidateAddr = "";
//                } else if(word.endsWith(Constant.KO_GIL)){
//                    roadGList.add(candidateAddr);
//                    sequenceList.add(candidateAddr);
//                    candidateAddr = "";
//                }
//            }
//        }
//
//        System.out.println(sequenceList);
//        Map map = Map.of(
//                Constant.KEY_CITY,cityList,
//                Constant.KEY_GU,guList,
//                Constant.KEY_RO,roadRList,
//                Constant.KEY_GIL,roadGList
//        );
//
//        return map;
//    }



}
