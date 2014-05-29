package com.appsomehow.ramadan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsomehow.ramadan.views.BanglaTextView;
import com.appsomehow.ramadan.views.JustifiedTextView;
import com.appsomehow.ramadan.views.StickyParallaxScrollView;

public class ParallaxFragment extends Fragment {

    public ParallaxFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parallax, container, false);
        StickyParallaxScrollView scrollView = (StickyParallaxScrollView) rootView.findViewById(R.id.sticky_parallax_scrollview);
        scrollView.parallaxViewBackgroundBy(scrollView, getResources().getDrawable(R.drawable.bg_parallax), .2f);

        return rootView;
    }
}
