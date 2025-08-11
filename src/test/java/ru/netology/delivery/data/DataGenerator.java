package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    //Генератор даты
    public static String generateDate(int shift) {
        String data = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return data;
    }
    //Генератор города
    public static String generateCity(Faker faker) {
        List<String> validCities = List.of(
                "Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Магас", "Нальчик", "Элиста", "Черкесск",
                "Петрозаводск","Москва", "Санкт-Петербург"
        );
        String city = validCities.get(new Random().nextInt(validCities.size()));
        //генератор случайных чисел выбирает число из длины массива и возращает значение ячейки массива
        return city;
    }

    //Генератор ФИ
    public static String generateName(Faker faker) {
        String name = faker.name().lastName() + " " + faker.name().firstName();
        return name;
    }

    public static String generatePhone(Faker faker) {
        String phone = "+7" + faker.phoneNumber().subscriberNumber(10);
        return phone;
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));
            UserInfo user = new UserInfo(generateCity(faker), generateName(faker), generatePhone(faker));
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}