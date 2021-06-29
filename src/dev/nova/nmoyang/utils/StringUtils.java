package dev.nova.nmoyang.utils;

public class StringUtils {

    /**
     *
     * THIS CODE IS NOT MINE!
     *
     * It was grabbed from {@link org.apache.commons.lang3.StringUtils} for compatibility issues.
     *
     */
    public static String substringBetween(String str, String open, String close) {
        if (str != null && open != null && close != null) {
            int start = str.indexOf(open);
            if (start != -1) {
                int end = str.indexOf(close, start + open.length());
                if (end != -1) {
                    return str.substring(start + open.length(), end);
                }
            }
            return null;
        } else {
            return null;
        }
    }
}
