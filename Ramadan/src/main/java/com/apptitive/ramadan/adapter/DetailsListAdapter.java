package com.apptitive.ramadan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.apptitive.ramadan.R;
import com.apptitive.ramadan.model.Detail;
import com.apptitive.ramadan.utilities.Constants;
import com.apptitive.ramadan.views.ArabicTextView;
import com.apptitive.ramadan.views.BanglaTextView;

import java.util.List;


/**
 * Created by Iftekhar on 6/5/2014.
 */
public class DetailsListAdapter extends BaseAdapter {

    private static final int VIEW_TYPE_COUNT = 6;

    private List<Detail> details;
    private Context context;

    public DetailsListAdapter(Context context, List<Detail> details) {
        this.context = context;
        this.details = details;
    }

    @Override
    public int getItemViewType(int position) {
        return details.get(position).getViewType();
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getCount() {
        return details.size();
    }

    @Override
    public Detail getItem(int position) {
        return details.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        BanglaTextView btvDetail;
        ArabicTextView atvDetail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Detail detail = getItem(position);
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
                case Constants.detail.VIEW_TYPE_TEXT_BULLET_ALIGN:
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_item_detail_text_bullet_align, parent, false);
                    holder.btvDetail = (BanglaTextView) convertView.findViewById(R.id.btv_plainText_bullet_align);
                    break;
                case Constants.detail.VIEW_TYPE_ARABIC:
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_item_detail_arabic, parent, false);
                    holder.atvDetail = (ArabicTextView) convertView.findViewById(R.id.btv_plainText_arabic);
                    break;
                case Constants.detail.VIEW_TYPE_ARABIC_BULLET_ALIGN:
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_item_detail_arabic_bullet_align, parent, false);
                    holder.atvDetail = (ArabicTextView) convertView.findViewById(R.id.btv_plainText_arabic_bullet_align);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (holder.btvDetail != null)
            holder.btvDetail.setBanglaText(detail.getText());
        if (holder.atvDetail != null)
            holder.atvDetail.setArabicText(detail.getText());

        return convertView;
    }
}
