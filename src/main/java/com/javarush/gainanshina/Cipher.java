package com.javarush.gainanshina;

import java.util.HashMap;
import java.util.Map;

public class Cipher {
    public static final Map<Character, Integer> CHAR_TO_INDEX = new HashMap<>();

    static {
        for (int i = 0; i < Constans.ALPHABET.length; i++) {
            CHAR_TO_INDEX.put(Constans.ALPHABET[i], i);
        }
    }
    // Метод шифрования
    public static String encrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        String lowerText = text.toLowerCase();
        for (char ch : lowerText.toCharArray()) {
            if (CHAR_TO_INDEX.containsKey(ch)) {
                int index = CHAR_TO_INDEX.get(ch);
                int newIndex = (index + key) % Constans.ALPHABET_SIZE;
                if (newIndex < 0) newIndex += Constans.ALPHABET_SIZE;
                result.append(Constans.ALPHABET[newIndex]);
            }
            // если символ не в алфавите — пропускаем
        }
        return result.toString();

    }

    // Метод расшифровки
    public static String decrypt(String text, int key) {
        return encrypt(text, -key);
    }
}
