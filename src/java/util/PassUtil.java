/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.security.SecureRandom;

/**
 *
 * @author ushiho
 */
public class PassUtil {

    private static SecureRandom random = new SecureRandom();
    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";

    public PassUtil() {
    }

    public static String passToSh1(String pass) {
        return HashageUtil.sha256(pass);
    }

    public static boolean testTwoPasswords(String pass1, String pass2) {
        return passToSh1(pass1).equals(pass2);
    }

    public static String generatePass(int len, int dic) {
        return generate(len, dic);
    }

    public static String generate(int len, int dic) {
        String result = "";
        String decimal = dicimales(dic);
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(decimal.length());
            result += decimal.charAt(index);
        }
        return result;
    }

    public static String dicimales(int dic) {
        switch (dic) {
            case 1:
                return NUMERIC;
            case 2:
                return ALPHA;
            case 3:
                return ALPHA_CAPS;
            case 4:
                return NUMERIC + ALPHA + ALPHA_CAPS;
        }
        return "Pas de string";
    }
}
