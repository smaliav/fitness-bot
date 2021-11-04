package ru.smaliav.fitnessbot.util;

public abstract class WordDeclinationHelper {

    public static String getDeclination(WordDeclinationEnum wordType, int num) {
        int res = num % 100;

        // МесяцЕВ
        if (res >= 10 && res <= 20) {
            return wordType.getDeclinations()[2];
        }

        res = num % 10;
        // МесяцЕВ
        if (res == 0 || res > 4) {
            return wordType.getDeclinations()[2];
        }
        // МесяцА
        if (res > 1) {
            return wordType.getDeclinations()[1];
        }
        // Месяц_
        if (res == 1) {
            return wordType.getDeclinations()[0];
        }

        return null;
    }

}
