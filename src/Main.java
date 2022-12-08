import java.util.Scanner;

public class Main {
    public static void alphabetChoice(int[] alphabetsToUse) {
        StringBuilder alphabetChoice = new StringBuilder("Выбранные алфавиты:");
        for (int j : alphabetsToUse) {
            if (j == 1) alphabetChoice.append(" |Кириллица|");
            if (j == 2) alphabetChoice.append(" |Латиница|");
            if (j == 3) alphabetChoice.append(" |Цифры|");
            if (j == 4) alphabetChoice.append(" |Церковнославянский|");
            if (j == 5) alphabetChoice.append(" |Весь юникод|");
        }
        System.out.println(alphabetChoice);
    }

    public static int[] alphabetsArray(byte cyrillic, byte latin, byte numbers, byte cyrillicChurch, byte allUnicode) {
        int arraySize = cyrillic + latin + numbers + cyrillicChurch + allUnicode;
        if (arraySize == 0) {
            return new int[]{0};
        } else {
            int[] alphabetsToUse = new int[arraySize];
            byte[] alphabets = new byte[]{cyrillic, latin, numbers, cyrillicChurch, allUnicode};
            for (int i = 0, j = 0; i < alphabets.length; i++) {
                if (alphabets[i] == 1) {
                    alphabetsToUse[j] = i + 1;
                    j++;
                }
            }
            return alphabetsToUse;
        }
    }

    public static void alphabetCounter(byte cyrillic, byte latin, byte numbers, byte cyrillicChurch, byte allUnicode, byte delete) {
        if (cyrillic == delete) System.out.println("1.Кириллица");
        if (latin == delete) System.out.println("2.Латиница");
        if (numbers == delete) System.out.println("3.Цифры");
        if (cyrillicChurch == delete) System.out.println("4.Церковнославянский");
        if (allUnicode == delete) System.out.println("5.Весь юникод");
        System.out.println("6.Нет");
    }

    public static int[] alphabetAddDelete(int[] alphabetsToUse, byte delete) {
        Scanner in = new Scanner(System.in);
        byte cyrillic = 0, latin = 0, numbers = 0, cyrillicChurch = 0, allUnicode = 0;
        for (int j : alphabetsToUse) {
            if (j == 1) cyrillic = 1;
            if (j == 2) latin = 1;
            if (j == 3) numbers = 1;
            if (j == 4) cyrillicChurch = 1;
            if (j == 5) allUnicode = 1;
        }
        boolean choice = true;

        while (choice) {
            alphabetChoice(alphabetsToUse);
            System.out.println("*******************************************");
            if (delete == 0) System.out.println("Добавить алфавит?");
            else System.out.println("Удалить алфавит?");
            alphabetCounter(cyrillic, latin, numbers, cyrillicChurch, allUnicode, delete);
            System.out.println("*******************************************");
            switch (in.nextInt()) {
                case 1 -> cyrillic = (delete == 0) ? (byte) 1 : (byte) 0;
                case 2 -> latin = (delete == 0) ? (byte) 1 : (byte) 0;
                case 3 -> numbers = (delete == 0) ? (byte) 1 : (byte) 0;
                case 4 -> cyrillicChurch = (delete == 0) ? (byte) 1 : (byte) 0;
                case 5 -> allUnicode = (delete == 0) ? (byte) 1 : (byte) 0;
                case 6 -> choice = false;
            }
            alphabetsToUse = alphabetsArray(cyrillic, latin, numbers, cyrillicChurch, allUnicode);
        }
        return alphabetsToUse;
    }

    public static int[] generatorAlphabetSwitch(int alphabet) {
        int start = 0, range = 0;
        switch (alphabet) {
            case 1 -> {
                start = 1072;
                range = 32;
            }
            case 2 -> {
                start = 97;
                range = 26;
            }
            case 3 -> {
                start = 48;
                range = 10;
            }
            case 4 -> {
                start = 1120;
                range = 42;
            }
            case 5 -> range = 65530;
        }
        return new int[]{start, range};
    }

    public static void generate(int[] alphabetsToUse, int wordCount, int wordLength) {
        int alphabet;
        char newSymbol;
        int symbolNumber;
        StringBuilder words = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            for (int j = 0; j < wordLength; j++) {
                alphabet = (int) (Math.random() * alphabetsToUse.length);
                int[] startAndRange = generatorAlphabetSwitch(alphabetsToUse[alphabet]);
                symbolNumber = startAndRange[0] + (int) (Math.random() * startAndRange[1]);
                newSymbol = (char) symbolNumber;
                words.append(newSymbol);
            }
            words.append("\n");
        }
        System.out.println(words);
        System.out.println("*******************************************");

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean process = true;
        byte delete;
        int[] alphabetsToUse = new int[]{0};
        int wordLength;
        int wordCount;
        do {
            alphabetChoice(alphabetsToUse);
            System.out.println("*******************************************");
            System.out.println("1.Генерировать\n2.Выбор алфавита\n3.Завершить");
            switch (in.nextInt()) {
                case 1 -> {
                    if (alphabetsToUse[0] == 0) {
                        System.out.println("Выберете хотя бы один алфавит");
                        break;
                    }
                    System.out.println("*******************************************");
                    System.out.println("Сколько слов надо сгенерировать");
                    wordCount = in.nextInt();
                    System.out.println("Какой длинны?");
                    wordLength = in.nextInt();
                    generate(alphabetsToUse, wordCount, wordLength);
                }
                case 2 -> {
                    alphabetChoice(alphabetsToUse);
                    System.out.println("*******************************************");
                    System.out.println("1.Добавить алфавит\n2.Удалить алфавит");
                    switch (in.nextInt()) {
                        case 1 -> {
                            delete = 0;
                            alphabetsToUse = alphabetAddDelete(alphabetsToUse, delete);
                        }
                        case 2 -> {
                            delete = 1;
                            alphabetsToUse = alphabetAddDelete(alphabetsToUse, delete);
                        }
                        default -> System.out.println("Ошибка");
                    }
                }
                case 3 -> process = false;
            }
            System.out.println("*******************************************");
        } while (process);
        in.close();
    }
}
