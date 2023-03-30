package com.nganun.util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class RobotUtil {

    private static final String[] LETTER_NUMBER_ARRAY = {"0","1","2","3","4","5","6","7","8","9",
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private static final String[] BIG_SYMBOL = {"~","!","@","#","$","%","^","&","*","(",")","_","+","{","}","|",":","\"","<",">","?"};
    private static final String[] SMALL_SYMBOL = {"`","-","=","[","]","\\",";","'",",",".","/"," "};

    public static void inputKeys(String content) {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < content.length(); i++) {
            robot.delay(10);
            char c = content.charAt(i);
            String s = String.valueOf(c);

            if (Arrays.asList(LETTER_NUMBER_ARRAY).contains(s)) {
                if (Character.isUpperCase(c)) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                }
                robot.keyPress(Character.toUpperCase(c));
                robot.keyRelease(Character.toUpperCase(c));
                if (Character.isUpperCase(c)) {
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                }
            }

            if (Arrays.asList(BIG_SYMBOL).contains(s)) {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(getKeyInt(s));
                robot.keyRelease(getKeyInt(s));
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }

            if (Arrays.asList(SMALL_SYMBOL).contains(s)) {
                robot.keyPress(getKeyInt(s));
                robot.keyRelease(getKeyInt(s));
            }
        }
    }

    public static int getKeyInt(String str) {
        switch (str) {
            case "~": return KeyEvent.VK_BACK_QUOTE;
            case "!": return KeyEvent.VK_1;
            case "@": return KeyEvent.VK_2;
            case "#": return KeyEvent.VK_3;
            case "$": return KeyEvent.VK_4;
            case "%": return KeyEvent.VK_5;
            case "^": return KeyEvent.VK_6;
            case "&": return KeyEvent.VK_7;
            case "*": return KeyEvent.VK_8;
            case "(": return KeyEvent.VK_9;
            case ")": return KeyEvent.VK_0;
            case "_": return KeyEvent.VK_MINUS;
            case "+": return KeyEvent.VK_EQUALS;
            case "{": return KeyEvent.VK_OPEN_BRACKET;
            case "}": return KeyEvent.VK_CLOSE_BRACKET;
            case "|": return KeyEvent.VK_BACK_SLASH;
            case ":": return KeyEvent.VK_SEMICOLON;
            case "\"": return KeyEvent.VK_QUOTE;
            case "<": return KeyEvent.VK_COMMA;
            case ">": return KeyEvent.VK_PERIOD;
            case "?": return KeyEvent.VK_SLASH;

            case "`": return KeyEvent.VK_BACK_QUOTE;
            case "-": return KeyEvent.VK_MINUS;
            case "=": return KeyEvent.VK_EQUALS;
            case "[": return KeyEvent.VK_OPEN_BRACKET;
            case "]": return KeyEvent.VK_CLOSE_BRACKET;
            case "\\": return KeyEvent.VK_BACK_SLASH;
            case ";": return KeyEvent.VK_SEMICOLON;
            case "'": return KeyEvent.VK_QUOTE;
            case ",": return KeyEvent.VK_COMMA;
            case ".": return KeyEvent.VK_PERIOD;
            case "/": return KeyEvent.VK_SLASH;
            case " ": return KeyEvent.VK_SPACE;
        }
        return 0;
    }

    public static void backspace(int num) {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < num; i++) {
            robot.delay(10);
            robot.keyPress(KeyEvent.VK_BACK_SPACE);
            robot.keyRelease(KeyEvent.VK_BACK_SPACE);
        }
    }

}
