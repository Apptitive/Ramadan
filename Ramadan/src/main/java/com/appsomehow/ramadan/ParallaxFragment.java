package com.appsomehow.ramadan;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.chrisjenx.paralloid.views.ParallaxScrollView;

public class ParallaxFragment extends Fragment {

    public ParallaxFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parallax, container, false);
        ParallaxScrollView scrollView = (ParallaxScrollView) rootView.findViewById(R.id.parallax_scrollview);
        scrollView.parallaxViewBackgroundBy(scrollView, getResources().getDrawable(R.drawable.bg_parallax), .2f);

        return rootView;
    }
}
