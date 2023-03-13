package com.nganun.hotstring;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class HotstringListener implements NativeKeyListener {

    public static Properties prop;
    public static Set<String> propKeySet;
    public static String typedKeys = "";
    public static String command = "";

    static {
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
            System.out.println(">>> command：" + command);
            RobotUtil.backspace(command.length());
            if (command.equals(";dd")) {
                RobotUtil.inputKeys(HotstringUtil.getDate());
            } else if (command.equals(";tt")) {
                RobotUtil.inputKeys(HotstringUtil.getTime());
            } else if (command.equals(";day")) {
                try {
                    Runtime.getRuntime().exec(prop.getProperty(command));
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else if (command.equals(";log")) {
                try {
                    Runtime.getRuntime().exec(prop.getProperty(command));
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else {
                RobotUtil.inputKeys(prop.getProperty(command));
            }
            typedKeys = "";
            command = "";
        }
    }

}