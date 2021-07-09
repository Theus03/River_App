package dev.kaua.river;

import android.util.Patterns;

import org.jetbrains.annotations.NotNull;


public abstract class Methods {

    public static boolean isValidPhoneNumber(@NotNull String phone) {
        if (!phone.trim().equals("") && phone.length() > 10) return Patterns.PHONE.matcher(phone).matches();
        return false;
    }

    public static String RemoveSpace(String str){
        str = str.replaceAll("^ +| +$|( )+", "$1");
        return str;
    }


    // This method can be used in the future
    /*public boolean isValidPhone(String phone) {
        if (!phone.matches("^[+]?[0-9]{10,13}$"))
            return false;
        else
            return android.util.Patterns.PHONE.matcher(phone).matches();
    }*/
}
