package dev.kaua.river.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import dev.kaua.river.Fragments.MainFragment;
import dev.kaua.river.Fragments.ProfileFragment;
import dev.kaua.river.R;

public class CommonLayoutActivity extends AppCompatActivity {
    FrameLayout frameLayoutCommonLayout;
    private static Bundle args;
    private static FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_layout);
        frameLayoutCommonLayout = findViewById(R.id.frameLayoutCommonLayout);

        args = getIntent().getExtras();
        switch (args.getInt("targeting_place")){
            case 0:
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setArguments(args);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frameLayoutCommonLayout, profileFragment, "PROFILE_FRAGMENT");
                transaction.commit();
                break;
        }
    }
}