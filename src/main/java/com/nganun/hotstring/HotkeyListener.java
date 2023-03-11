package com.nganun.hotstring;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;
import java.util.Properties;

public class HotkeyListener implements NativeKeyListener {

    public static String typedString = "";
    public static Properties prop;

    static {
        try {
            prop = PropUtil.getProp();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        String typed = NativeKeyEvent.getKeyText(e.getKeyCode());
        if (typed.equals("Semicolon")) {
            typedString = ";";
        } else {
            typedString += typed;
        }

        System.out.println(">>>>" + typedString + ":" + prop.getProperty(typedString));

        if (prop.getProperty(typedString.toLowerCase()) != null)  {
            System.out.println(">>>> 执行命令：" + typedString);
            int len = typedString.length();
            String lenStr = typedString;
            typedString = "";

            RobotUtil.backspace(len);

            RobotUtil.inputKeys(prop.getProperty(lenStr.toLowerCase()));

        }

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

        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

    }

    public void nativeKeyTyped(NativeKeyEvent e) {
         // System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new HotkeyListener());
    }
}