package com.apptitive.ramadan;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.apptitive.ramadan.adapter.TopicListAdapter;
import com.apptitive.ramadan.model.Topic;
import com.apptitive.ramadan.utilities.Constants;
import com.apptitive.ramadan.views.ParallaxListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;

import uk.co.chrisjenx.paralloid.Parallaxor;

public class TopicsFragment extends ListFragment implements TopicListAdapter.OnTopicClickListener {

    private int topicFileResId;
    private XmlPullParserFactory parserFactory;
    private TopicsActivity parentActivity;
    private TopicListAdapter topicListAdapter;
    private ArrayList<Topic> topics;

    public TopicsFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = (TopicsActivity) activity;
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

        for (int eventType = xpp.getEventType(); eventType != XmlPullParser.END_DOCUMENT; eventType = xpp.nextToken()) {
            String name = xpp.getName();
            if (eventType == XmlPullParser.START_TAG) {
                if (name.equalsIgnoreCase("subtopic")) {
                    topic = new Topic();
                    topic.setHeader(xpp.getAttributeValue(null, "name"));
                    topic.setHasFullText(Boolean.parseBoolean(xpp.getAttributeValue(null, "show_all")));
                }
                if (name.equalsIgnoreCase("details")) {
                    topic.setDetailId(Integer.parseInt(xpp.getAttributeValue(null, "id")));
                }
                if (name.equalsIgnoreCase("brief")) {
                    topic.setShortDescription(xpp.getAttributeValue(null, "text"));
                }
            }
            if (eventType == XmlPullParser.END_TAG) {
                if (name.equalsIgnoreCase("subtopic")) {
                    topics.add(topic);
                }
            }
        }
        xpp.setInput(null);
    }

    private void parallaxListViewBackground(int drawable) {
        final ListView listView = getListView();
        if (listView instanceof Parallaxor) {
            ((ParallaxListView) listView).parallaxViewBackgroundBy(listView, getResources().getDrawable(drawable), .35f);
        }
    }

    private boolean isListScrolling(ListView listView) {
        View lastChild = listView.getChildAt(listView.getLastVisiblePosition());
        if (lastChild != null)
            if (lastChild.getBottom() < listView.getHeight())
                return false;
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topics, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setAdapter(topicListAdapter);
        topicListAdapter.notifyDataSetChanged();
        final ListView listView = getListView();
        listView.post(new Runnable() {
            @Override
            public void run() {
                if (isListScrolling(listView)) {
                    int orientation = getResources().getConfiguration().orientation;
                    if (orientation == Configuration.ORIENTATION_PORTRAIT)
                        parallaxListViewBackground(R.drawable.bg_home);
                    else
                        parallaxListViewBackground(R.drawable.bg_home_land);
                }
            }
        });
    }

    @Override
    public void onTopicClick(Topic topic, int position) {
        if (!topic.hasFullText()) {
            Intent i = new Intent(parentActivity, DetailsActivity.class);
            i.putParcelableArrayListExtra(Constants.topic.EXTRA_PARCELABLE_LIST, topics);
            i.putExtra(Constants.topic.EXTRA_VIEWING_NOW, position);
            i.putExtra(Constants.topic.EXTRA_ICON_ID, parentActivity.getIconDrawableId());
            i.putExtra(Constants.topic.EXTRA_DATA_FILE, topicFileResId);
            startActivity(i);
        }
    }
}
