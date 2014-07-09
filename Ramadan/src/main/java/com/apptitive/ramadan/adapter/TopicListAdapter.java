package com.apptitive.ramadan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.apptitive.ramadan.R;
import com.apptitive.ramadan.model.Topic;
import com.apptitive.ramadan.views.BanglaTextView;

import java.util.List;

/**
 * Created by Iftekhar on 6/4/2014.
 */
public class TopicListAdapter extends ArrayAdapter<Topic> {
    private int layoutResId;
    private TopicClickListener topicClickListener;

    public TopicListAdapter(Context context, int resource, List<Topic> objects, TopicClickListener topicClickListener) {
        super(context, resource, objects);
        layoutResId = resource;
        this.topicClickListener = topicClickListener;
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
        View divider;
        View selectableView;
        BanglaTextView btvHeader;
        BanglaTextView btvShortDesc;
        ImageView imageViewDetails;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Topic topic = getItem(position);
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutResId, parent, false);
            holder = new ViewHolder();
            holder.divider = convertView.findViewById(R.id.divider);
            holder.selectableView = convertView.findViewById(R.id.brief_topic);
            holder.btvHeader = (BanglaTextView) convertView.findViewById(R.id.btv_topic_header);
            holder.btvShortDesc = (BanglaTextView) convertView.findViewById(R.id.btv_topic_hint);
            holder.imageViewDetails = (ImageView) convertView.findViewById(R.id.imageView_details);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.btvHeader.setBanglaText(topic.getHeader());
        holder.btvShortDesc.setBanglaText(topic.getShortDescription());
        if (topic.hasFullText()) {
            holder.divider.setVisibility(View.GONE);
            holder.imageViewDetails.setVisibility(View.GONE);
        } else {
            holder.divider.setVisibility(View.VISIBLE);
            holder.imageViewDetails.setVisibility(View.VISIBLE);
        }

        final ViewHolder finalViewHolder = holder;
        finalViewHolder.selectableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topicClickListener.onTopicClick(topic, position);
            }
        });

        return convertView;
    }

    public interface TopicClickListener {
        void onTopicClick(Topic topic, int position);
    }
}
