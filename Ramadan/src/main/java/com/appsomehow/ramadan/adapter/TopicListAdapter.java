package com.appsomehow.ramadan.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.appsomehow.ramadan.R;
import com.appsomehow.ramadan.model.Topic;
import com.appsomehow.ramadan.views.BanglaTextView;

import java.util.List;

/**
 * Created by Iftekhar on 6/4/2014.
 */
public class TopicListAdapter extends ArrayAdapter<Topic> {
    private int layoutResId;

    public TopicListAdapter(Context context, int resource, List<Topic> objects) {
        super(context, resource, objects);
        layoutResId = resource;
    }

    @Override
    public int getViewTypeCount() {
        if (getCount() != 0)
            return getCount();

        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private class ViewHolder {
        BanglaTextView btvHeader;
        BanglaTextView btvShortDesc;
        View divider;
        ImageView imageViewDetails;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Topic topic = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutResId, parent, false);
            holder = new ViewHolder();
            holder.btvHeader = (BanglaTextView) convertView.findViewById(R.id.btv_topic_header);
            holder.btvShortDesc = (BanglaTextView) convertView.findViewById(R.id.btv_topic_hint);
            holder.divider = convertView.findViewById(R.id.divider);
            holder.imageViewDetails = (ImageView) convertView.findViewById(R.id.imageView_details);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.btvHeader.setText(topic.getHeader());
        holder.btvShortDesc.setText(topic.getShortDescription());
        if (topic.isFullText()) {
            holder.divider.setVisibility(View.VISIBLE);
            holder.imageViewDetails.setVisibility(View.VISIBLE);
        } else {
            holder.divider.setVisibility(View.GONE);
            holder.imageViewDetails.setVisibility(View.GONE);
        }
        return convertView;
    }
}
