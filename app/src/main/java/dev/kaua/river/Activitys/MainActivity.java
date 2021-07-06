package dev.kaua.river.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.kaua.river.EncryptHelper;
import dev.kaua.river.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String enc = "0";
        enc = EncryptHelper.encrypt(enc);

        String dec = "vZ4swtvm2cPKjDwrrnx6SCH/Di+2urcoXnLmR+BkTvEhpF2EG3fJnxUFP4lAynxP";
        dec = EncryptHelper.decrypt(dec);
    }

}