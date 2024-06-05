package com.ablec.myarchitecture.gridtopager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

    public static int currentPosition;
    private static final String KEY_CURRENT_POSITION = "com.google.samples.gridtopager.key.currentPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            // Return here to prevent adding additional GridFragments when changing orientation.
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, new GridFragment(), GridFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(GridFragment.class.getSimpleName());
        if (fragmentByTag instanceof  GridFragment){
           ((GridFragment) fragmentByTag).scroll();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, GridActivity.class);
        context.startActivity(starter);
    }
}
