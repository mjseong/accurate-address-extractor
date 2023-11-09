package com.sundaydev.aas.utils;

import com.sundaydev.aas.common.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractionAddress {

    private final static Pattern CITY_PATTERN = Pattern.compile(Constant.CITY_REGX);
    private final static Pattern GU_PATTERN = Pattern.compile(Constant.GU_REGX);
    private final static Pattern ROAD_DST_PATTERN = Pattern.compile(Constant.ROAD_DST_REGX);
    private final static Pattern ROAD_GU_PATTERN = Pattern.compile(Constant.ROAD_RO_REGX);
    private final static Pattern ROAD_GIL_PATTERN = Pattern.compile(Constant.ROAD_GIL_REGX);
    private final static Pattern ROAD_ACCURATE_PATTERN = Pattern.compile(Constant.ROAD_ACCURATE_REGX);

    @Deprecated
    public static String extractAddressWithRegx(String candidateAddress){

        String rawAddress = candidateAddress.replaceAll(Constant.specialRemovePattern, "");
        Pattern addressPattern = Pattern.compile(Constant.addrPattern2);
        Matcher matcher = addressPattern.matcher(rawAddress);

        String gu = null;
        String road = null;


        List<String> roads = new ArrayList();
        if(matcher.find()){

            gu = matcher.group(1);
            road = matcher.group(2);

//            road = road.replaceAll(" ", "");
            Pattern roadPattern = Pattern.compile("(.+?)(로|길|$)");
            Matcher roadMatcher = roadPattern.matcher(road);


            while (roadMatcher.find()){
                roads.add(roadMatcher.group());
            }

        }
        road = roads.get(0);

        if(gu != null && road != null){
            return String.format("%s %s",gu, road);
        }else if(gu != null){
            return gu;
        }else if(road != null){
            return road;
        }

        return "미출력";
    }

    public static String extractAddress(String candidateAddress){
        List<String> cityList = new ArrayList<>();
        List<String> guList = new ArrayList<>();
        List<String> roadRList = new ArrayList<>();
        List<String> roadGList = new ArrayList<>();

//        candidateAddress = candidateAddress.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", " ");
        String[] words = candidateAddress.split(" ");
        String candidateAddr = "";

        for(String word: words) {
            Matcher cityMatcher = CITY_PATTERN.matcher(word);
            Matcher guMatcher = GU_PATTERN.matcher(word);
            Matcher roadMatcher = ROAD_DST_PATTERN.matcher(word);

            //
            if(cityMatcher.find()){
                cityList.add(cityMatcher.group(1));
            }

            //
            if(guMatcher.find()){
                String gu = guMatcher.group(3).replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣]","");
                guList.add(gu);
            }

            if(roadMatcher.find()){
                String road = roadMatcher.group();
                Matcher roMatcher = ROAD_GU_PATTERN.matcher(road);
                Matcher gilMatcher = ROAD_GIL_PATTERN.matcher(road);
                if(roMatcher.find()){
                    roadRList.add(roMatcher.group(3));
                }
                if(gilMatcher.find()) {
                    roadGList.add(gilMatcher.group(3));
                }
            }

            if(word.length() < 2){
                candidateAddr += word;
                if(word.endsWith("로")){
                    roadRList.add(candidateAddr);
                    candidateAddr = "";
                } else if(word.endsWith("길")){
                    roadGList.add(candidateAddr);
                    candidateAddr = "";
                }
            }
        }

        System.out.println(cityList);
        System.out.println(guList);
        System.out.println(roadRList);
        System.out.println(roadGList);

        StringJoiner stringJoiner = new StringJoiner(" ");

        if(!cityList.isEmpty()){
            stringJoiner.add(cityList.get(0));
        }

        if(!guList.isEmpty()){
            stringJoiner.add(guList.get(0));
        }

        if(!roadRList.isEmpty()){
            stringJoiner.add(roadRList.get(0));
        }

        if(!roadGList.isEmpty()) {
            stringJoiner.add(roadGList.get(0));
        }
        System.out.println(stringJoiner.toString().trim());

        return "";
    }

}
