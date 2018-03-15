package br.org.venturus.newyorktimesreader.infra.utils;

public class StringUtils {

    private StringUtils(){}

    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || isEmpty(cs.toString());
    }

}
