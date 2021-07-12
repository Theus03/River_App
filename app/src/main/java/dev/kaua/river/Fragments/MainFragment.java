package dev.kaua.river.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.kaua.river.Activitys.MainActivity;
import dev.kaua.river.Data.Account.DtoAccount;
import dev.kaua.river.Data.Post.Actions.RecommendedPosts;
import dev.kaua.river.R;
import dev.kaua.river.Tools.ToastHelper;

/**
 *  Copyright (c) 2021 Kauã Vitório
 *  Official repository https://github.com/Kauavitorio/River_App
 *  Responsible developer: https://github.com/Kauavitorio
 *  @author Kaua Vitorio
 **/

@SuppressLint("StaticFieldLeak")
public class MainFragment extends Fragment {
    //private static SwipeRefreshLayout swipe_main;
    private ConstraintLayout btn_create_new_story_main;
    private static RecyclerView recyclerView_Posts;
    private CircleImageView icon_ProfileUser_main;
    private LinearLayout header_main;
    private static Context instance;
    private static RelativeLayout loadingPanel;
    private Handler timer = new Handler();


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
        timer.postDelayed(MainFragment::RefreshRecycler,1000);

        return view;
    }

    private void StoryClick() {
        ToastHelper.toast(requireActivity(), getString(R.string.under_development), 0);
    }


    public static void RefreshRecycler(){ RecommendedPosts.getRecommendedPosts(instance, recyclerView_Posts, loadingPanel); }

    private void Ids(View view) {
        instance = requireActivity();
        account = MainActivity.getInstance().getUserInformation();
        loadingPanel = view.findViewById(R.id.loadingPanel);
        icon_ProfileUser_main = view.findViewById(R.id.icon_ProfileUser_main);
        btn_create_new_story_main = view.findViewById(R.id.btn_create_new_story_main);
        recyclerView_Posts = view.findViewById(R.id.recyclerView_Posts);
        header_main = view.findViewById(R.id.header_main);
        //swipe_main = view.findViewById(R.id.swipe_main);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        recyclerView_Posts.setLayoutManager(linearLayout);
    }
}
