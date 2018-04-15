/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.util.Arrays;


/**
 *
 * @author ushiho
 */
public class sh256 {

    public static void main(String[] args) {
        //[1, 2]    [1, 2, 3]
        String[] droits = {"1","2","3"};
        System.out.println(Arrays.toString(droits));
        char[] caracs = Arrays.toString(droits).toCharArray();
        System.out.println(caracs[1]);
        System.out.println("size : "+caracs.length);
        if(caracs.length==9){
            String rs = caracs[1]+""+caracs[4]+""+caracs[7];
            System.out.println(rs);
            System.out.println(rs.length());
        }
        if(caracs.length==6){
            System.out.println(caracs[1]+" et "+caracs[4]);
        }
        if(caracs.length==3){
            System.out.println(caracs[1]);
        }
        
            System.out.println("imposta7ile");
                    System.out.println(Arrays.toString(droits));

    }
}
