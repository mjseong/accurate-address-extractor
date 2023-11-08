package com.sundaydev.aas;

import com.sundaydev.aas.utils.ExtractionAddress;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressSearchTest {

    @Test
    public void test(){
        String addr = "성남, 분당 백 현 로 265, 푸른마을 아파트로 보내주세요!! ";
        ExtractionAddress.extractAddress(addr);
    }

    @Test
    public void searchTest(){
        String addr = "성남,분당구백현로265,푸른마을아파트로보내주세요!!";
        addr = addr.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]"," ");
        String[] words = addr.split(" ");
        String si = null;
        String gu = null;
        String road = null;
        String candidateAddr = "";

        Pattern siPattern = Pattern.compile("^(.*[가-힣]{2,}(시))");
        Pattern guPattern = Pattern.compile("^(.*[가-힣]{1,}(구))");

        Pattern roadPattern = Pattern.compile("([가-힣A-Za-z·\\d~\\-\\.]{1,}(로|길)([\\d]+)?)");
        Pattern accurateRoadPattern  = Pattern.compile("([가-힣A-Za-z·\\d~\\-\\.]{2,}(로|길)\\d+)");

        List<String> cityList = new ArrayList<>();
        List<String> guList = new ArrayList<>();
        List<String> roadRList = new ArrayList<>();
        List<String> roadGList = new ArrayList<>();

        for(String word: words){
            Matcher siM = siPattern.matcher(word);
            Matcher guM = guPattern.matcher(word);
            Matcher roadM = roadPattern.matcher(word);

            //city
            if(siM.find()){
                si = siM.group(1);
                cityList.add(si);
            }
            //gu
            if(guM.find()){
                gu = guM.group(1);
                guList.add(gu);
            }

            //road
            if(roadM.find()){
                road = roadM.group();
                System.out.println(roadM.group(1));
                if(road.endsWith("로")){
                    roadRList.add(road);
                }else if(road.endsWith("길")) {
                    roadGList.add(road);
                }
            }

            //split word candidateAddress
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
            if(!roadGList.isEmpty()){
                String fullRoad = roadRList.get(0)+roadGList.get(0);
                //valid accurate RoadName pattern
                if(accurateRoadPattern.matcher(fullRoad).find()){
                    stringJoiner.add(fullRoad);
                }
            }else{
                stringJoiner.add(roadRList.get(0));
            }
        }

        System.out.println(stringJoiner.toString().trim());

    }
}
