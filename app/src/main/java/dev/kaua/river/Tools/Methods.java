package dev.kaua.river.Tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.util.Patterns;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

import dev.kaua.river.Activitys.MainActivity;
import dev.kaua.river.Data.Account.AccountServices;
import dev.kaua.river.Data.Account.DtoAccount;
import dev.kaua.river.LocalDataBase.DaoAccount;
import dev.kaua.river.Security.EncryptHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class Methods extends MainActivity {

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

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, (float) pixels, (float) pixels, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static void LoadFollowersAndFollowing(Context context){
        final Retrofit retrofitUser = GetRetrofitBuilder();
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        DtoAccount account = new DtoAccount();
        account.setAccount_id_cry(sp.getString("pref_account_id", null));
        AccountServices services = retrofitUser.create(AccountServices.class);
        Call<DtoAccount> call = services.get_followers_following(account);
        call.enqueue(new Callback<DtoAccount>() {
            @Override
            public void onResponse(@NotNull Call<DtoAccount> call, @NotNull Response<DtoAccount> response) {
                if(response.code() == 200){
                    DtoAccount info = new DtoAccount();
                    info.setAccount_id(Integer.parseInt(Objects.requireNonNull(EncryptHelper.decrypt(sp.getString("pref_account_id", null)))));
                    assert response.body() != null;
                    info.setFollowers(response.body().getFollowers());
                    info.setFollowing(response.body().getFollowing());
                    DaoAccount daoAccount = new DaoAccount(context);
                    long lines = daoAccount.Register_Followers_Following(info);
                    if(lines > 0) Log.d("LocalDataBase", "Followers and Following Update");
                    else Log.d("LocalDataBase", "Followers and Following is NOT Update");
                }
            }
            @Override
            public void onFailure(@NotNull Call<DtoAccount> call, @NotNull Throwable t) {}
        });
    }

    public static String NumberTrick(int number) {
        String numberString = "";
        if (Math.abs(number / 1000000) > 1)
            numberString = (number / 1000000) + "m";
        else if (Math.abs(number / 1000) > 1)
            numberString = (number / 1000) + "k";
        else
            numberString = number + "";
        return numberString;
    }

    // This method can be used in the future
    /*public boolean isValidPhone(String phone) {
        if (!phone.matches("^[+]?[0-9]{10,13}$"))
            return false;
        else
            return android.util.Patterns.PHONE.matcher(phone).matches();
    }*/
}
