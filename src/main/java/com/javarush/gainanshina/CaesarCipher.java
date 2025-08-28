package com.javarush.gainanshina;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaesarCipher {

    private static final char[] ALPHABET = {
                'а','б','в','г','д','е','ё','ж','з','и','й','к','л',
                'м','н','о','п','р','с','т','у','ф','х','ц','ч',
                'ш','щ','ъ','ы','ь','э','ю','я',
                '.', ',', '“', '”', ':', '-', '!', '?', ' ','\n'
        };

        private static final int ALPHABET_SIZE = ALPHABET.length;
        private static final Map<Character, Integer> CHAR_TO_INDEX = new HashMap<>();

        static {
            for (int i = 0; i < ALPHABET.length; i++) {
                CHAR_TO_INDEX.put(ALPHABET[i], i);
            }
        }

        // Метод шифрования
        public static String encrypt(String text, int key) {
            StringBuilder result = new StringBuilder();
            String lowerText = text.toLowerCase();
            for (char ch : lowerText.toCharArray()) {
                if (CHAR_TO_INDEX.containsKey(ch)) {
                    int index = CHAR_TO_INDEX.get(ch);
                    int newIndex = (index + key) % ALPHABET_SIZE;
                    if (newIndex < 0) newIndex += ALPHABET_SIZE;
                    result.append(ALPHABET[newIndex]);
                }
                // если символ не в алфавите — пропускаем
            }
            return result.toString();

        }

        // Метод расшифровки
        public static String decrypt(String text, int key) {
            return encrypt(text, -key);
        }

        // Чтение файла

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

        private static boolean isLikelyRussianText(String text) {

            String regex = "!\\p{IsCyrillic}";
            String regex1 = ",\\p{IsCyrillic}";
            String regex2 = ":\\p{IsCyrillic}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            Pattern pattern1 = Pattern.compile(regex1);
            Matcher matcher1 = pattern1.matcher(text);
            Pattern pattern2 = Pattern.compile(regex2);
            Matcher matcher2 = pattern2.matcher(text);

            while (matcher2.find() || matcher1.find() || matcher.find()) {
                return false;
            }

            return true;
        }

        // Брутфорс для поиска ключа
        public static void bruteForce(String encryptedText) {
            System.out.println("Начинается брутфорс...");
            for (int key = 0; key < ALPHABET_SIZE; key++) {
                String possibleText = decrypt(encryptedText, key);
                if (isLikelyRussianText(possibleText)) {
                    System.out.println("Возможный ключ: " + key);
                    System.out.println("Расшифрованный текст:\n" + possibleText);
                    return; // остановка после первого подходящего варианта
                }
            }
            System.out.println("Не удалось найти подходящий ключ методом brute force.");
        }

        public static void main(String[] args) throws IOException {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Выберите режим:");
            System.out.println("1 - Шифрование");
            System.out.println("2 - Расшифровка");
            System.out.println("3 - Брутфорс");
            System.out.println("4 - Exit");
            int mode = scanner.nextInt();
            scanner.nextLine();

            if (mode == 1 || mode == 2) {
                System.out.print("Введите путь к исходному файлу: ");
                String inputPath = scanner.nextLine();

                System.out.print("Введите путь для сохранения результата: ");
                String outputPath = scanner.nextLine();

                System.out.print("Введите ключ (целое число): ");
                int key = scanner.nextInt();
                scanner.nextLine();

                String text = readFile(inputPath);
                String result;

                if (mode == 1) { // шифрование
                    result = encrypt(text, key);
                    System.out.println("Шифрование завершено.");
                } else { // расшифровка
                    result = decrypt(text, key);
                    System.out.println("Расшифровка завершена.");
                }

                writeFile(outputPath, result);
                System.out.println("Результат сохранен в файл: " + outputPath);

            } else if (mode == 3) { // брутфорс
                System.out.print("Введите путь к зашифрованному файлу: ");
                String inputPath = scanner.nextLine();

                String encryptedText = readFile(inputPath);
                bruteForce(encryptedText);


            } else if (mode ==4) {
                System.exit(0);
            } else {
                System.out.println("Некорректный выбор.");
            }

            scanner.close();
        }
}