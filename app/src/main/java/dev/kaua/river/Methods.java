package dev.kaua.river;

import android.telephony.PhoneNumberUtils;
import android.util.Patterns;

public abstract class Methods {

    public static boolean isValidPhoneNumber(String phone) {
        if (!phone.trim().equals("") && phone.length() > 10)
            return Patterns.PHONE.matcher(phone).matches();
        return false;
    }


    // This method can be used in the future
    public boolean isValidPhone(String phone) {
        if (!phone.matches("^[+]?[0-9]{10,13}$"))
            return false;
        else
            return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
