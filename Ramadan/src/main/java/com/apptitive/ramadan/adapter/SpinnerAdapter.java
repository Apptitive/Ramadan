package com.apptitive.ramadan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.apptitive.ramadan.R;
import com.apptitive.ramadan.utilities.Utilities;

/**
 * Created by Sharif on 7/6/2014.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    private String[] items;
    private Context context;

    public SpinnerAdapter(Context context, int resourceId,
                          String[] items) {
        super(context, resourceId, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String text = items[position];
        View v = super.getView(position, convertView, parent);
        ((TextView) v).setText(Utilities.getBanglaSpannableString(text, context));
      //  ((TextView) v).setTextSize(context.getResources().getDimension(R.dimen.text_size_12));
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        String text = items[position];
         View v = super.getDropDownView(position, convertView, parent);
        ((TextView) v).setText(Utilities.getBanglaSpannableString(text, context));
     //   ((TextView) v).setTextSize(context.getResources().getDimension(R.dimen.text_size_15));
        return v;
    }
}