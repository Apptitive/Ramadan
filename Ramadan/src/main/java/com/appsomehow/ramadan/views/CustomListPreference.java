package com.appsomehow.ramadan.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.preference.ListPreference;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.appsomehow.ramadan.R;
import com.appsomehow.ramadan.utilities.Utilities;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;

/**
 * TODO: document your custom view class.
 */
public class CustomListPreference extends ListPreference {


    public CustomListPreference(Context context) {
        super(context);
    }

    public CustomListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView txtTittle = (TextView) view.findViewById(android.R.id.title);
        txtTittle.setTypeface(Utilities.getFont(getContext()));
        //   txtTittle.setText(AndroidCustomFontSupport.getCorrectedBengaliFormat("", Utilities.getFont(getContext()), -1));
    }

    @Override
    protected View onCreateDialogView() {
        new CustomListPreferenceAdapter(getContext());
        return super.onCreateDialogView();
    }

    private class CustomListPreferenceAdapter extends BaseAdapter {
        public CustomListPreferenceAdapter(Context context) {

        }


        @Override
        public int getCount() {
            return 0;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            Log.e("Hi hi calling the layout", "lasl");
            TextView textView = (TextView) convertView.findViewById(android.R.id.title);
            TextView summary = (TextView) convertView.findViewById(android.R.id.summary);
            textView.setTypeface(Utilities.getFont(getContext()));
            summary.setTypeface(Utilities.getFont(getContext()));
            return row;
        }

    }
}
