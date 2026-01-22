package com.amazon.utilities;

import java.io.*;
import java.util.*;

/**
 * Utility class for handling Property file operations
 */
public class PropertyUtility {
    private static Properties properties;

    public PropertyUtility(String filePath) throws IOException {
        properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        properties.load(fileInputStream);
        fileInputStream.close();
    }

    /**
     * Get value from property file by key
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get value with default if key doesn't exist
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Set property value
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Save properties to file
     */
    public void saveProperties(String filePath) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
        properties.store(fileOutputStream, "Updated properties");
        fileOutputStream.close();
    }

    /**
     * Get all properties as map
     */
    public Map<String, String> getAllProperties() {
        Map<String, String> propertiesMap = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            propertiesMap.put(key, properties.getProperty(key));
        }
        return propertiesMap;
    }

    /**
     * Check if property exists
     */
    public boolean containsProperty(String key) {
        return properties.containsKey(key);
    }

    /**
     * Remove property
     */
    public void removeProperty(String key) {
        properties.remove(key);
    }
}
