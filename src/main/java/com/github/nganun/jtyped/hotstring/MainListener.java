package com.github.nganun.jtyped.hotstring;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.nganun.jtyped.util.PropUtil;
import com.github.nganun.jtyped.util.RobotUtil;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class MainListener implements NativeKeyListener {

    public static Properties prop;
    public static Set<String> propKeySet;
    public static String typedKeys = "";
    public static String command = "";
    public static String propValue = "";
    public static NativeKeyListener hostringListener;
    

    static {
        System.out.println(">>> Main process start ...");
        try {
            prop = PropUtil.getProp();
            propKeySet = prop.stringPropertyNames();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        hostringListener = new HotstringListener();
        GlobalScreen.addNativeKeyListener(hostringListener);
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
        GlobalScreen.addNativeKeyListener(hostringListener);
    }

    public static void stop() {
        GlobalScreen.removeNativeKeyListener(hostringListener);
    }

    public static void restart() {
        GlobalScreen.addNativeKeyListener(hostringListener);
        PropUtil.getReloadProp();
        GlobalScreen.removeNativeKeyListener(hostringListener);
    }

    public static void exit() {
        System.exit(1);
    }
}