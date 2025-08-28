package com.javarush.gainanshina;

import java.io.IOException;
import java.util.Scanner;

public class Application {
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

            String text = File.readFile(inputPath);
            String result;

            if (mode == 1) { // шифрование
                result = Cipher.encrypt(text, key);
                System.out.println("Шифрование завершено.");
            } else { // расшифровка
                result = Cipher.decrypt(text, key);
                System.out.println("Расшифровка завершена.");
            }

            File.writeFile(outputPath, result);
            System.out.println("Результат сохранен в файл: " + outputPath);

        } else if (mode == 3) { // брутфорс
            System.out.print("Введите путь к зашифрованному файлу: ");
            String inputPath = scanner.nextLine();

            System.out.print("Введите путь для сохранения результата: ");
            String outputPath = scanner.nextLine();

            String encryptedText = File.readFile(inputPath);
            Force.bruteForce(encryptedText, outputPath);

        } else if (mode ==4) {
            System.exit(0);
        } else {
            System.out.println("Некорректный выбор.");
        }

        scanner.close();
    }
}
