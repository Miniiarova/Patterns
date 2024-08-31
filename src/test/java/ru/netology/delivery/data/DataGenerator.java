package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private static final String LOCALE = "ru";

    private DataGenerator(){
    }

    public static String generateName() {
        Faker faker = new Faker(new Locale(LOCALE));

        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generateCity() {
        String[] cities = new String[] {
            "Калининград", "Москва", "Санкт-Петербург", "Вологда", "Чебоксары", "Йошкар-Ола",
            "Казань", "Пермь", "Уфа", "Нижний Новгород", "Тюмень", "Омск", "Новосибирск", "Кемерово", "Барнаул",
            "Томск", "Красноярск", "Иркутск", "Улан-Удэ", "Якутск", "Благовещенск", "Хабаровск", "Владивосток",
        };

        return cities[new Random().nextInt(cities.length)];
    }

    public static String generatePhone() {
        Faker faker = new Faker(new Locale(LOCALE));

        return faker.phoneNumber().phoneNumber();
    }

    public static String generateDate(int shiftDays) {
        return LocalDate.now().plusDays(shiftDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser() {
            return new UserInfo(generateCity(), generateName(), generatePhone());
        }
    }

    @Value
    public static class UserInfo{
        String city;
        String name;
        String phone;
    }
}
