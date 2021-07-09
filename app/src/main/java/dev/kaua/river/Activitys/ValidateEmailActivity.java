package dev.kaua.river.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import dev.kaua.river.Data.Account.DtoAccount;
import dev.kaua.river.Data.Validation.ValidationServices;
import dev.kaua.river.Security.EncryptHelper;
import dev.kaua.river.LoadingDialog;
import dev.kaua.river.R;
import dev.kaua.river.Security.Login;
import dev.kaua.river.Warnings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ValidateEmailActivity extends AppCompatActivity {
    private TextView txt_who_is_sent;
    private TextInputEditText edit_verification_code;
    private Button btn_next;
    private ImageView btn_back;
    private TextView txt_didNot_receive_email_validate;

    private String account_id, password, email_user;

    final Retrofit retrofitUser = new Retrofit.Builder()
            .baseUrl("https://dev-river-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_email);
        Ids();

        //  Get User Information form SignUp
        Bundle bundle = getIntent().getExtras();
        account_id = bundle.getString("account_id");
        password = bundle.getString("password");
        email_user = bundle.getString("email_user");
        int type_validate = bundle.getInt("type_validate");
        if(type_validate == 0){
            //  Show Account Created Alert
            Warnings.Base_Sheet_Alert(this, getString(R.string.account_was_created), false);
            txt_who_is_sent.setText(getString(R.string.the_code_has_been_sent, email_user.replace(" ", "")));
        }
        else{
            Warnings.Base_Sheet_Alert(this, getString(R.string.unable_to_login), false);
            txt_who_is_sent.setText(getString(R.string.the_code_has_been_sent_no_email));
        }

        //  Set back click
        btn_back.setOnClickListener(v -> Warnings.Sheet_Really_want_to_leave_emailValidation(this));


        //  Set Didn't receive email click
        txt_didNot_receive_email_validate.setOnClickListener(v -> Warnings.DidNot_receive_email(this, getString(R.string.resend_code_desc), getString(R.string.resend_code), 0));

        //  Set Edit Code TextWatcher
        edit_verification_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 6){
                    btn_next.setEnabled(true);
                    btn_next.setBackgroundResource(R.drawable.custom_button_next);
                }else{
                    btn_next.setEnabled(false);
                    btn_next.setBackgroundResource(R.drawable.custom_button_disable_next);
                }
            }
        });

        //  Button Next click
        btn_next.setOnClickListener(v -> {
            LoadingDialog loadingDialog = new LoadingDialog(ValidateEmailActivity.this);
            loadingDialog.startLoading();
            DtoAccount account = new DtoAccount();
            account.setAccount_id_cry(EncryptHelper.encrypt(account_id + ""));
            account.setVerify_id(EncryptHelper.encrypt(edit_verification_code.getText().toString()));
            ValidationServices services = retrofitUser.create(ValidationServices.class);
            Call<DtoAccount> call = services.validate_email(account);
            call.enqueue(new Callback<DtoAccount>() {
                @Override
                public void onResponse(@NotNull Call<DtoAccount> call, @NotNull Response<DtoAccount> response) {
                    loadingDialog.dismissDialog();
                    if(response.code() == 200) Login.DoLogin(ValidateEmailActivity.this, email_user, password);
                    else if(response.code() == 203)
                        //  Validation Code is Invalid
                        Warnings.Base_Sheet_Alert(ValidateEmailActivity.this, getString(R.string.the_validation_code_is_invalid), true);
                    else
                        Warnings.showWeHaveAProblem(ValidateEmailActivity.this);
                }

                @Override
                public void onFailure(@NotNull Call<DtoAccount> call, @NotNull Throwable t) {
                    loadingDialog.dismissDialog();
                    Warnings.showWeHaveAProblem(ValidateEmailActivity.this);
                }
            });
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        edit_verification_code.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onBackPressed() {
        Warnings.Sheet_Really_want_to_leave_emailValidation(this);
    }

    private void Ids() {
        txt_who_is_sent = findViewById(R.id.txt_who_is_sent_validate_email);
        edit_verification_code = findViewById(R.id.edit_verification_code_validate_email);
        txt_didNot_receive_email_validate = findViewById(R.id.txt_didNot_receive_email_validate_email);
        btn_back = findViewById(R.id.btn_back_validate_email);
        btn_next = findViewById(R.id.btn_next_validate_email);
        btn_next.setEnabled(false);
        btn_next.setBackgroundResource(R.drawable.custom_button_disable_next);
    }
}