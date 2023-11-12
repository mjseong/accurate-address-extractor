package com.sundaydev.aas.common;

public class Constant {

    public final static String CITY_REGX = "^(.*[가-힣]{2,}(시))";
    public final static String GU_REGX = "^(.*[가-힣]{2,}(시))?(.*[가-힣]{1,}(구))";
    public final static String ROAD_RO_REGX = "^(.*[가-힣]{1,}(구|\\s))?(.*[가-힣A-Za-z·\\d~\\-\\.]{1,}(로))";
    public final static String ROAD_GIL_REGX = "^(.*[가-힣A-Za-z·\\d~\\-\\.]{1,}(로))?(.*[가-힣A-Za-z·\\d~\\-\\.]{1,}(길))";
    public final static String ROAD_DST_REGX = "([가-힣A-Za-z·\\d~\\-\\.]{1,}(로|길)([\\d]+)?)";
    public final static String ROAD_ACCURATE_REGX = "([가-힣A-Za-z·\\d~\\-\\.]{1,}(로|길)\\d+)";

    public final static String KO_CITY = "시";
    public final static String KO_GU = "구";
    public final static String KO_RO = "로";
    public final static String KO_GIL = "길";

    public final static String KEY_CITY = "CITY";
    public final static String KEY_GU = "GU";
    public final static String KEY_RO = "RO";
    public final static String KEY_GIL = "GIL";


    public final static String addrPattern2 = "^(.*[가-힣]+구)?\\s*(.*[로길]+)\\s*(.*)";
    public final static String specialRemovePattern = "[^a-zA-Z가-힣0-9\\s]";
    public final static String delimiter = ",";
    public final static String csvPattern = "\"(.*?)\",|([^,]+),";

    public final static String KoreaDistrict = "강남구,강동구,강북구,강서구,관악구,광진구,구로구,금천구,노원구," +
            "도봉구,동대문구,동작구,마포구,서대문구,서초구,성동구,성북구,송파구," +
            "양천구,영등포구,용산구,은평구,종로구,중구,중랑구," +
            "강서구,금정구,남구,동구,동래구,부산진구,북구,사상구,사하구,서구," +
            "수영구,연제구,영도구,중구,해운대구," +
            "중구,동구,미추홀구,연수구,남동구,부평구,계양구,서구," +
            "남구,달서구,동구,북구,서구,수성구,중구," +
            "광산구,남구,동구,북구,서구," +
            "대덕구,동구,서구,유성구,중구," +
            "남구,동구,북구,중구," +
            "권선구,영통구,장안구,팔달구,분당구,수정구,중원구,동안구,만안구,덕양구,일산동구,일산서구,단원구,상록구," +
            "기흥구,수지구,처인구,원미구,소사구,오정구,상당구,흥덕구,청원구,서원구,동남구,서북구,덕진구,완산구,남구,북구," +
            "의창구,성산구,마산합포구,마산회원구,진해구";


}
