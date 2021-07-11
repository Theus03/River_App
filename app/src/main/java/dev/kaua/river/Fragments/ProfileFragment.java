package dev.kaua.river.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.kaua.river.Activitys.MainActivity;
import dev.kaua.river.Data.Account.DtoAccount;
import dev.kaua.river.LocalDataBase.DaoAccount;
import dev.kaua.river.R;
import dev.kaua.river.Security.EncryptHelper;
import dev.kaua.river.Tools.Methods;
import dev.kaua.river.Tools.ToastHelper;

public class ProfileFragment extends Fragment {
    //  Fragments Items
    private ImageView img_banner_profile;
    private CircleImageView ic_ProfileUser_profile;
    private TextView txt_user_name, txt_username_name, txt_user_bio_profile, txt_amount_following_profile, txt_amount_followers_profile;
    private Button btn_follow_following_profile;

    private View view;
    private static ProfileFragment instance;

    // User Info
    private DtoAccount account = new DtoAccount();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity_profile, container, false);
        Ids(view);

        DaoAccount db = new DaoAccount(getContext());
        DtoAccount account_follow = db.get_followers_following(account.getAccount_id());
        txt_amount_following_profile.setText(Methods.NumberTrick(Integer.parseInt(account_follow.getFollowing())));
        txt_amount_followers_profile.setText(Methods.NumberTrick(Integer.parseInt(account_follow.getFollowers())));

        Picasso.get().load(account.getBanner_user()).into(img_banner_profile);
        Picasso.get().load(account.getProfile_image()).into(ic_ProfileUser_profile);
        txt_user_name.setText(account.getName_user());
        txt_username_name.setText(account.getUsername());
        txt_user_bio_profile.setText(account.getBio_user());
        btn_follow_following_profile.setBackground(requireActivity().getDrawable(R.drawable.background_button_following));
        btn_follow_following_profile.setEnabled(true);
        btn_follow_following_profile.setText(requireContext().getString(R.string.edit_profile));
        btn_follow_following_profile.setTextColor(requireActivity().getColor(R.color.black));

        btn_follow_following_profile.setOnClickListener(v -> {
            switch (btn_follow_following_profile.getText().toString()){

            }
        });

        return view;
    }

    private void CheckIfFollowOrNot(String account_id) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static ProfileFragment getInstance(){ return instance;}

    public void LoadAnotherUser(){
        Bundle bundle = MainActivity.getInstance().SetBundleProfile();
        if(Integer.parseInt(bundle.getString("account_id")) != account.getAccount_id()){
            Picasso.get().load(bundle.getString("banner_user")).into(img_banner_profile);
            Picasso.get().load(bundle.getString("profile_image")).into(ic_ProfileUser_profile);
            txt_user_name.setText(bundle.getString("name_user"));
            txt_username_name.setText(bundle.getString("username"));
            txt_user_bio_profile.setText(bundle.getString("bio_user"));
            txt_amount_following_profile.setText(Methods.NumberTrick(Integer.parseInt(bundle.getString("following"))));
            txt_amount_followers_profile.setText(Methods.NumberTrick(Integer.parseInt(bundle.getString("followers"))));
            CheckIfFollowOrNot(bundle.getString("account_id"));
        }
    }

    private void Ids(View view) {
        instance = this;
        account = MainActivity.getInstance().getUserInformation();
        img_banner_profile = view.findViewById(R.id.img_banner_profile);
        ic_ProfileUser_profile = view.findViewById(R.id.ic_ProfileUser_profile);
        txt_user_name = view.findViewById(R.id.txt_user_name);
        txt_username_name = view.findViewById(R.id.txt_username_name);
        txt_user_bio_profile = view.findViewById(R.id.txt_user_bio_profile);
        btn_follow_following_profile = view.findViewById(R.id.btn_follow_following_profile);
        txt_amount_following_profile = view.findViewById(R.id.txt_amount_following_profile);
        txt_amount_followers_profile = view.findViewById(R.id.txt_amount_followers_profile);
    }
}
