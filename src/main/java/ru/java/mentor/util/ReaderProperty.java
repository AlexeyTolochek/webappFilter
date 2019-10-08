package ru.java.mentor.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReaderProperty {
    private static final String pathToConfigurationFile = "config.properties";

    public static String readProperty(String key) throws ExceptionFromReadMethod {
        Properties properties = new Properties();
        String value;
        try {
            InputStream stream = ReaderProperty.class.getClassLoader().getResourceAsStream(pathToConfigurationFile);
            properties.load(stream);
            value = properties.getProperty(key);
            System.out.println("// " + value + " / " + key);
        } catch (IOException e) {
            throw new ExceptionFromReadMethod("can`t find file " + pathToConfigurationFile);
        }
        return value;
    }
}
