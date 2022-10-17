package com.example.mm.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Utility methods used inside {@link com.example.mm.services}
 */
public class ServiceUtils {
    public static String hashPassword(String password) {
        String bcryptHashString = BCrypt.hashpw(password, BCrypt.gensalt(10));
        return bcryptHashString;
    }

    public static boolean checkMatchingPasswords(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
