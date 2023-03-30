package com.nganun.hotstring;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.nganun.util.HotstringUtil;
import com.nganun.util.PropUtil;
import com.nganun.util.RobotUtil;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

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
        typedKeys = typedKeys.length() > 10 ? typedKeys.substring(5) : typedKeys;

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
                    .replaceAll("HKCUSoftwareMicrosoftWindowsCurrentVersionInternet Settings",
                            "HKCU\\\\Software\\\\Microsoft\\\\Windows\\\\CurrentVersion\\\\Internet Settings");
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

            typedKeys = "";
            command = "";
        }
    }

}