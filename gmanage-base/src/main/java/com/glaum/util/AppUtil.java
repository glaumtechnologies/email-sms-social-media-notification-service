package com.glaum.util;

import java.util.List;

public class AppUtil {

    public static String concatenate(List<String> strings, String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            stringBuilder.append(string).append(delimiter);
        }
        return stringBuilder.toString();
    }

}
