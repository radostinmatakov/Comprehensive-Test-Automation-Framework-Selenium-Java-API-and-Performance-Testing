package utils;

import com.github.javafaker.Faker;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;

public class DataGenerator {
    public static HashSet<String> usedUsernames = new HashSet<>();
    public static HashSet<String> usedPassword = new HashSet<>();
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
    public static String generateUniqueContentPost(int length) {
        return faker.lorem().characters(length);
    }

    private static String generateString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            result.append(characters.charAt(randomIndex));
        }
        return result.toString();
    }
}
