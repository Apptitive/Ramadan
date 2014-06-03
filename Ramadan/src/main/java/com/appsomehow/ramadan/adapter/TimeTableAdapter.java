package com.appsomehow.ramadan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.appsomehow.ramadan.R;
import com.appsomehow.ramadan.model.TimeTable;
import com.appsomehow.ramadan.model.TimeTableViewModel;
import com.appsomehow.ramadan.utilities.UIUtils;
import com.appsomehow.ramadan.utilities.Utilities;

import java.util.List;

/**
 * Created by rayhan on 6/3/2014.
 */
public class TimeTableAdapter extends ArrayAdapter<TimeTable> {

    Context context;
    int layoutResourceId;

    public TimeTableAdapter(Context context, int layout, List<TimeTable> timeTables) {
        super(context, layout, timeTables);
        this.context = context;
        layoutResourceId = layout;
    }

    private class ViewHolder {
        TextView date;
        TextView sehriTime;
        TextView ifterTime;
        TextView rojaCount;
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TimeTable timeTable = getItem(position);

        View inflatedView = LayoutInflater.from(context).inflate(
                layoutResourceId, parent, false);


        ViewHolder holder = null;
        if (convertView == null) {

            convertView = inflatedView;

            holder = new ViewHolder();

            holder.date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.sehriTime = (TextView) convertView.findViewById(R.id.tv_sehri_time);
            holder.ifterTime = (TextView) convertView.findViewById(R.id.tv_ifter_time);
            holder.rojaCount = (TextView) convertView.findViewById(R.id.tv_roja_count);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.date.setText(Utilities.getBanglaText(timeTable.getDateInBangla(), context));
        holder.sehriTime.setText(Utilities.getBanglaText(timeTable.getSehriTime(), context));
        holder.ifterTime.setText(Utilities.getBanglaText(timeTable.getIfterTime(), context));
        holder.rojaCount.setText(Utilities.getBanglaText(timeTable.getRojaCount(), context));

        /*holder.date.setText(timeTable.getDate());
        holder.sehriTime.setText(timeTable.getSehriTime());
        holder.ifterTime.setText(timeTable.getIfterTime());
        holder.rojaCount.setText(timeTable.getRojaCount())*/;


        if (Integer.parseInt(timeTable.getId()) % 2 == 0)
            convertView.setBackgroundColor(context.getResources().getColor(R.color.table_row_background));


        return convertView;
    }
}
