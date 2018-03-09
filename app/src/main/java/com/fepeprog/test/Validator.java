package com.fepeprog.test;

import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fepeprog on 3/7/18.
 */

public class Validator {

    public static boolean validateEmail(String email){
        final String ePattern = "[a-zA-Z0-9-.]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\." +
                                "[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean validatePassword(String password){
        final String PASSWORD_PATTERN =
                "((?=.*[a-z])(?=.*[A-Z]).{6,20})";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean validateNameLat(String name){
        final String ePattern =  "^[A-Za-z\\s]+$";;
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(name);
        return m.matches();
    }


    public static boolean validateNameCyr(String name){
        final String ePattern =   "\\s*[А-ЯІЇЮЄЯЬ][-А-яіїюєяь\\s]+$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static boolean validatePhone(String phone){
        phone = phone.replaceAll(" ", "");
        final String ePattern =  "^\\+(?:[0-9] ?){9,12}[0-9]$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(phone);
        boolean valide = m.matches();
        Log.d("phone", "validatePhone: "+phone+": "+String.valueOf(valide));
        return valide;

    }



}
