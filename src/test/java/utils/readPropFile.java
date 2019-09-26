package utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class readPropFile {

    public String getProperty(String desiredProperty) {

        try {
            InputStream input = new FileInputStream(".\\src\\test\\resources\\suite.properties");
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty(desiredProperty);
        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }
}