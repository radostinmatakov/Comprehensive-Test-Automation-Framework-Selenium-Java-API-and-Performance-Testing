package utils;

import com.github.javafaker.Faker;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;

public class DataGenerator {
    public static HashSet<String> usedUsernames = new HashSet<>();
    public static HashSet<String> usedEmails = new HashSet<>();
    private static Faker faker = new Faker();
    private static Random random = new Random();

    public static String generateUniqueUsername() {
        String username;
        do {
            username = faker.regexify("[a-zA-Z]{6,20}");
        } while (usedUsernames.contains(username));
        usedUsernames.add(username);
        return username;
    }
    public static String generateUniquePassword() {
        String password ;
        do {
            password = faker.regexify("[a-zA-Z0-9!@#$%^&*()_?<>.,]{6,20}");
        } while (usedUsernames.contains(password));
        usedUsernames.add(password);
        return password;
    }

    public static String generateUniqueEmail() {
        String email;
        do {
            int number = random.nextInt(1000);
            email = "testaccount." + number + "@abv.bg";
        } while (usedEmails.contains(email));
        usedEmails.add(email);
        return email;
    }

    public static String generateUniqueContentPost() {
        return faker.lorem().characters(10, 50);
    }

}
