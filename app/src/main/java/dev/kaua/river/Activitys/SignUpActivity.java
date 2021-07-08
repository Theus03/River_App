package dev.kaua.river.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import dev.kaua.river.LoadingDialog;
import dev.kaua.river.Methods;
import dev.kaua.river.R;

public class SignUpActivity extends AppCompatActivity {
    private ImageView btn_back_signUp;
    private TextInputEditText edit_name, edit_email, edit_phone, edit_bornDate, edit_password;
    private TextInputLayout email_tl_signUp, phone_tl_signUp, bornDate_tl_signUp, password_tl_signUp;
    private Button btn_next;
    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms
    private final Calendar myCalendar = Calendar.getInstance();
    private static DatePickerDialog.OnDateSetListener date;
    private LoadingDialog loadingDialog;

    String name_user, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Ids();
        RunEditTextErrors();
        checking_password_have_minimum_characters();

        //  Creating Calendar
        date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        edit_bornDate.setOnClickListener(v -> ShowCalendar());

        btn_back_signUp.setOnClickListener(v -> Back_to_intro());

        btn_next.setOnClickListener(v -> {
            try {
                String name_user_full = Objects.requireNonNull(edit_name.getText()).toString();
                name_user = name_user_full.replaceAll("^ +| +$|( )+", "$1");
                String[] split_name = name_user.split(" ");
                if(split_name[1].length() <= 0)
                    edit_name.setError(getString(R.string.it_is_necessary_last_name));
                else if (!Patterns.EMAIL_ADDRESS.matcher(Objects.requireNonNull(edit_email.getText()).toString()).matches())
                    email_tl_signUp.setError(getString(R.string.please_enter_a_valid_email));
                else if(!Methods.isValidPhoneNumber(Objects.requireNonNull(edit_phone.getText()).toString()))
                    phone_tl_signUp.setError(getString(R.string.please_enter_a_valid_phone_number));
                else if(Objects.requireNonNull(edit_bornDate.getText()).toString().length() <= 0)
                    bornDate_tl_signUp.setError(getString(R.string.age_warning));
                else if(!Objects.requireNonNull(edit_password.getText()).toString().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$"))
                    password_tl_signUp.setError(getString(R.string.password_needs));
                else{
                    loadingDialog.startLoading();
                }
            }catch (Exception ex){
                edit_name.setError(getString(R.string.it_is_necessary_last_name));
            }
        });

    }

    private void RunEditTextErrors() {
        //  Email Text Watcher
        edit_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { all_filled(); }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) { if(timer != null) timer.cancel(); all_filled();}
            @Override
            public void afterTextChanged(final Editable s) {
                all_filled();
                //avoid triggering event when text is too short
                email_tl_signUp.setErrorEnabled(false);
                if (s.length() >= 3) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            email = s.toString();
                            runOnUiThread(() -> {
                                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                                    email_tl_signUp.setError(getString(R.string.please_enter_a_valid_email));
                                email_tl_signUp.setErrorEnabled(true);
                            });
                        }
                    }, DELAY);
                }
            }
        });

        //  Phone Text Watcher
        edit_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { all_filled(); }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) { if(timer != null) timer.cancel();
                all_filled();}
            @Override
            public void afterTextChanged(final Editable s) {
                all_filled();
                //avoid triggering event when text is too short
                phone_tl_signUp.setErrorEnabled(false);
                if (s.length() >= 3) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            phone = s.toString();
                            runOnUiThread(() -> {
                                if(!Methods.isValidPhoneNumber(phone))
                                    phone_tl_signUp.setError(getString(R.string.please_enter_a_valid_phone_number));
                                phone_tl_signUp.setErrorEnabled(true);
                            });
                        }
                    }, DELAY);
                }
            }
        });

        //  Name Text Watcher
        edit_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {all_filled();}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {all_filled();}
            @Override
            public void afterTextChanged(Editable s) {all_filled();}
        });
    }

    private void Ids() {
        loadingDialog = new LoadingDialog(this);
        btn_back_signUp = findViewById(R.id.btn_back_signUp);
        edit_name = findViewById(R.id.edit_name_signUp);
        edit_email = findViewById(R.id.edit_email_signUp);
        edit_phone = findViewById(R.id.edit_phone_signUp);
        email_tl_signUp = findViewById(R.id.email_tl_signUp);
        phone_tl_signUp = findViewById(R.id.phone_tl_signUp);
        edit_bornDate = findViewById(R.id.edit_bornDate_signUp);
        bornDate_tl_signUp = findViewById(R.id.bornDate_tl_signUp);
        edit_password = findViewById(R.id.edit_password_signUp);
        password_tl_signUp = findViewById(R.id.password_tl_signUp);
        btn_next = findViewById(R.id.btn_next_signUp);
    }

    private void ShowCalendar(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        String dateSelected = sdf.format(myCalendar.getTime());

        String[] dateSplit = dateSelected.split("/");

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        if((year - Integer.parseInt(dateSplit[2])) < 13 )
            bornDate_tl_signUp.setError(getString(R.string.age_warning));
        else
            bornDate_tl_signUp.setErrorEnabled(false);

        all_filled();
        edit_bornDate.setText(dateSelected);
    }

    private void checking_password_have_minimum_characters() {
        Objects.requireNonNull(password_tl_signUp.getEditText()).addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {
                all_filled();
                if(Objects.requireNonNull(edit_password.getText()).toString().length() > 0){
                    if (Objects.requireNonNull(edit_password.getText()).toString().indexOf(' ') > 0){
                        password_tl_signUp.setError(getString(R.string.password_cannot_contain_spaces));
                        password_tl_signUp.setErrorEnabled(true);
                    }else{
                        if (!edit_password.getText().toString().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")){
                            password_tl_signUp.setError(getString(R.string.password_needs));
                            password_tl_signUp.setErrorEnabled(true);
                        }else
                            password_tl_signUp.setErrorEnabled(false);
                    }
                }else
                    password_tl_signUp.setErrorEnabled(false);
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void all_filled(){
        if(edit_name.getText().toString().length() > 0 && Patterns.EMAIL_ADDRESS.matcher(edit_email.getText().toString()).matches()
        && Methods.isValidPhoneNumber(edit_phone.getText().toString()) && edit_bornDate.getText().toString().length() > 0 && edit_password.getText().toString().length() >= 8
        && edit_password.getText().toString().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")){
            btn_next.setEnabled(true);
            btn_next.setBackgroundResource(R.drawable.custom_button_next);
        }else{
            btn_next.setEnabled(false);
            btn_next.setBackgroundResource(R.drawable.custom_button_disable_next);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Objects.requireNonNull(edit_name.getText()).length() <= 0)
            edit_name.requestFocus();
        else if(Objects.requireNonNull(edit_email.getText()).length() <= 0)
            edit_email.requestFocus();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onBackPressed() {
        Back_to_intro();
    }

    private void Back_to_intro() {
        Intent goTo_intro = new Intent(this, IntroActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.move_to_right, R.anim.move_to_right);
        ActivityCompat.startActivity(this, goTo_intro, activityOptionsCompat.toBundle());
        finish();
    }
}