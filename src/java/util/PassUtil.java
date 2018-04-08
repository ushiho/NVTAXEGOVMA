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

    public String passToSh1(String pass) {
        System.out.println("lhashage :"+HashageUtil.sha256(pass));
        System.out.println("pssd : "+HashageUtil.sha256(pass));
        return HashageUtil.sha256(pass);
    }

    public boolean testTwoPasswords(String pass1, String pass2) {
        System.out.println("pass1 : "+passToSh1(pass1));
        System.out.println("pass2 : "+pass2);
        return passToSh1(pass1).equals(pass2);
    }

    public String generatePassAndHash(int len, int dic) {
        String result = generate(len, dic);
        return passToSh1(result);
    }

    public String generate(int len, int dic) {
        String result = "";
        String decimal = dicimales(dic);
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(decimal.length());
            result += decimal.charAt(index);
        }
        return result;
    }

    public String dicimales(int dic) {
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
