package com.appsomehow.ramadan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsomehow.ramadan.R;
import com.appsomehow.ramadan.model.DetailFragment;
import com.appsomehow.ramadan.utilities.Constants;
import com.appsomehow.ramadan.views.BanglaTextView;

import java.util.List;

/**
 * Created by Iftekhar on 6/5/2014.
 */
public class DetailsListAdapter extends BaseAdapter{

    private static final int VIEW_TYPE_COUNT = 3;

    private List<DetailFragment> detailFragmentList;
    private Context context;

    public DetailsListAdapter(Context context, List<DetailFragment> detailFragmentList) {
        this.context = context;
        this.detailFragmentList = detailFragmentList;
    }

    @Override
    public int getItemViewType(int position) {
        return detailFragmentList.get(position).getViewType();
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getCount() {
        return detailFragmentList.size();
    }

    @Override
    public DetailFragment getItem(int position) {
        return detailFragmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        BanglaTextView btvDetail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DetailFragment detailFragment = getItem(position);
        int viewType = getItemViewType(position);
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            switch (viewType) {
                case Constants.detail.VIEW_TYPE_TEXT_ONLY:
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_item_detail_textonly, parent, false);
                    holder.btvDetail = (BanglaTextView) convertView.findViewById(R.id.btv_plainText);
                    break;
                case Constants.detail.VIEW_TYPE_BULLET:
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_item_detail_bullet, parent, false);
                    holder.btvDetail = (BanglaTextView) convertView.findViewById(R.id.btv_bulletText);
                    break;
                case Constants.detail.VIEW_TYPE_HEADER_ONLY:
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_item_detail_headeronly, parent, false);
                    holder.btvDetail = (BanglaTextView) convertView.findViewById(R.id.btv_headerText);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.btvDetail.setText(detailFragment.getText());

        return convertView;
    }
}
