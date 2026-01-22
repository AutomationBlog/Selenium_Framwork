package com.amazon.utilities;

import com.google.gson.*;

import java.io.*;
import java.util.*;

/**
 * Utility class for handling JSON operations
 */
public class JsonUtility {
    private JsonElement jsonElement;

    public JsonUtility(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        jsonElement = JsonParser.parseReader(fileReader);
        fileReader.close();
    }

    /**
     * Get value from JSON by key
     */
    public String getValueByKey(String key) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject.get(key).getAsString();
    }

    /**
     * Get nested value from JSON
     */
    public String getNestedValue(String parentKey, String childKey) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject parentObject = jsonObject.getAsJsonObject(parentKey);
        return parentObject.get(childKey).getAsString();
    }

    /**
     * Get array from JSON
     */
    public JsonArray getJsonArray(String key) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject.getAsJsonArray(key);
    }

    /**
     * Convert JSON array to list of maps
     */
    public List<Map<String, String>> jsonArrayToList() {
        List<Map<String, String>> list = new ArrayList<>();
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            Map<String, String> map = new LinkedHashMap<>();

            for (String key : jsonObject.keySet()) {
                map.put(key, jsonObject.get(key).getAsString());
            }
            list.add(map);
        }
        return list;
    }

    /**
     * Write data to JSON file
     */
    public static void writeJsonFile(String filePath, Map<String, Object> data) throws IOException {
        JsonObject jsonObject = new JsonObject();
        
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getValue() instanceof String) {
                jsonObject.addProperty(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof Number) {
                jsonObject.addProperty(entry.getKey(), (Number) entry.getValue());
            } else if (entry.getValue() instanceof Boolean) {
                jsonObject.addProperty(entry.getKey(), (Boolean) entry.getValue());
            }
        }

        FileWriter fileWriter = new FileWriter(filePath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(jsonObject, fileWriter);
        fileWriter.close();
    }

    /**
     * Get entire JSON object
     */
    public JsonObject getJsonObject() {
        return jsonElement.getAsJsonObject();
    }
}
