package com.appsomehow.ramadan;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsomehow.ramadan.views.BanglaTextView;
import com.appsomehow.ramadan.views.ParallaxScrollView;

import uk.co.chrisjenx.paralloid.OnScrollChangedListener;

public class TopicsFragment extends Fragment {

    Activity parentActivity;

    public TopicsFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_topics_saom, container, false);
        ParallaxScrollView scrollView = (ParallaxScrollView) rootView.findViewById(R.id.parallax_scrollview);
        scrollView.parallaxViewBackgroundBy(scrollView, getResources().getDrawable(R.drawable.bg_parallax), .2f);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((BanglaTextView) view.findViewById(R.id.header_saom_1).findViewById(R.id.btv_topic_header)).setBanglaText(parentActivity.getResources().getString(R.string.saom_importance));
        ((BanglaTextView) view.findViewById(R.id.header_saom_2).findViewById(R.id.btv_topic_header)).setBanglaText(parentActivity.getResources().getString(R.string.saom_masayel));
        ((BanglaTextView) view.findViewById(R.id.header_saom_3).findViewById(R.id.btv_topic_header)).setBanglaText(parentActivity.getResources().getString(R.string.sehri));
        ((BanglaTextView) view.findViewById(R.id.header_saom_4).findViewById(R.id.btv_topic_header)).setBanglaText(parentActivity.getResources().getString(R.string.iftari));
        ((BanglaTextView) view.findViewById(R.id.brief_saom_1).findViewById(R.id.btv_topic_hint)).setBanglaText(parentActivity.getResources().getString(R.string.topics_desc_text));
        ((BanglaTextView) view.findViewById(R.id.brief_saom_2).findViewById(R.id.btv_topic_hint)).setBanglaText(parentActivity.getResources().getString(R.string.topics_desc_text));
        ((BanglaTextView) view.findViewById(R.id.brief_saom_3).findViewById(R.id.btv_topic_hint)).setBanglaText(parentActivity.getResources().getString(R.string.topics_desc_text));
        ((BanglaTextView) view.findViewById(R.id.brief_saom_4).findViewById(R.id.btv_topic_hint)).setBanglaText(parentActivity.getResources().getString(R.string.topics_desc_text));
    }
}
