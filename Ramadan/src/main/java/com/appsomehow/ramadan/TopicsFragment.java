package com.appsomehow.ramadan;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.appsomehow.ramadan.adapter.TopicListAdapter;
import com.appsomehow.ramadan.model.Topic;
import com.appsomehow.ramadan.views.ParallaxListView;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.paralloid.Parallaxor;

public class TopicsFragment extends ListFragment {

    private String[] topicHeaders;
    private String[] topicBriefs;
    private TypedArray fullTexts;
    private Activity parentActivity;
    private TopicListAdapter topicListAdapter;
    private List<Topic> topics;

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
        topics = new ArrayList<Topic>();
        topicListAdapter = new TopicListAdapter(parentActivity, R.layout.list_item_topics, topics);
        topicHeaders = getResources().getStringArray(R.array.header_topic_saom);
        topicBriefs = getResources().getStringArray(R.array.brief_topic_saom);
        fullTexts = getResources().obtainTypedArray(R.array.full_text_topic_saom);
        for(int i = 0; i < topicHeaders.length; i++) {
            topics.add(new Topic(topicHeaders[i], topicBriefs[i], fullTexts.getBoolean(i, false)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_topics, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listView = getListView();
        if(listView instanceof Parallaxor) {
            ((ParallaxListView)listView).parallaxViewBackgroundBy(listView, getResources().getDrawable(R.drawable.bg_parallax), .25f);
        }
        getListView().setAdapter(topicListAdapter);
    }
}
