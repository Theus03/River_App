package dev.kaua.river.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ChatFragment extends Fragment {
    private static final int PROFILE_TARGET = 0;

    private AutoCompleteTextView edit_search;
    private View view;
    private static ChatFragment instance;
    private DtoAccount account;
    private ArrayList<String> SuggestionsSearch = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity_chat, container, false);
        Ids(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void Ids(View view) {
        instance = this;
    }
}
