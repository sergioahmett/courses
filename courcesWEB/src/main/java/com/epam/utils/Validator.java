package com.epam.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that helps validate incoming data.
 * 
 * @author Sergey Ahmetshin
 * 
 */

public class Validator {
    private static final String NAME_AND_SURNAME_PATTERN = "^([\\p{L}]){2,20}+$";
    private static final String LOGIN_PATTERN = "^[A-Za-z0-9]{2,20}+$";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,30}";

    public boolean checkNameOrSurname(String name_surname) {
        return checkProperty(NAME_AND_SURNAME_PATTERN, name_surname);
    }

    public boolean checkEmail(String mail) {
        return checkProperty(EMAIL_PATTERN, mail);
    }

    public boolean checkLogin(String login) {
        return checkProperty(LOGIN_PATTERN, login);
    }

    public boolean checkPassword(String password) {
        return checkProperty(PASSWORD_PATTERN, password);
    }

    private boolean checkProperty(String pattern, String data) {
        Matcher regexp = Pattern.compile(pattern).matcher(data);
        return regexp.matches();
    }

}
