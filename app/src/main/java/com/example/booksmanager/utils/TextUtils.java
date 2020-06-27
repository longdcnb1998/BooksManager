package com.example.booksmanager.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Pattern;

public class TextUtils {

    public static boolean isAccountPasswordOk(String text) {
        boolean isOk = true;
        if (text.matches("[a-z0-9_]*")) {
            isOk = true;
        } else {
            isOk = false;
        }
        return isOk;
    }

    public static boolean isInt(String string) {
        try {
            Integer.parseInt(string);
        }  catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isStringNull(String str) {
        if (str == null || str.equals("") || str.trim().length() == 0)
            return true;
        return false;
    }

    public static boolean isStringNull(EditText et) {
        String str = et.getText().toString();
        if (str == null || str.equals("") || str.trim().length() == 0)
            return true;
        return false;
    }

    public static String getEmail(Context context) {
        String email = "";
        try {
            Pattern emailPattern = Patterns.EMAIL_ADDRESS;
            Account[] accounts = AccountManager.get(context).getAccounts();
            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    if (account.name.contains("@gmail.com")) {
                        email = account.name;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return email;
    }

    public static String getDeviceOwner(Context context) {
        String name = "";
        try {
            Pattern emailPattern = Patterns.EMAIL_ADDRESS;
            Account[] accounts = AccountManager.get(context).getAccounts();
            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    if (account.name.contains("@gmail.com")) {
                        name = account.name.split("@")[0];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

}
