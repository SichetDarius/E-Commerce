package com.example.mm.models;

public class User {
    private static String userName;
    private static Long userId;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static Long getUserId() {
        return userId;
    }

    public static void setUserId(Long userId) {
        User.userId = userId;
    }
}
