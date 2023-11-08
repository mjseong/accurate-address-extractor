package com.sundaydev.aas;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressSearchTest {

    @Test
    public void searchTest(){
        String addr = "성남시, 분시 당구 구로구 디 지 털로 30번길 삼 성 로 2 0 길 28 푸른마을 아파트로 보내주세요!!";

        String[] words = addr.split(" ");
        String si = null;
        String gu = null;
        String road = null;
        String candidateAddr = "";

        Pattern siPattern = Pattern.compile("^(.*[가-힣]{2,}(시))");
        Pattern guPattern = Pattern.compile("^(.*[가-힣]{1,}(구))");

        Pattern roadPattern = Pattern.compile("([가-힣A-Za-z·\\d~\\-\\.]{1,}(로|길)([\\d]+)?)");
        Pattern accurateRoadPattern  = Pattern.compile("([가-힣A-Za-z·\\d~\\-\\.]{2,}(로|길)\\d+)");

        Set<String> citySet = new LinkedHashSet<>();
        Set<String> guSet = new LinkedHashSet<>();
        Set<String> roadSet = new LinkedHashSet<>();
        Set<String> candidateAddrSet = new LinkedHashSet<>();

        for(String word: words){
            Matcher siM = siPattern.matcher(word);
            Matcher guM = guPattern.matcher(word);
            Matcher roadM = roadPattern.matcher(word);

            //city
            if(siM.find()){
                si = siM.group(1);
                citySet.add(si);
            }
            //gu
            if(guM.find()){
                gu = guM.group(1);
                guSet.add(gu);
            }

            //road
            if(roadM.find()){
                road = roadM.group();
                roadSet.add(road);
            }

            //split word candidateAddress
            if(word.length() < 2){
                candidateAddr += word;

                if(word.endsWith("로")||word.endsWith("길")){
                    candidateAddrSet.add(candidateAddr);
                    candidateAddr = "";
                }
            }
        }

        System.out.println(citySet);
        System.out.println(guSet);
        System.out.println(roadSet);
        System.out.println(candidateAddrSet);
    }
}
