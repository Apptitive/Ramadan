package com.apptitive.ramadan;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptitive.ramadan.adapter.DetailsListAdapter;
import com.apptitive.ramadan.model.Detail;
import com.apptitive.ramadan.model.Topic;
import com.apptitive.ramadan.utilities.Constants;
import com.google.analytics.tracking.android.EasyTracker;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends ListFragment {

    private XmlPullParserFactory parserFactory;
    private DetailProvider detailProvider;
    private DetailsListAdapter detailsListAdapter;
    private List<Detail> details;

    public DetailsFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(getActivity()).activityStart(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(getActivity()).activityStop(getActivity());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            detailProvider = (DetailProvider) activity;
        } catch (ClassCastException cce) {
            Log.e(this.getTag(), "Parent activity must implement DetailProvider");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        details = new ArrayList<Detail>();
        detailsListAdapter = new DetailsListAdapter(getActivity(), details);
        try {
            populateList(detailProvider.getFileResId(), detailProvider.getTopic().getDetailId());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setListAdapter(detailsListAdapter);
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    private void populateList(int fileResId, int detailId) throws XmlPullParserException, IOException {
        parserFactory = XmlPullParserFactory.newInstance();
        parserFactory.setNamespaceAware(false);
        XmlPullParser xpp = parserFactory.newPullParser();
        xpp.setInput(getResources().openRawResource(fileResId), "utf-8");

        boolean foundDetail = false;

        for (int eventType = xpp.getEventType(); eventType != XmlPullParser.END_DOCUMENT; eventType = xpp.next()) {
            String name = xpp.getName();
            if (eventType == XmlPullParser.START_TAG) {
                if (name.equalsIgnoreCase("details")) {
                    if (detailId == Integer.parseInt(xpp.getAttributeValue(null, "id"))) {
                        foundDetail = true;
                    } else {
                        foundDetail = false;
                    }
                }
                if (name.equalsIgnoreCase("part")) {
                    if (foundDetail) {
                        details.add(new Detail(xpp.getAttributeValue(null, "text"), findViewTypeValue(xpp.getAttributeValue(null, "view_type"))));
                    }
                }
            }
            if (eventType == XmlPullParser.END_TAG) {
                continue;
            }
        }
        xpp.setInput(null);
    }

    private int findViewTypeValue(String vt) {
        if (vt.equalsIgnoreCase("t"))
            return Constants.detail.VIEW_TYPE_TEXT_ONLY;
        else if (vt.equalsIgnoreCase("b"))
            return Constants.detail.VIEW_TYPE_BULLET;
        else if (vt.equalsIgnoreCase("h"))
            return Constants.detail.VIEW_TYPE_HEADER_ONLY;
        else if (vt.equalsIgnoreCase("a"))
            return Constants.detail.VIEW_TYPE_ARABIC;
        else if (vt.equalsIgnoreCase("ab"))
            return Constants.detail.VIEW_TYPE_ARABIC_BULLET_ALIGN;
        else if (vt.equalsIgnoreCase("tb"))
            return Constants.detail.VIEW_TYPE_TEXT_BULLET_ALIGN;
        return 0;
    }

    public void changeTopic(Topic topic) {
        details.clear();
        try {
            populateList(detailProvider.getFileResId(), topic.getDetailId());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getListView().setAdapter(detailsListAdapter);
    }

    public interface DetailProvider {
        Topic getTopic();
        int getFileResId();
    }
}
