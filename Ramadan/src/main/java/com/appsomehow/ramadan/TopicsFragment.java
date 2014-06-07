package com.appsomehow.ramadan;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.appsomehow.ramadan.adapter.TopicListAdapter;
import com.appsomehow.ramadan.model.Topic;
import com.appsomehow.ramadan.utilities.Constants;
import com.appsomehow.ramadan.views.ParallaxListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.paralloid.Parallaxor;

public class TopicsFragment extends ListFragment implements TopicListAdapter.OnTopicClickListener {

    private int topicFileResId;
    private XmlPullParserFactory parserFactory;
    private TopicsActivity parentActivity;
    private TopicListAdapter topicListAdapter;
    private List<Topic> topics;

    public TopicsFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = (TopicsActivity)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topics = new ArrayList<Topic>();
        topicListAdapter = new TopicListAdapter(parentActivity, R.layout.list_item_topics, topics, this);
        topicFileResId = parentActivity.getTopicResId();
        try {
            populateList(topicFileResId);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateList(int topicResId) throws XmlPullParserException, IOException {
        parserFactory = XmlPullParserFactory.newInstance();
        parserFactory.setNamespaceAware(false);
        XmlPullParser xpp = parserFactory.newPullParser();

        xpp.setInput(getResources().openRawResource(topicResId), "utf-8");
        Topic topic = null;
        for(int eventType = xpp.getEventType(); eventType != XmlPullParser.END_DOCUMENT; eventType = xpp.next()) {
            String name = xpp.getName();
            if(eventType == XmlPullParser.START_TAG) {
                if(name.equalsIgnoreCase("subtopic")) {
                    topic = new Topic();
                    topic.setHeader(xpp.getAttributeValue(null, "name"));
                    topic.setFullText(Boolean.parseBoolean(xpp.getAttributeValue(null, "show_all")));
                }
                if(name.equalsIgnoreCase("details")) {
                    topic.setDetailId(Integer.parseInt(xpp.getAttributeValue(null, "id")));
                }
            }
            if(eventType == XmlPullParser.END_TAG) {
                if(name.equalsIgnoreCase("subtopic")) {
                    topics.add(topic);
                }
                if(name.equalsIgnoreCase("brief")) {
                    topic.setShortDescription(xpp.getAttributeValue(null, "text"));
                }
            }
        }
        xpp.setInput(null);
    }

    private void parallaxListViewBackground(int drawable) {
        final ListView listView = getListView();
        if(listView instanceof Parallaxor) {
            ((ParallaxListView)listView).parallaxViewBackgroundBy(listView, getResources().getDrawable(drawable), .25f);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parallax_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
            parallaxListViewBackground(R.drawable.bg_home);
        else
            parallaxListViewBackground(R.drawable.bg_home_land);
        getListView().setAdapter(topicListAdapter);
    }

    @Override
    public void onTopicClick(Topic topic) {
        Intent i = new Intent(parentActivity, DetailsActivity.class);
        i.putExtra(Constants.detail.EXTRA_DETAIL_ID, topic.getDetailId());
        i.putExtra(Constants.detail.EXTRA_FILE_RES_ID, topicFileResId);
        startActivity(i);
    }
}
