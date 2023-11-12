package com.sundaydev.addr.kis.common.utils;

import com.sundaydev.addr.kis.common.AddressConstant;
import com.sundaydev.addr.kis.common.KoreaRoadAddressRegxType;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractionAddress {

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
//        System.out.println("input words: " + words);

        String mergeWord = "";
        String beforeWord = "";

        //preProcess
        for(String word: words) {

            mergeWord = mergeWordWithCondition(mergeWord, word, beforeWord);

            PreprocessToken processToken = preprocessTokenization(mergeWord);
            //System.out.println(mergeWord);
            if(processToken != null
                    && !processToken.result().isBlank()){
                seqList.add(processToken.result());

                if(processToken.remain()!=null){
                    mergeWord = processToken.remain();
                }else {
                    mergeWord = "";
                }
            }
            beforeWord = word;
        }

        //System.out.println("preProcess result: " + seqList);

        Set<String> candidateSet = new LinkedHashSet<>();

        //postProcess
        for(int i = seqList.size()-1; i>=0; i--){
            String subStr = "";

            for(int j=0; j <= i; j++){
                String seqWord = seqList.get(j);

                //앞 대상의 규칙에 따라 뒤에 글자의 공백들을 제거한다.
                if((subStr.endsWith(AddressConstant.KO_GU) && seqWord.endsWith(AddressConstant.KO_RO))
                        ||(subStr.endsWith(AddressConstant.KO_RO) && seqWord.endsWith(AddressConstant.KO_GIL))
                        ||(subStr.endsWith(AddressConstant.KO_GU) && seqWord.endsWith(AddressConstant.KO_GIL))){

                    Matcher pMGuro = pAGuRo.matcher(seqWord);
                    Matcher pMRogil = pARoGil.matcher(seqWord);
                    Matcher pMGugil = pAGuGil.matcher(seqWord);
                    if(!pMGuro.find() && !pMRogil.find() && !pMGugil.find()){
                        seqWord = seqWord.replaceAll(" ", "");
                    }
                }

                subStr = subStr +" "+ seqWord;
            }

            Set result = postprocessTokenization(subStr.trim());

            if(!result.isEmpty()){
                candidateSet.addAll(result);
            }
        }

        //System.out.println("postProcess candidateSet: " + candidateSet);
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

    /**
     * 로/길, 구, 로, 길 tokenizer 처리하기 위한 전처리 method
     * @param candidateAddress
     * @return {@link PreprocessToken}
     */
    private static PreprocessToken preprocessTokenization(String candidateAddress){
        Matcher mGu = pGu.matcher(candidateAddress);
        Matcher mRo = pRo.matcher(candidateAddress);
        Matcher mGil = pGil.matcher(candidateAddress);
        Matcher mRoGil = pRoGil.matcher(candidateAddress);

        PreprocessToken processData = null;

        if(mRoGil.find()){
            String result = mRoGil.group(1)
                    +" "+ mRoGil.group(2);

            processData = new PreprocessToken(result, null);
        }
        else if(mGu.find()) {
            String result = mGu.group(1) != null ? mGu.group(1): mGu.group(2);
            String remain = mGu.group(3) != null ? mGu.group(3) : null;

            processData = new PreprocessToken(result, remain);
        }
        else if(mRo.find()){
            String result = mRo.group(1) != null ?
                    mRo.group(1) : mRo.group(2);
            String remain = mRo.group(3) != null ? mRo.group(3) : null;

            processData = new PreprocessToken(result, remain);
        }
        else if(mGil.find()){
            String result = mGil.group(1) != null ?
                    mGil.group(1): mGil.group(2);
            processData = new PreprocessToken(result, null);
        }

        return processData;
    }

    /**
     * 전처리 이후 candidateAddress가될 후보군을 검증하는 method
     * @param candidateAddress
     * @return {@link LinkedHashSet}
     */
    private static Set postprocessTokenization(String candidateAddress){

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

}
