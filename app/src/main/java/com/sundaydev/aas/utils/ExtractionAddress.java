package com.sundaydev.aas.utils;

import com.sundaydev.aas.common.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractionAddress {

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

    public static String extractAddressWithRoadRegx(String candidateAddress){
        Pattern addressPattern = Pattern.compile(Constant.addrPattern);
        Matcher matcher = addressPattern.matcher(candidateAddress);

        String addr;
        String addr2;

        if(matcher.find()){
            addr = matcher.group(1);
            addr2 = matcher.group(2);
            System.out.println(addr+" "+addr2);
        }

        return "";
    }
}
