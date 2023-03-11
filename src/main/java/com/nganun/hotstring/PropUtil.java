package com.nganun.hotstring;

import java.io.*;
import java.util.Properties;

public class PropUtil {

    private static final String PROP_PATH = System.getProperty("user.home") + "/hotkey.properties";
    private static File file;
    private static Properties prop;
    private static BufferedInputStream bis;
//    private static BufferedOutputStream bos;

    static {
        try {
            File file = new File(PROP_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            prop = new Properties();
            bis = new BufferedInputStream(new FileInputStream(file));
            prop.load(bis);
//            bos = new BufferedOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();;
        }
    }

    public static Properties getProp() throws IOException {
        return prop;
    }

    public static String getHotkey(String key) throws IOException {
        return prop.getProperty(key);
    }
    
}
