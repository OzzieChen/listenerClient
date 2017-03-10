package net.xssu.client.util;

/**
 * Cast operation util class
 * Created by ozziechen on 16/3/13.
 */
public class CastUtil {

    /**
     * Cast to String
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * Cast to String with default value
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * Cast to double
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     * Cast to double with default value
     */
    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return doubleValue;
    }

    /**
     * Cast to long
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /**
     * Cast to long with defaultValue
     */
    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return longValue;
    }

    /**
     * Cast to int
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * Cast to int with default value
     */
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return intValue;
    }

    /**
     * Cast to boolean
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * Cast to boolean with default value
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }

}
