package com.github.nganun.jtyped.hotstring;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.nganun.jtyped.util.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HotstringListener implements NativeKeyListener {

    public static Properties prop;
    public static Set<String> propKeySet;
    public static String typedKeys = "";
    public static String command = "";
    public static String propValue = "";

    static {
        System.out.println(">>> Hostring process start ...");
        try {
            prop = PropUtil.getProp();
            propKeySet = prop.stringPropertyNames();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void nativeKeyPressed(NativeKeyEvent e) {
        // System.out.println("KeyText: " + NativeKeyEvent.getKeyText(e.getKeyCode()));


        /**
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
        }
         */
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        // System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {

        typedKeys += e.getKeyChar();
        typedKeys = typedKeys.length() > 20 ? typedKeys.substring(5) : typedKeys;

        String regex = ".*;;.*;";
        boolean isMatch = Pattern.matches(regex, typedKeys);

        if (Pattern.matches(".*;;.*", typedKeys)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(typedKeys);
            if (Pattern.matches(".*;;.*;.*", typedKeys)) {
                if (matcher.find()) {
                    String word = matcher.group(0).split(";")[2];
                    boolean exist = ExcelUtil.isExist(word);
                    if (!exist) {
                        RobotUtil.backspace(word.length() + 3);
                        typedKeys = "";
                        String[] dict = DictUtil.getTranslate(word);
                        System.out.println(">>> [word]: " + word + "\t\t[dict]: " + Arrays.toString(dict));
                        ExcelUtil.addWord(dict);
                    } else {
                        typedKeys = "";
                        RobotUtil.backspace(word.length() + 3);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtil test = new DialogUtil();
                                test.show("Alert", word + " 单词已经存在", 1000);
                            }
                        }).start();
                    }

                }
            }
        } else {
            for (String propKey : propKeySet) {
                if (typedKeys.indexOf(propKey) > 0) {
                    typedKeys = propKey;
                    command = propKey;
                    break;
                }
            }

            if (command.length() > 0) {
                System.out.print(">>> [key]: " + command);
                RobotUtil.backspace(command.length());

                propValue = prop.getProperty(command)
                        .replaceAll("HKCUSoftwareMicrosoftWindowsCurrentVersion",
                                "HKCU\\\\Software\\\\Microsoft\\\\Windows\\\\CurrentVersion\\\\")
                        .replace("ThemesPersonalize",
                                "Themes\\Personalize");
                if (propValue.trim().equals("")) {
                    if (command.equals(";dd")) {
                        propValue = HotstringUtil.getDate();
                        RobotUtil.inputKeys(propValue);
                    } else if (command.equals(";tt")) {
                        propValue = HotstringUtil.getTime();
                        RobotUtil.inputKeys(propValue);
                    }
                } else if (propValue.trim().startsWith("cmd /")) {
                    try {
                        Runtime.getRuntime().exec(propValue);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    RobotUtil.inputKeys(propValue);
                }

                System.out.println("\t\t[value]: " + propValue);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        DialogUtil test = new DialogUtil();
//                        test.show("Alert", propValue);
//                    }
//                }).start();
                typedKeys = "";
                command = "";
            }
        }


    }

}