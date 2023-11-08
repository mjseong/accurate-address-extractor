package com.sundaydev.aas.common;

public class Constant {
    public final static String addrPattern = "([가-힣A-Za-z·\\d~\\-\\.]{2,}(로|길)\\d+)";
    //^(.*[가-힣]+구)?\s*(.*[로길]+)\s*(.*)
    public final static String addrPattern2 = "^(.*[가-힣]+구)?\\s*(.*[로길]+)\\s*(.*)";
    public final static String specialRemovePattern = "[^a-zA-Z가-힣0-9\\s]";
    public final static String delimiter = ",";
    public final static String csvPattern = "\"(.*?)\",|([^,]+),";


}
