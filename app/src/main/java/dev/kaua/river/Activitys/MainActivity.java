package dev.kaua.river.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.kaua.river.Data.Account.DtoAccount;
import dev.kaua.river.Data.Post.Actions.RecommendedPosts;
import dev.kaua.river.Fragments.MainFragment;
import dev.kaua.river.Fragments.SearchFragment;
import dev.kaua.river.JsonHandler;
import dev.kaua.river.Security.EncryptHelper;
import dev.kaua.river.R;
import dev.kaua.river.Security.Login;
import dev.kaua.river.ToastHelper;

/**
 *  Copyright (c) 2021 Kauã Vitório
 *  Official repository https://github.com/Kauavitorio/River_App
 *  Responsible developer: https://github.com/Kauavitorio
 *  @author Kaua Vitorio
 **/

@SuppressLint({"StaticFieldLeak", "UseCompatLoadingForDrawables"})
public class MainActivity extends AppCompatActivity {
    private FrameLayout frameLayoutMain;
    private static ImageView btn_search_main, btn_home_main;
    private CircleImageView btn_profile_main;


    private Bundle bundle;
    private static Bundle args;
    private static FragmentTransaction transaction;
    private static MainActivity instance;
    //  Set preferences
    private SharedPreferences mPrefs;
    public static final String PREFS_NAME = "myPrefs";

    private static final DtoAccount account = new DtoAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Ids();
        account.setProfile_image("https://avatars.githubusercontent.com/u/64799699?v=4");
        LoadMainFragment();


        //  Get all SharedPreferences
        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        bundle = getIntent().getExtras();
        if (sp.contains("pref_email") && sp.contains("pref_password")) StartNavigation();
        else Login.Force_LogOut(this);

        btn_search_main.setOnClickListener(v -> LoadSearchFragment());
        btn_home_main.setOnClickListener(v -> LoadMainFragment());
    }

    private void StartNavigation() {
        getUserInformation();
        LoadMainFragment();
    }

    public static MainActivity getInstance(){ return instance; }

    @SuppressWarnings("ConstantConditions")
    public DtoAccount getUserInformation(){
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //account.setAccount_id(Integer.parseInt(EncryptHelper.decrypt(sp.getString("pref_account_id", null))));
        account.setName_user(EncryptHelper.decrypt(sp.getString("pref_name_user", null)));
        account.setUsername(EncryptHelper.decrypt(sp.getString("pref_username", null)));
        account.setEmail(EncryptHelper.decrypt(sp.getString("pref_email", null)));
        account.setPhone_user(EncryptHelper.decrypt(sp.getString("pref_phone_user", null)));
        account.setBanner_user(EncryptHelper.decrypt(sp.getString("pref_banner_user", null)));
        account.setPhone_user(EncryptHelper.decrypt(sp.getString("pref_profile_image", null)));
        account.setBio_user(EncryptHelper.decrypt(sp.getString("pref_bio_user", null)));
        account.setUrl_user(EncryptHelper.decrypt(sp.getString("pref_url_user", null)));
        account.setFollowing(EncryptHelper.decrypt(sp.getString("pref_following", null)));
        account.setFollowers(EncryptHelper.decrypt(sp.getString("pref_followers", null)));
        account.setBorn_date(EncryptHelper.decrypt(sp.getString("pref_born_date", null)));
        account.setJoined_date(EncryptHelper.decrypt(sp.getString("pref_joined_date", null)));
        account.setToken(EncryptHelper.decrypt(sp.getString("pref_token", null)));
        account.setVerification_level(EncryptHelper.decrypt(sp.getString("pref_verification_level", null)));

        Picasso.get().load(account.getProfile_image()).into(btn_profile_main);
        return account;
    }

    private void LoadMainFragment() {
        Fragment tag_main = getSupportFragmentManager().findFragmentByTag("MAIN_FRAGMENT");
        if (tag_main == null){
            MainFragment mainFragment = new MainFragment();
            args = new Bundle();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.frameLayoutMain, mainFragment, "MAIN_FRAGMENT");
            transaction.commit();
        } else RefreshMain();
    }

    private void LoadSearchFragment() {
        SearchFragment searchFragment = new SearchFragment();
        args = new Bundle();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.frameLayoutMain, searchFragment, "SEARCH_FRAGMENT");
        transaction.commit();
    }

    public void Check_Fragments(){
        btn_search_main.setImageDrawable(getDrawable(R.drawable.ic_search));
        btn_home_main.setImageDrawable(getDrawable(R.drawable.ic_home));
        Fragment tag_main = getSupportFragmentManager().findFragmentByTag("MAIN_FRAGMENT");
        Fragment tag_search = getSupportFragmentManager().findFragmentByTag("SEARCH_FRAGMENT");
        if(tag_main == null) btn_search_main.setImageDrawable(getDrawable(R.drawable.ic_search_select));
        else if (tag_search == null) btn_home_main.setImageDrawable(getDrawable(R.drawable.ic_home_select));
    }

    private void RefreshMain() {
        MainFragment.RefreshRecycler();
    }

    private void Ids() {
        instance = this;
        frameLayoutMain = findViewById(R.id.frameLayoutMain);
        btn_profile_main = findViewById(R.id.btn_profile_main);
        btn_search_main = findViewById(R.id.btn_search_main);
        btn_home_main = findViewById(R.id.btn_home_main);
    }

}