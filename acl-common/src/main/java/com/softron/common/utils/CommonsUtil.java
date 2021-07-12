package com.softron.common.utils;

import java.util.List;
import java.util.stream.Collectors;

public final class CommonsUtil {

    private CommonsUtil() {

    }

    public static String listToString(List<String> list) {
        return listToString(list, ",");
    }

    public static String listToString(List<String> list, String delimeter) {
        return list.stream().collect(Collectors.joining(delimeter));
    }
}
