package com.ablec.myarchitecture.gridtopager.adapter;

import static com.ablec.myarchitecture.gridtopager.adapter.ImageData.IMAGE_DRAWABLES;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ablec.myarchitecture.gridtopager.fragment.ImageFragment;

public class ImagePagerAdapter extends FragmentStateAdapter {

  private Fragment mFragment;

  public ImagePagerAdapter(Fragment fragment) {
    super(fragment);
    mFragment = fragment;
  }


  @NonNull
  @Override
  public Fragment createFragment(int position) {
    return ImageFragment.newInstance(IMAGE_DRAWABLES[position]);
  }

  @Override
  public int getItemCount() {
    return IMAGE_DRAWABLES.length;
  }

  @Override
  public long getItemId(int position) {
    return super.getItemId(position);
  }

  public Fragment getItem(int position){
    return mFragment.getChildFragmentManager().findFragmentByTag("f"+position);
  }
}
