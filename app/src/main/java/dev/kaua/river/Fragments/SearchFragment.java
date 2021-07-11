package dev.kaua.river.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.util.ArrayList;

import dev.kaua.river.Activitys.CommonLayoutActivity;
import dev.kaua.river.Activitys.MainActivity;
import dev.kaua.river.Data.Account.AsyncUser_Search;
import dev.kaua.river.Data.Account.DtoAccount;
import dev.kaua.river.R;

@SuppressLint("StaticFieldLeak")
public class SearchFragment extends Fragment {
    private static final int PROFILE_TARGET = 0;

    private AutoCompleteTextView edit_search;
    private View view;
    private static SearchFragment instance;
    private DtoAccount account;
    private ArrayList<String> SuggestionsSearch = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity_search, container, false);
        Ids(view);

        AsyncUser_Search asyncProductsSearchMain = new AsyncUser_Search(edit_search, getActivity());
        //noinspection unchecked
        asyncProductsSearchMain.execute();

        edit_search.setOnItemClickListener((parent, view, position, id) -> {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(requireActivity().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edit_search.getWindowToken(), 0);
            DtoAccount info = (DtoAccount) parent.getItemAtPosition(position);
            Bundle bundle = new Bundle();
            bundle.putString("account_id", info.getAccount_id_cry());
            bundle.putString("name_user", info.getName_user());
            bundle.putString("username", info.getUsername());
            bundle.putString("email", info.getEmail());
            bundle.putString("phone_user", info.getPhone_user());
            bundle.putString("banner_user", info.getBanner_user());
            bundle.putString("profile_image", info.getProfile_image());
            bundle.putString("bio_user", info.getBio_user());
            bundle.putString("url_user", info.getUrl_user());
            bundle.putString("following", info.getFollowing());
            bundle.putString("followers", info.getFollowers());
            bundle.putString("born_date", info.getBorn_date());
            bundle.putString("joined_date", info.getJoined_date());
            bundle.putString("token", info.getToken());
            bundle.putString("verification_level", info.getVerification_level());
            MainActivity.getInstance().GetBundleProfile(bundle);
            ProfileFragment.getInstance().LoadAnotherUser();
            MainActivity.getInstance().CallProfile();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void Ids(View view) {
        instance = this;
        account = MainActivity.getInstance().getUserInformation();
        edit_search = view.findViewById(R.id.edit_Search_Main);
    }
}
