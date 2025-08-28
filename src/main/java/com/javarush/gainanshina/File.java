package com.javarush.gainanshina;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class File {
    public static String readFile(String path) throws IOException {
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
            throw new IOException("Файл не найден по пути: " + path);
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    // Запись файла
    public static void writeFile(String path, String content) throws IOException {
        Path filePath = Paths.get(path);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }
}
