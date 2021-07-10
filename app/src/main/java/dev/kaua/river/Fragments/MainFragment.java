package dev.kaua.river.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.kaua.river.Activitys.MainActivity;
import dev.kaua.river.Data.Account.DtoAccount;
import dev.kaua.river.Data.Post.Actions.RecommendedPosts;
import dev.kaua.river.R;
import dev.kaua.river.ToastHelper;

/**
 *  Copyright (c) 2021 Kauã Vitório
 *  Official repository https://github.com/Kauavitorio/River_App
 *  Responsible developer: https://github.com/Kauavitorio
 *  @author Kaua Vitorio
 **/

public class MainFragment extends Fragment {
    //private static SwipeRefreshLayout swipe_main;
    private ConstraintLayout btn_create_new_story_main;
    private static RecyclerView recyclerView_Posts;
    private CircleImageView icon_ProfileUser_main;
    private LinearLayout header_main;
    private static Context instance;

    private View view;
    private DtoAccount account;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity_main, container, false);
        Ids(view);

        Picasso.get().load(account.getProfile_image()).into(icon_ProfileUser_main);
        btn_create_new_story_main.setOnClickListener(v -> StoryClick());

        //swipe_main.setOnRefreshListener(MainFragment::RefreshRecycler);

        RefreshRecycler();
        return view;
    }

    private void StoryClick() {
        ToastHelper.toast(requireActivity(), getString(R.string.under_development), 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.getInstance().Check_Fragments();
    }


    public static void RefreshRecycler(){ RecommendedPosts.getRecommendedPosts(instance, recyclerView_Posts/*, swipe_main*/); }

    private void Ids(View view) {
        instance = requireActivity();
        account = MainActivity.getInstance().getUserInformation();
        icon_ProfileUser_main = view.findViewById(R.id.icon_ProfileUser_main);
        btn_create_new_story_main = view.findViewById(R.id.btn_create_new_story_main);
        recyclerView_Posts = view.findViewById(R.id.recyclerView_Posts);
        header_main = view.findViewById(R.id.header_main);
        //swipe_main = view.findViewById(R.id.swipe_main);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        recyclerView_Posts.setLayoutManager(linearLayout);
    }
}
