package com.github.nganun.jtyped.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RegistryUtil {

    public static Map<String, String> readNode(String nodePath) {
        Map<String, String> regMap = new HashMap<>();

        try {
            Process process = Runtime.getRuntime().exec("reg query " + nodePath);
            process.getOutputStream().close();

            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            String line = null;

            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(" +");
                if (arr.length != 4) {
                    continue;
                }
                regMap.put(arr[1], arr[3]);
            }

            process.destroy();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return regMap;
    }

    public static String readValue(String nodePath, String key) {
        return readNode(nodePath).get(key);
    }

    public static void main(String[] args) {
        String nodePath = "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize";
        System.out.println(nodePath);
        System.out.println(readValue(nodePath, "SystemUsesLightTheme"));
        String a = readValue(nodePath, "SystemUsesLightTheme");
        int b = Integer.parseInt(a.substring(2), 16);
        System.out.println(b);
    }
}
