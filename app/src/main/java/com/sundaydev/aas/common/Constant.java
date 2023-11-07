package com.sundaydev.aas.common;

public class Constant {
    public final static String addrPattern = "(.*?)구\\s(.*?)(길|로)\\s.*";
    //^(.*[가-힣]+구)?\\s*((?:[가-힣0-9]+[로길]+\\s*)+)\\s*(.*)
    //^(.*[가-힣]+구)?\s*(.*[로길]+)\s*(.*)
    public final static String addrPattern2 = "^(.*[가-힣]+구)?\\s*(.*[로길]+)\\s*(.*)";
    public final static String specialRemovePattern = "[^a-zA-Z가-힣0-9\\s]";
    public final static String delimiter = ",";
    public final static String csvPattern = "\"(.*?)\",|([^,]+),";


}
