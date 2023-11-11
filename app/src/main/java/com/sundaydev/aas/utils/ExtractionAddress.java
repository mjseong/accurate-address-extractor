package com.sundaydev.aas.utils;

import com.sundaydev.aas.common.Constant;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractionAddress {

//    private final static Pattern CITY_PATTERN = Pattern.compile(Constant.CITY_REGX);
//    private final static Pattern GU_PATTERN = Pattern.compile(Constant.GU_REGX);
//    private final static Pattern ROAD_DST_PATTERN = Pattern.compile(Constant.ROAD_DST_REGX);
//    private final static Pattern ROAD_GU_PATTERN = Pattern.compile(Constant.ROAD_RO_REGX);
//    private final static Pattern ROAD_GIL_PATTERN = Pattern.compile(Constant.ROAD_GIL_REGX);

    private final static Pattern pGUROGIL = Pattern.compile(KoreaRoadAddressRegxType.P_GU_RO_GIL.getRegx());
    private final static Pattern pGuRo = Pattern.compile(KoreaRoadAddressRegxType.P_GU_RO.getRegx());
    private final static Pattern pGuGil = Pattern.compile(KoreaRoadAddressRegxType.P_GU_GIL.getRegx());
    private final static Pattern pRoGil = Pattern.compile(KoreaRoadAddressRegxType.P_RO_GIL.getRegx());
    private final static Pattern pGu = Pattern.compile(KoreaRoadAddressRegxType.P_GU.getRegx());
    private final static Pattern pRo = Pattern.compile(KoreaRoadAddressRegxType.P_RO.getRegx());
    private final static Pattern pGil = Pattern.compile(KoreaRoadAddressRegxType.P_GIL.getRegx());


    public static Set<String> extractAddressWithTokenizer(String candidateAddress) {
        candidateAddress = candidateAddress.replaceAll("[^a-zA-Z0-9가-힣\\s]", "");
        candidateAddress = candidateAddress.replaceAll(" ", " ,");
        List<String> words = Arrays.asList(candidateAddress.split(","));
        List<String> seqList = new ArrayList<>();
        System.out.println(words);

        StringJoiner stringJoiner = new StringJoiner(" ");
        String mergeWord = "";
        //preProcess
        for(String word: words) {
            if(word.length() == 2 && word.contains(" ")){
                mergeWord += word.trim();
            }else{
                mergeWord += word;
            }
            System.out.println(mergeWord);
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
        }

        System.out.println("preProcess result: " + seqList);

        Set<String> candidateSet = new LinkedHashSet<>();
        String seqTmp ="";

        //postProcess
        for(String seqStr:seqList){
//            String candi = concatWord(seqStr);
//            candidateSet.add(candi);
        }

        System.out.println("candidateSet: " + candidateSet);
        return candidateSet;
    }

//    private static String concatWord(String sequence){
//        List<String> words = List.of(sequence.split(" "));
//        String mergeWord = "";
//        for(String word: words){
//            ProcessData processData = preProcess(word);
//            if(processData != null &&
//                    !processData.result().isBlank()){
//                mergeWord += processData.result();
//                continue;
//            }
//            mergeWord = word;
//        }
////        System.out.println(mergeWord);
//        return mergeWord;
//    }

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
            processData = new ProcessData(result, null);
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

    private static String postProcess(String candidateAddress){
        Matcher mGUROGIL = pGUROGIL.matcher(candidateAddress);
        Matcher mGuRo = pGuRo.matcher(candidateAddress);
        Matcher mGuGil = pGuGil.matcher(candidateAddress);
        Matcher mRoGil = pRoGil.matcher(candidateAddress);
        Matcher mRo = pRo.matcher(candidateAddress);


        return null;
    }

    private static String checkAddressRegx(String candidateAddress){

        Matcher mGUROGIL = pGUROGIL.matcher(candidateAddress);
        Matcher mGuRo = pGuRo.matcher(candidateAddress);
        Matcher mGuGil = pGuGil.matcher(candidateAddress);
        Matcher mRoGil = pRoGil.matcher(candidateAddress);
        Matcher mRo = pRo.matcher(candidateAddress);

        String result = "";
        if(mGUROGIL.find()){
            result =  mGUROGIL.group();
        }
        else if(mGuRo.find()){
            result =  mGuRo.group(1).replaceAll(" ", "") +" "+ mGuRo.group(2).replaceAll(" ", "");
        }
        else if(mGuGil.find()){
            result = mGuGil.group(1).replaceAll(" ", "") + " " + mGuGil.group(2).replaceAll(" ", "");
        }
        else if(mRoGil.find()){
            result = mRoGil.group(1).replaceAll(" ", "") +" "+ mRoGil.group(2).replaceAll(" ", "");
        }
        return result;
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
