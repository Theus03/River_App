package dev.kaua.river;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Patterns;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class Methods {

    //  Base API URL
    private static final String BASE_URL = "https://dev-river-api.herokuapp.com/";

    //  Method to validate phone number.
    public static boolean isValidPhoneNumber(@NotNull String phone) {
        if (!phone.trim().equals("") && phone.length() > 10) return Patterns.PHONE.matcher(phone).matches();
        return false;
    }

    //  Method to remove Spaces from String.
    public static String RemoveSpace(String str){
        str = str.replaceAll("^ +| +$|( )+", "$1");
        return str;
    }

    //  Method to generate Random Characters.
    //  The only parameter is the number of characters it will return.
    private static final Random rand = new Random();
    private static final char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$!@#$!@#$".toCharArray();
    public static String RandomCharacters (int CharactersAmount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CharactersAmount; i++) {
            int ch = rand.nextInt (letters.length);
            sb.append (letters [ch]);
        }
        return sb.toString();
    }

    //  Method to return Default Retrofit Builder
    public static Retrofit GetRetrofitBuilder(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    // This method can be used in the future
    /*public boolean isValidPhone(String phone) {
        if (!phone.matches("^[+]?[0-9]{10,13}$"))
            return false;
        else
            return android.util.Patterns.PHONE.matcher(phone).matches();
    }*/
}
