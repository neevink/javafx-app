package com.neevin.Database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * Класс-помощник, который генерирует соль и хэширует пароли
 */
public class PasswordHasher {
    /**
     * Длина генерируемой соли
     */
    protected static final int SALT_LENGTH = 5;
    /**
     * Символы, из которых создаётся соль
     */
    protected static final String SaltSource = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()_+=-~/.,?><";
    protected static SecureRandom random = new SecureRandom();

    public static String generateSalt() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SALT_LENGTH; i++)
            sb.append(SaltSource.charAt(random.nextInt(SaltSource.length())));
        return sb.toString();
    }

    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] messageDigest = md.digest((password + salt).getBytes());

            BigInteger num = new BigInteger(1, messageDigest);
            String hashedPassword = num.toString(16);

            // Добавить предыдущие 0, чтобы сделать его 32-битным
            while (hashedPassword.length() < 32) {
                hashedPassword = "0" + hashedPassword;
            }
            return hashedPassword;
        }catch (Exception exc){ }
        return password+salt;
    }
}
