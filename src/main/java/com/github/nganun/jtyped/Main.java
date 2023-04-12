package com.github.nganun.jtyped;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.nganun.jtyped.hotstring.MainListener;

public class Main {
    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        NativeKeyListener mainListener = new MainListener();
        GlobalScreen.addNativeKeyListener(mainListener);
    }
}