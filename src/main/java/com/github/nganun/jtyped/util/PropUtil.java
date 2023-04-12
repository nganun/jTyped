package com.github.nganun.jtyped.util;

import java.io.*;
import java.util.Properties;

public class PropUtil {

    private static final String CUSTOM_PATH = System.getProperty("user.home") + "/.dot/.properties";
    private static final String DEFAULT_PATH = System.getProperty("user.home") + "/.properties";
    private static File file;
    private static Properties prop;
    private static BufferedInputStream bis;
//    private static BufferedOutputStream bos;

    static {
        try {
            file = new File(CUSTOM_PATH);
            if (!file.exists()) {
                file = new File(DEFAULT_PATH);
                file.createNewFile();
            }
            prop = new Properties();
            bis = new BufferedInputStream(new FileInputStream(file));
            prop.load(bis);


//            bos = new BufferedOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();;
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Properties getProp() throws IOException {
        return prop;
    }

    public static String getHotkey(String key) throws IOException {
        return prop.getProperty(key);
    }

    public static Properties getReloadProp() {
        try {
            file = new File(CUSTOM_PATH);
            if (!file.exists()) {
                file = new File(DEFAULT_PATH);
                file.createNewFile();
            }
            Properties reloadProp = new Properties();
            bis = new BufferedInputStream(new FileInputStream(file));
            prop.load(bis);
            return prop;
//            bos = new BufferedOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();;
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return prop;
    }
    
}
