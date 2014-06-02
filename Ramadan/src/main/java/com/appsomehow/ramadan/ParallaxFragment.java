package com.appsomehow.ramadan;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsomehow.ramadan.views.ParallaxScrollView;

import uk.co.chrisjenx.paralloid.OnScrollChangedListener;

public class ParallaxFragment extends Fragment {

    private OnScrollChangedListener activity;

    public ParallaxFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activity = (OnScrollChangedListener) activity;
        }
        catch (ClassCastException cce) {
            throw new ClassCastException(activity.toString() + " must implement OnScrollChangedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parallax, container, false);
        ParallaxScrollView scrollView = (ParallaxScrollView) rootView.findViewById(R.id.parallax_scrollview);
        scrollView.parallaxViewBackgroundBy(scrollView, getResources().getDrawable(R.drawable.bg_parallax), .2f);
        scrollView.setOnScrollChangedListener(activity);

        return rootView;
    }
}
