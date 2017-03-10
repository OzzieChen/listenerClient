package net.xssu.client.util;

/**
 * Utils for String
 * Created by ozziechen on 16/3/16.
 */
public class StringUtil {
    /**
     * Decide whether String is empty
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return str.length() == 0 ? true : false;
    }

    /**
     * Decide whether String is not empty
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Divide string by separator
     * @param str The String to be divided
     * @param separator (String type)
     * @return
     */
    public static String[] divideToStrArray(String str, String separator) {
        String[] strArray = null;
        strArray = str.split(separator);
        return strArray;
    }

}
