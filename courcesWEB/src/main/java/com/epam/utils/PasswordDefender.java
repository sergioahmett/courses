package com.epam.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * The class that is responsible for encrypting the password and checking it.
 * 
 * @author Sergey Ahmetshin
 * 
 */

public class PasswordDefender {

    public static String getEncryptedPass(String password, String login) {
        return DigestUtils.sha256Hex(password + login);
    }

    public static boolean checkPass(String dbHesh, String login, String incomingPassword) {
        return DigestUtils.sha256Hex(incomingPassword + login).equals(dbHesh);
    }
}
