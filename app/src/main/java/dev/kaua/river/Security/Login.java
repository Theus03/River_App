package dev.kaua.river.Security;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import org.jetbrains.annotations.NotNull;

import dev.kaua.river.Activitys.MainActivity;
import dev.kaua.river.Activitys.SignInActivity;
import dev.kaua.river.Activitys.SignUpActivity;
import dev.kaua.river.Activitys.ValidateEmailActivity;
import dev.kaua.river.Data.Account.AccountServices;
import dev.kaua.river.Data.Account.DtoAccount;
import dev.kaua.river.LoadingDialog;
import dev.kaua.river.R;
import dev.kaua.river.Warnings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public abstract class Login {
    @SuppressLint("StaticFieldLeak")
    private static LoadingDialog loadingDialog;

    //  Set preferences
    private static SharedPreferences mPrefs;
    private static final String PREFS_NAME = "myPrefs";

    static final Retrofit retrofitUser = new Retrofit.Builder()
            .baseUrl("https://dev-river-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void DoLogin(Context context, String login_method, String password){
        loadingDialog = new LoadingDialog((Activity) context);
        loadingDialog.startLoading();
        DtoAccount account = new DtoAccount(EncryptHelper.encrypt(login_method), EncryptHelper.encrypt(password));
        AccountServices login_service = retrofitUser.create(AccountServices.class);
        Call<DtoAccount> call = login_service.login(account);
        call.enqueue(new Callback<DtoAccount>() {
            @Override
            public void onResponse(@NotNull Call<DtoAccount> call, @NotNull Response<DtoAccount> response) {
                loadingDialog.dismissDialog();
                if(response.code() == 200){
                    mPrefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    mPrefs.edit().clear().apply();
                    SharedPreferences.Editor editor = mPrefs.edit();
                    assert response.body() != null;
                    editor.putString("pref_account_id", response.body().getAccount_id_cry());
                    editor.putString("pref_name_user", response.body().getName_user());
                    editor.putString("pref_username", response.body().getUsername());
                    editor.putString("pref_email", response.body().getEmail());
                    editor.putString("pref_phone_user", response.body().getPhone_user());
                    editor.putString("pref_banner_user", response.body().getBanner_user());
                    editor.putString("pref_profile_image", response.body().getProfile_image());
                    editor.putString("pref_bio_user", response.body().getBio_user());
                    editor.putString("pref_url_user", response.body().getUrl_user());
                    editor.putString("pref_following", response.body().getFollowing());
                    editor.putString("pref_followers", response.body().getFollowers());
                    editor.putString("pref_born_date", response.body().getBorn_date());
                    editor.putString("pref_joined_date", response.body().getJoined_date());
                    editor.apply();
                    Intent i = new Intent(context, MainActivity.class);
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.move_to_left, R.anim.move_to_right);
                    ActivityCompat.startActivity(context, i, activityOptionsCompat.toBundle());
                    ((Activity) context).finish();
                }else if(response.code() == 206){
                    Intent i = new Intent(context, ValidateEmailActivity.class);
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(context,R.anim.move_to_left, R.anim.move_to_right);
                    //noinspection ConstantConditions
                    i.putExtra("account_id", EncryptHelper.decrypt(response.body().getMessage()));
                    i.putExtra("email_user", login_method);
                    i.putExtra("password", password);
                    i.putExtra("type_validate", 1);
                    ActivityCompat.startActivity(context, i, activityOptionsCompat.toBundle());
                    ((Activity) context).finish();
                }else if(response.code() == 401) {
                    try {
                        SignInActivity.getInstance().Invalid_email_or_password();
                    }catch (Exception ex){
                        Warnings.showWeHaveAProblem(context);
                    }
                }
                else Warnings.showWeHaveAProblem(context);
            }
            @Override
            public void onFailure(@NotNull Call<DtoAccount> call, @NotNull Throwable t) {
                loadingDialog.dismissDialog();
                Warnings.showWeHaveAProblem(context);
            }
        });

    }

}
