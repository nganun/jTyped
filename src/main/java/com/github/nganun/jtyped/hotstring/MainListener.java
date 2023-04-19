package com.github.nganun.jtyped.hotstring;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.nganun.jtyped.util.PropertiesUtil;
import com.github.nganun.jtyped.util.RobotUtil;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class MainListener implements NativeKeyListener {

    public static Properties properties;
    public static Set<String> propertiesKeySet;
    public static String typedKeys = "";
    public static NativeKeyListener hotstringListener;
    

    static {
        System.out.println(">>> Main process start ...");
        try {
            properties = PropertiesUtil.getProp();
            propertiesKeySet = properties.stringPropertyNames();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        hotstringListener = new HotstringListener();
        GlobalScreen.addNativeKeyListener(hotstringListener);
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        // System.out.println("KeyText: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        // System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {

    // System.out.println(">>> main: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        typedKeys += e.getKeyChar();
        typedKeys = typedKeys.length() > 20 ? typedKeys.substring(10) : typedKeys;

        if (typedKeys.indexOf(";stop") > 0) {
            RobotUtil.backspace(5);
            typedKeys = "";
            stop();
        }

        if (typedKeys.indexOf(";start") > 0) {
            RobotUtil.backspace(6);
            typedKeys = "";
            start();
        }

        if (typedKeys.indexOf(";restart") > 0) {
            RobotUtil.backspace(8);
            typedKeys = "";
            restart();
        }

        if (typedKeys.indexOf(";exit") > 0) {
            RobotUtil.backspace(5);
            typedKeys = "";
            exit();
        }

    }

    public static void start() {
        GlobalScreen.addNativeKeyListener(hotstringListener);
    }

    public static void stop() {
        GlobalScreen.removeNativeKeyListener(hotstringListener);
    }

    // Invalid
    public static void restart() {
        GlobalScreen.addNativeKeyListener(hotstringListener);
        // PropertiesUtil.getReloadProp();
        GlobalScreen.removeNativeKeyListener(hotstringListener);
    }

    public static void exit() {
        System.exit(1);
    }
}