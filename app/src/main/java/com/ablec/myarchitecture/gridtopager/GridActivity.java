package com.ablec.myarchitecture.gridtopager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ablec.lib.base.BaseActivity;
import com.ablec.myarchitecture.R;
import com.ablec.myarchitecture.gridtopager.fragment.GridFragment;

/**
 * Grid to pager app's main activity.
 */
public class GridActivity extends BaseActivity {

    private static final String KEY_CURRENT_POSITION = "com.google.samples.gridtopager.key.currentPosition";
    public static int currentPosition;

    public static void start(Context context) {
        Intent starter = new Intent(context, GridActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set the toolbar as the activity's app bar.
        setSupportActionBar(findViewById(R.id.toolbar));
        //showNavigationIcon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, new GridFragment(), GridFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(GridFragment.class.getSimpleName());
        if (fragmentByTag instanceof GridFragment) {
            ((GridFragment) fragmentByTag).scroll();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
