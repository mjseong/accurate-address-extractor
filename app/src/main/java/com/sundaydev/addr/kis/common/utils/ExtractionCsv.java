package com.sundaydev.addr.kis.common.utils;

import com.sundaydev.addr.kis.common.AddressConstant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractionCsv {

    public static List<String> extractCsv(Path path){

        try {
            List<String> addresses = new ArrayList<>();
            Pattern csvPattern = Pattern.compile(AddressConstant.csvPattern);
            Files.readAllLines(path)
                    .forEach(line -> {
                        Matcher m = csvPattern.matcher(line);
                        while(m.find()){
                            String value = m.group(1);
                            if(value != null){
                                addresses.add(value);
                            }else{
                                addresses.add(m.group(2));
                            }
                        }
                    });

            return addresses;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
