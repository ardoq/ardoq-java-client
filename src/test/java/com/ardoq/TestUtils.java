package com.ardoq;

import java.io.IOException;
import java.util.Properties;

public class TestUtils {
    private static Properties p;

    private static void initProperties() {
        if (null == p) {
            p = new Properties();
            try {
                p.load(TestUtils.class.getClassLoader().getResourceAsStream("keys.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getTestPropery(String name) {
        initProperties();
        return p.getProperty(name);
    }
}
