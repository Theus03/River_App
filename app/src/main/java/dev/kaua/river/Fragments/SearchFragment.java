package dev.kaua.river.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.kaua.river.Activitys.MainActivity;
import dev.kaua.river.Data.Account.DtoAccount;
import dev.kaua.river.R;

public class SearchFragment extends Fragment {

    private View view;
    private DtoAccount account;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity_search, container, false);
        Ids(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.getInstance().Check_Fragments();
    }

    private void Ids(View view) {
        account = MainActivity.getInstance().getUserInformation();
    }
}
