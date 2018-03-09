package com.fepeprog.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean validateEmail(String email) {
        final String ePattern = "[a-zA-Z0-9-.]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\." +
                "[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean validatePassword(String password) {
        // password must contain 2 numbers, 2
        return password.matches("([a-zA-Z]{8,})([0-9]{2,})");
    }

    public static boolean validateNameLatin(String name) {
        final String ePattern = "^[A-Za-z\\s]+$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(name);
        return m.matches();
    }


    /*
    * param
    * */
    public static boolean validateNameCyrillic(String name) {
        final String ePattern = "\\s*[А-ЯІЇЮЄЯЬ][-А-яіїюєяь\\s]+$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static boolean validatePhone(String phone) {
        phone = phone.replaceAll(" ", ""); //delete spaces
        final String ePattern = "^\\+(?:[0-9] ?){9,12}[0-9]$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(phone);
        return m.matches();

    }


}
