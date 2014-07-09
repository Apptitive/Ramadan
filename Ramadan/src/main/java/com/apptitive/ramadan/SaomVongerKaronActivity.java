package com.apptitive.ramadan;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;

import com.apptitive.ramadan.model.Topic;
import com.apptitive.ramadan.utilities.Constants;
import com.apptitive.ramadan.utilities.Utilities;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;


public class SaomVongerKaronActivity extends BaseActionBar implements DetailsFragment.DetailProvider {

    private int iconDrawableId, topicResId;
    private String topicTitle;
    private XmlPullParserFactory parserFactory;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            topicTitle = extras.getString(Constants.topic.EXTRA_TITLE);
            iconDrawableId = extras.getInt(Constants.topic.EXTRA_ICON_ID);
            topicResId = extras.getInt(Constants.topic.EXTRA_DATA_FILE);
        }

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.AB_Green_Ramadan)));
        actionBar.setTitle(Utilities.getBanglaSpannableString(topicTitle, this));
        actionBar.setIcon(getResources().getDrawable(iconDrawableId));
        actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_saom_vonger_karon);
    }

    private Topic getTopic(int topicResId) throws XmlPullParserException, IOException {
        parserFactory = XmlPullParserFactory.newInstance();
        parserFactory.setNamespaceAware(false);
        XmlPullParser xpp = parserFactory.newPullParser();
        xpp.setInput(getResources().openRawResource(topicResId), "utf-8");

        Topic topic = new Topic();

        for (int eventType = xpp.getEventType(); eventType != XmlPullParser.END_DOCUMENT; eventType = xpp.next()) {
            String name = xpp.getName();
            if (eventType == XmlPullParser.START_TAG) {
                if (name.equalsIgnoreCase("topic")) {
                    topic.setHeader(xpp.getAttributeValue(null, "name"));
                    topic.setHasFullText(false);
                }
                if (name.equalsIgnoreCase("details")) {
                    topic.setDetailId(Integer.parseInt(xpp.getAttributeValue(null, "id")));
                }
            }
        }
        xpp.setInput(null);

        return topic;
    }

    @Override
    public Topic getTopic() {
        try {
            return getTopic(topicResId);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Topic(topicTitle, "", false, 1, null);
    }

    @Override
    public int getFileResId() {
        return topicResId;
    }
}
