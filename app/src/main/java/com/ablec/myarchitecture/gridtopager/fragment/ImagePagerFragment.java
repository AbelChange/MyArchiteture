/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ablec.myarchitecture.gridtopager.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.app.SharedElementCallback;
import androidx.viewpager2.widget.ViewPager2;

import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ablec.myarchitecture.R;
import com.ablec.myarchitecture.gridtopager.GridActivity;
import com.ablec.myarchitecture.gridtopager.adapter.ImagePagerAdapter;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.List;
import java.util.Map;

/**
 * A fragment for displaying a pager of images.
 */
public class ImagePagerFragment extends Fragment {

    private ViewPager2 viewPager;
    private ImagePagerAdapter imagePagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewPager = (ViewPager2) inflater.inflate(R.layout.fragment_pager, container, false);
        imagePagerAdapter = new ImagePagerAdapter(this);
        viewPager.setAdapter(imagePagerAdapter);
        viewPager.setCurrentItem(GridActivity.currentPosition, false);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                GridActivity.currentPosition = position;
                requireActivity().setResult(100);
            }
        });
        prepareSharedElementTransition();
        if (savedInstanceState == null) {
            postponeEnterTransition();
        }
        return viewPager;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Prepares the shared element transition from and back to the grid fragment.
     */
    private void prepareSharedElementTransition() {
        // A similar mapping is set at the GridFragment with a setExitSharedElementCallback.
        requireActivity().getWindow().setSharedElementEnterTransition(TransitionInflater.from(requireActivity())
                .inflateTransition(R.transition.image_shared_element_transition));
        requireActivity().setEnterSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        Fragment currentFragment = imagePagerAdapter.getItem(GridActivity.currentPosition);
                        if (currentFragment == null) {
                            return;
                        }
                        View view = currentFragment.getView();
                        if (view == null) {
                            return;
                        }
                        sharedElements.put(names.get(0), view.findViewById(R.id.image));
                    }
                });
    }
}
