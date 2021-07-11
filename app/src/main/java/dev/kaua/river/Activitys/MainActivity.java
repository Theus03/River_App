package dev.kaua.river.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.kaua.river.Data.Account.DtoAccount;
import dev.kaua.river.Fragments.FragmentPageAdapter;
import dev.kaua.river.Fragments.MainFragment;
import dev.kaua.river.Fragments.SearchFragment;
import dev.kaua.river.Security.EncryptHelper;
import dev.kaua.river.R;
import dev.kaua.river.Security.Login;
import dev.kaua.river.Tools.Methods;

/**
 *  Copyright (c) 2021 Kauã Vitório
 *  Official repository https://github.com/Kauavitorio/River_App
 *  Responsible developer: https://github.com/Kauavitorio
 *  @author Kaua Vitorio
 **/

@SuppressLint({"StaticFieldLeak", "UseCompatLoadingForDrawables"})
public class MainActivity extends AppCompatActivity {
    private static ImageView btn_search_main, btn_home_main;
    private CircleImageView btn_profile_main;
    private LinearLayout container_btn_profile_main;
    private static ViewPager viewPager;
    private FragmentPageAdapter adapter;


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


        // Create an adapter that
        // knows which fragment should
        // be shown on each page
                adapter
                = new FragmentPageAdapter(
                getSupportFragmentManager());

        // Set the adapter onto
        // the view pager
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1, true);

        account.setProfile_image("https://avatars.githubusercontent.com/u/64799699?v=4");
        //LoadMainFragment();


        //  Get all SharedPreferences
        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        bundle = getIntent().getExtras();
        if (sp.contains("pref_account_id") && sp.contains("pref_username")) StartNavigation();
        else Login.Force_LogOut(this);

        btn_search_main.setOnClickListener(v -> LoadSearchFragment());
        btn_home_main.setOnClickListener(v -> LoadMainFragment());

        container_btn_profile_main.setOnClickListener(v -> CallProfile());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                adapter.notifyDataSetChanged();
                viewPager.setCurrentItem(position, true);
                Check_Fragments(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull @NotNull View page, float position) {
                page.setTranslationX(-position * page.getWidth());

                if (Math.abs(position) <= 0.5) {
                    page.setVisibility(View.VISIBLE);
                    page.setScaleX(1 - Math.abs(position));
                    page.setScaleY(1 - Math.abs(position));
                } else if (Math.abs(position) > 0.5)
                    page.setVisibility(View.GONE);


                if (position < -1) // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0);
                else if (position <= 0) {   // [-1,0]
                    page.setAlpha(1);
                    page.setRotation(360 * Math.abs(position));
                }
                else if (position <= 1) {   // (0,1]
                    page.setAlpha(1);
                    page.setRotation(-360 * Math.abs(position));
                }
                else // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0);
            }
        });
    }

    private void StartNavigation() {
        getUserInformation();
        LoadMainFragment();
    }

    public static MainActivity getInstance(){ return instance; }

    Bundle bundle_profile;
    public void GetBundleProfile(Bundle bundle){
        bundle_profile = bundle;
    }
    public Bundle SetBundleProfile(){ return bundle_profile; }

    public void CallProfile(){
        viewPager.setCurrentItem(3, true);
        adapter.notifyDataSetChanged();
    }

    @SuppressWarnings("ConstantConditions")
    public DtoAccount getUserInformation(){
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        account.setAccount_id(Integer.parseInt(EncryptHelper.decrypt(sp.getString("pref_account_id", null))));
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
        viewPager.setCurrentItem(1, true);
        adapter.notifyDataSetChanged();
    }

    private void LoadSearchFragment() {
        viewPager.setCurrentItem(2, true);
        adapter.notifyDataSetChanged();
    }

    public void Check_Fragments(int position){
        btn_search_main.setImageDrawable(getDrawable(R.drawable.ic_search));
        btn_home_main.setImageDrawable(getDrawable(R.drawable.ic_home));
        btn_profile_main.setBorderWidth(0);
        if(position == 1) {
            btn_home_main.setImageDrawable(getDrawable(R.drawable.ic_home_select));
            MainFragment.RefreshRecycler();
        }
        else if(position == 2) btn_search_main.setImageDrawable(getDrawable(R.drawable.ic_search_select));
        else if(position == 3) btn_profile_main.setBorderWidth(3);
    }


    private void Ids() {
        instance = this;
        viewPager = (ViewPager)findViewById(R.id.viewpager_main);
        btn_profile_main = findViewById(R.id.btn_profile_main);
        btn_search_main = findViewById(R.id.btn_search_main);
        btn_home_main = findViewById(R.id.btn_home_main);
        container_btn_profile_main = findViewById(R.id.container_btn_profile_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInformation();
        Methods.LoadFollowersAndFollowing(this);
    }
}