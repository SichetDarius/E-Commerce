package com.example.mm.services;

import java.sql.SQLException;

import static com.example.mm.models.User.getUserId;
import static com.example.mm.models.User.setUserId;
import static com.example.mm.services.UserService.validateUserOnSignIn;

/**
 * TODO: Add try-catch in all SQL-related methods!
 */
public class UserStateService {

    /**
     * Sets the state (userId) of the current user on Login/Sign up.
     *
     * @param username - given username
     * @param password - given password
     * @return success or failure (true/false) of setting the state of the entered user
     */
    public static boolean setCurrentUser(String username, String password) throws SQLException {
        Long userId = validateUserOnSignIn(username, password);
        if (userId != null) {
            setUserId(userId);
            return true;
        }
        return false;
    }

    /**
     * Logs out the current user (sets its Id to null).
     *
     * @return success or failure of logging out the user
     */
    public static boolean logoutUser() {
        Long userId = getUserId();
        if (userId != null) {
            setUserId(null);
            return true;
        }
        return false;
    }


}
