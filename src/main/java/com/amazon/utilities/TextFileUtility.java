package com.amazon.utilities;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Utility class for handling Text file operations
 */
public class TextFileUtility {

    /**
     * Read all lines from text file
     */
    public static List<String> readTextFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    /**
     * Read single line from text file
     */
    public static String readLine(String filePath, int lineNumber) throws IOException {
        List<String> lines = readTextFile(filePath);
        if (lineNumber > 0 && lineNumber <= lines.size()) {
            return lines.get(lineNumber - 1);
        }
        return null;
    }

    /**
     * Write content to text file
     */
    public static void writeToTextFile(String filePath, String content, boolean append) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, append);
        fileWriter.write(content);
        fileWriter.write(System.lineSeparator());
        fileWriter.close();
    }

    /**
     * Write multiple lines to text file
     */
    public static void writeLines(String filePath, List<String> lines, boolean append) throws IOException {
        Files.write(Paths.get(filePath), lines, StandardOpenOption.CREATE,
                append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Search for text in file
     */
    public static boolean searchInFile(String filePath, String searchText) throws IOException {
        List<String> lines = readTextFile(filePath);
        return lines.stream().anyMatch(line -> line.contains(searchText));
    }

    /**
     * Get line count
     */
    public static int getLineCount(String filePath) throws IOException {
        return (int) Files.lines(Paths.get(filePath)).count();
    }

    /**
     * Append content to file
     */
    public static void appendToFile(String filePath, String content) throws IOException {
        writeToTextFile(filePath, content, true);
    }

    /**
     * Clear file content
     */
    public static void clearFile(String filePath) throws IOException {
        Files.write(Paths.get(filePath), new byte[0]);
    }

    /**
     * Check if file exists
     */
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    /**
     * Delete file
     */
    public static boolean deleteFile(String filePath) throws IOException {
        return Files.deleteIfExists(Paths.get(filePath));
    }
}
