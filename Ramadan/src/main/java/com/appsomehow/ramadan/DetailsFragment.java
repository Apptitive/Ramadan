package com.appsomehow.ramadan;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.appsomehow.ramadan.adapter.DetailsListAdapter;
import com.appsomehow.ramadan.model.Detail;
import com.appsomehow.ramadan.model.Topic;
import com.appsomehow.ramadan.views.ParallaxListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.paralloid.Parallaxor;

public class DetailsFragment extends ListFragment {

    private XmlPullParserFactory parserFactory;
    private DetailsActivity parentActivity;
    private DetailsListAdapter detailsListAdapter;
    private List<Detail> details;

    public DetailsFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = (DetailsActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        details = new ArrayList<Detail>();
        detailsListAdapter = new DetailsListAdapter(parentActivity, details);
        int file_res_id = parentActivity.getFileResId();
        int detail_id = parentActivity.getDetailId();
        try {
            populateList(file_res_id, detail_id);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                if(name.equalsIgnoreCase("details")) {
                    if (detailId == Integer.parseInt(xpp.getAttributeValue(null, "id"))) {
                        foundDetail = true;
                    } else {
                        foundDetail = false;
                    }
                }
            }
            if (eventType == XmlPullParser.END_TAG) {
                if (name.equalsIgnoreCase("part")) {
                    if (foundDetail) {
                        details.add(new Detail(xpp.getAttributeValue(null, "text"), findViewTypeValue(xpp.getAttributeValue(null, "view_type"))));
                    }
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

    private int findViewTypeValue(String vt) {
        if (vt.equalsIgnoreCase("t"))
            return 0;
        else if (vt.equalsIgnoreCase("b"))
            return 1;
        else if (vt.equalsIgnoreCase("h"))
            return 2;
        return 0;
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
        getListView().setAdapter(detailsListAdapter);
    }
}
