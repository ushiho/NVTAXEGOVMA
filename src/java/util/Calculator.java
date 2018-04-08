/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author
 */
public class Calculator {

    public static Double calculate(String expression) {
        if (expression == null || expression.length() == 0) {
            return null;
        }
        return calc(expression.replace(" ", ""));
    }

    public static Double calc(String expression) {

        if (expression.startsWith("(") && expression.endsWith(")")) {
            return calc(expression.substring(1, expression.length() - 1));//eliminer les parenteses
        }
        String[] containerArr = new String[]{expression};
        double leftVal = getNextOperand(containerArr);
        expression = containerArr[0];
        if (expression.length() == 0) {
            return leftVal;
        }
        char operator = expression.charAt(0);
        expression = expression.substring(1);

        while (operator == '*' || operator == '/') {
            containerArr[0] = expression;
            double rightVal = getNextOperand(containerArr);
            expression = containerArr[0];
            if (operator == '*') {
                leftVal = leftVal * rightVal;
            } else {
                leftVal = leftVal / rightVal;
            }
            if (expression.length() > 0) {
                operator = expression.charAt(0);
                expression = expression.substring(1);
            } else {
                return leftVal;
            }
        }
        if (operator == '+') {
            return leftVal + calc(expression);
        } else {
            return leftVal - calc(expression);
        }

    }

    private static double getNextOperand(String[] exp) {
        double res;
        if (exp[0].startsWith("(")) {
            int open = 1;
            int i = 1;
            while (open != 0) {
                if (exp[0].charAt(i) == '(') {
                    open++;
                } else if (exp[0].charAt(i) == ')') {
                    open--;
                }
                i++;
            }
            res = calc(exp[0].substring(1, i - 1));
            exp[0] = exp[0].substring(i);
        } else {
            int i = 1;
            if (exp[0].charAt(0) == '-') {
                i++;
            }
            while (exp[0].length() > i && isNumber((int) exp[0].charAt(i))) {
                i++;
            }
            res = Double.parseDouble(exp[0].substring(0, i));
            exp[0] = exp[0].substring(i);
        }
        return res;
    }

    private static boolean isNumber(int c) {
        int zero = (int) '0';
        int nine = (int) '9';
        return (c >= zero && c <= nine) || c == '.';
    }

    public static void main(String[] args) throws ScriptException {
//    System.out.println(calculate("(((( -6 )))) * 9 * -1"));
//    System.out.println(calc("(-5.2+-5*-5*((5/4+2)))"));
//        double beforeCalcultae = System.currentTimeMillis();
//        System.out.println(calculate("3+(2*5/3)"));
//        double afterCalcultae = System.currentTimeMillis();
//        System.out.println("calclate timing " + (afterCalcultae - beforeCalcultae));
        String expression = "1+5<7";// or 1+5 o howa y7seb lik
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine jsEngine = manager.getEngineByName("JavaScript"); //BE CAREFUL about the engine name. 
        Object result = jsEngine.eval(expression); //Returns a java.lang.Boolean for the expression "1<3"
        System.out.println("ha exp " + expression + " o ha res " + result);
    }

    public static List<String> extracteService(String expression) {
        return extracteService(expression, ";", ";");
    }

    private static List<String> extracteService(String expression, String start, String end) {

// Captures the word(s) between the above two character(s)
        String pattern = start + "(\\w+)" + end;
        Pattern patternToBeExec = Pattern.compile(pattern);
        Matcher matcher = patternToBeExec.matcher(expression);
        List<String> res = new ArrayList();
        while (matcher.find()) {
            res.add(matcher.group().replace(start, "").replace(end, ""));
        }
        return res;
    }

    public List<String> formateService(String nonFormatedText) {
        //nonFormatedText = "6*;configurationItemFacadDOTfindByNameACLDOTgetDefaultValueACL;+66 ;koko;";

        List<String> res = (extracteService(nonFormatedText));
        for (int i = 0; i < res.size(); i++) {
            res.set(i, res.get(i).replace("DOT", "."));

        }
        for (int i = 0; i < res.size(); i++) {
            res.set(i, (res.get(i).replace("ACL", "()")));

        }
        return res;
    }
}
