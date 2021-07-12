package dev.kaua.river.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import dev.kaua.river.BuildConfig;
import dev.kaua.river.R;
import dev.kaua.river.Security.EncryptHelper;
import dev.kaua.river.Tools.Methods;
import dev.kaua.river.Tools.ToastHelper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {
    //  Create timer
    private final Handler timer = new Handler();

    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.geoplugin.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView app_version_splash = findViewById(R.id.app_version_splash);
        app_version_splash.setText(BuildConfig.VERSION_NAME);

        Intent intent = getIntent();
        Uri data = intent.getData();
        if(data != null){
            String UrlGetFrom = data.toString();
            UrlGetFrom = UrlGetFrom.replace("https://dev-river-api.herokuapp.com/", "").replace("http://dev-river-api.herokuapp.com/", "");
            String[] KnowContent = UrlGetFrom.split("/");
            if (KnowContent[0].equals("verify-account")){
                if(KnowContent[1] != null && KnowContent[1].length() > 3 ){
                    try {
                        String result = ValidateEmailActivity.TestIntent();
                        if(result.length() > 0){
                            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("validate_email_intent"));
                            DoValidation(KnowContent[1]);
                        }
                    }catch (Exception ex){
                        DoValidation(KnowContent[1]);
                    }
                }
            }
        }else verifyIfUsersLogged();

    }

    private void DoValidation(String value) {
        SharedPreferences sp_First = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Intent i = new Intent(this, ValidateEmailActivity.class);
        i.putExtra("account_id", EncryptHelper.decrypt(sp_First.getString("pref_account_id", null)));
        i.putExtra("password", EncryptHelper.decrypt(sp_First.getString("pref_password", null)));
        i.putExtra("email_user", EncryptHelper.decrypt(sp_First.getString("pref_email", null)));
        i.putExtra("verify_id", value);
        i.putExtra("type_validate", 2);
        startActivity(i);
        finish();
    }


    public void verifyIfUsersLogged() {
        //  Verification of user preference information
        SharedPreferences sp_First = getSharedPreferences("myPrefs", MODE_PRIVATE);
        if (sp_First.contains("pref_token")) LoadBaseInfoAndMain();
        else timer.postDelayed(this::GoToIntro, 1500);
    }

    private void LoadBaseInfoAndMain() {
        Methods.LoadFollowersAndFollowing(this);
        timer.postDelayed(this::GoToMain, 300);
    }

    private void GoToMain(){
        Intent goto_main = new Intent(this, MainActivity.class);
        goto_main.putExtra("shortcut", 0);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.move_to_left_go, R.anim.move_to_right_go);
        ActivityCompat.startActivity(this, goto_main, activityOptionsCompat.toBundle());
        finish();
    }

    private void GoToIntro(){
        Intent goto_intro = new Intent(this, IntroActivity.class);
        goto_intro.putExtra("shortcut", 0);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),R.anim.move_to_left_go, R.anim.move_to_right_go);
        ActivityCompat.startActivity(this, goto_intro, activityOptionsCompat.toBundle());
        finish();
    }
}