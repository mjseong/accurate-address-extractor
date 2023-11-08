package com.sundaydev.aas.common;

public class Constant {

    public final static String CITY_REGX = "^(.*[가-힣]{2,}(시))";
    public final static String GU_REGX = "^(.*[가-힣]{2,}(시))?(.*[가-힣]{1,}(구))";
    public final static String ROAD_RO_REGX = "^(.*[가-힣]{1,}(구))?(.*[가-힣A-Za-z·\\d~\\-\\.]{1,}(로))";
    public final static String ROAD_GIL_REGX = "^(.*[가-힣A-Za-z·\\d~\\-\\.]{1,}(로))?(.*[가-힣A-Za-z·\\d~\\-\\.]{1,}(길))";
    public final static String ROAD_DST_REGX = "([가-힣A-Za-z·\\d~\\-\\.]{1,}(로|길)([\\d]+)?)";
    public final static String ROAD_ACCURATE_REGX = "([가-힣A-Za-z·\\d~\\-\\.]{1,}(로|길)\\d+)";


    public final static String addrPattern2 = "^(.*[가-힣]+구)?\\s*(.*[로길]+)\\s*(.*)";
    public final static String specialRemovePattern = "[^a-zA-Z가-힣0-9\\s]";
    public final static String delimiter = ",";
    public final static String csvPattern = "\"(.*?)\",|([^,]+),";


}
