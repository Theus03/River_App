package dev.kaua.river.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import dev.kaua.river.R;

public class IntroActivity extends AppCompatActivity {
    CardView btn_create_account;
    TextView txt_login;
    Handler timer = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Ids();

        //  Set create account btn click
        btn_create_account.setOnClickListener(v -> {
            btn_create_account.setCardBackgroundColor(getColor(R.color.base_color_click));
            timer.postDelayed(() -> btn_create_account.setCardBackgroundColor(getColor(R.color.base_color)),300);
            Intent goTo_SignUp = new Intent(this, SignUpActivity.class);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),R.anim.move_to_left, R.anim.move_to_right);
            ActivityCompat.startActivity(this, goTo_SignUp, activityOptionsCompat.toBundle());
            finish();
        });

        //  Set Login btn click
        txt_login.setOnClickListener(v -> {
            Intent goTo_SignIn = new Intent(this, SignInActivity.class);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),R.anim.move_to_left, R.anim.move_to_right);
            ActivityCompat.startActivity(this, goTo_SignIn, activityOptionsCompat.toBundle());
            finish();
        });
    }

    private void Ids() {
        btn_create_account = findViewById(R.id.btn_create_account_into);
        txt_login = findViewById(R.id.txt_login_intro);
    }
}