package com.javarush.gainanshina;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Force {
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

    public static void bruteForce(String encryptedText, String outputPath) {
        // System.out.println("Начинается брутфорс...");
        for (int key = 0; key < Constans.ALPHABET_SIZE; key++) {
            String possibleText = Cipher.decrypt(encryptedText, key);
            if (isLikelyRussianText(possibleText)) {
                System.out.println("Возможный ключ: " + key);
                // System.out.println("Расшифрованный текст:\n" + possibleText);
                try {
                    File.writeFile(outputPath, possibleText);
                    System.out.println("Результат сохранен в файл: " + outputPath);
                } catch (IOException e) {
                    System.out.println("Ошибка при сохранении файла: " + e.getMessage());
                }
                return; // остановка после первого подходящего варианта
            }
        }
        System.out.println("Не удалось найти подходящий ключ методом brute force.");
    }
}
