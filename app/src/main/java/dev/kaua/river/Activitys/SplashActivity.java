package dev.kaua.river.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import dev.kaua.river.BuildConfig;
import dev.kaua.river.R;
import dev.kaua.river.Tools.Methods;

public class SplashActivity extends AppCompatActivity {
    //  Create timer
    private final Handler timer = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView app_version_splash = findViewById(R.id.app_version_splash);
        app_version_splash.setText(BuildConfig.VERSION_NAME);

        verifyIfUsersLogged();
        /*Intent goto_main = new Intent(this, MainActivity.class);
        goto_main.putExtra("shortcut", 0);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.move_to_left_go, R.anim.move_to_right_go);
        ActivityCompat.startActivity(this, goto_main, activityOptionsCompat.toBundle());
        finish();*/
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