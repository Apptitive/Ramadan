package com.apptitive.ramadan.receiver;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.apptitive.ramadan.MainActivity;
import com.apptitive.ramadan.R;
import com.apptitive.ramadan.helper.DbManager;
import com.apptitive.ramadan.model.Region;
import com.apptitive.ramadan.model.TimeTable;
import com.apptitive.ramadan.utilities.Constants;
import com.apptitive.ramadan.utilities.PreferenceHelper;
import com.apptitive.ramadan.utilities.UIUtils;
import com.apptitive.ramadan.utilities.Utilities;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Iftekhar on 7/2/2014.
 */
public class TimeTableWidgetProvider extends AppWidgetProvider {

    private PreferenceHelper preferenceHelper;
    private List<TimeTable> timeTables;
    private List<Region> regions;
    private Region region;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ComponentName thisWidget = new ComponentName(context,
                TimeTableWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.layout_appwidget);
            remoteViews.setImageViewBitmap(R.id.imageView_name,
                    Utilities.getTypefaceBitmap(context, context.getString(R.string.widget_left_label), 20, false));
            remoteViews.setImageViewBitmap(R.id.imageView_time_seheri, Utilities.getTypefaceBitmap(context, context.getString(R.string.sehri_time), 12, false));
            remoteViews.setImageViewBitmap(R.id.imageView_time_iftar, Utilities.getTypefaceBitmap(context, context.getString(R.string.iftar), 12, false));

            DbManager.init(context);
            preferenceHelper = new PreferenceHelper(context);
            timeTables = DbManager.getInstance().getAllTimeTables();
            regions = DbManager.getInstance().getAllRegions();
            region = UIUtils.getSelectedLocation(regions, preferenceHelper.getString(Constants.PREF_KEY_LOCATION, "Dhaka"));

            region = UIUtils.getSelectedLocation(regions, preferenceHelper.getString(Constants.PREF_KEY_LOCATION, "Dhaka"));
            if (region != null) {
                try {
                    if (region.isPositive()) {
                        remoteViews.setImageViewBitmap(R.id.imageView_seheri_time, Utilities.getTypefaceBitmap(context, UIUtils.getSehriIftarTime(region.getIntervalSehri(), timeTables, true, true), 35, true));
                        remoteViews.setImageViewBitmap(R.id.imageView_iftar_time, Utilities.getTypefaceBitmap(context, UIUtils.getSehriIftarTime(region.getIntervalIfter(), timeTables, true, false), 35, true));
                    } else {
                        remoteViews.setImageViewBitmap(R.id.imageView_seheri_time, Utilities.getTypefaceBitmap(context, UIUtils.getSehriIftarTime(-region.getIntervalSehri(), timeTables, true, true), 35, true));
                        remoteViews.setImageViewBitmap(R.id.imageView_iftar_time, Utilities.getTypefaceBitmap(context, UIUtils.getSehriIftarTime(-region.getIntervalIfter(), timeTables, true, false), 35, true));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_sehri_iftar_time, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}
