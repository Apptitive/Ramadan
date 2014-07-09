package com.apptitive.ramadan.utilities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.TypedValue;

import com.apptitive.ramadan.R;
import com.apptitive.ramadan.receiver.NotificationCancelReceiver;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;
import com.dibosh.experiments.android.support.customfonthelper.utils.TypefaceSpan;

import java.util.Locale;

import androidbangladesh.bengali.support.BengaliUnicodeString;

/**
 * Created by Sharif on 5/27/2014.
 */
public class Utilities {

    public static Typeface getFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(),
                "fonts/" + context.getString(R.string.font_solaimanlipi));
    }

    public static android.text.SpannableString getBanglaSpannableString(String banglaText, Context context) {
        if (banglaText == null) {
            return new SpannableString(new String(""));
        }
        if (isBuildAboveThirteen()) {
            SpannableString spannableString = new SpannableString(banglaText);
            if (isBanglaAvailable()) {
                TypefaceSpan span = new TypefaceSpan(getFont(context));
                spannableString.setSpan(span, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return spannableString;
        }
        return AndroidCustomFontSupport.getCorrectedBengaliFormat(banglaText, getFont(context), -1);
    }

    public static void customNotification(Context context) {
        Intent notificationIntent = new Intent(context, NotificationCancelReceiver.class);
        notificationIntent.setAction("notification_cancelled");
        PendingIntent deletePendingIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon_tarabih)
                        .setContentTitle(isBuildAboveThirteen() ? getBanglaSpannableString(context.getString(R.string.ramadan_app_alarm), context) : context.getString(R.string.ramadan_app_alarm_eng))
                        .setContentText(isBuildAboveThirteen() ? getBanglaSpannableString(context.getString(R.string.alarm_turn_off), context) : context.getString(R.string.alarm_turn_off_eng));
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mBuilder.setDeleteIntent(deletePendingIntent);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(deletePendingIntent);
        mNotifyMgr.notify((int) System.currentTimeMillis(), mBuilder.build());
    }

    public static android.text.SpannableString[] getBanglaSpannableStrings(String[] banglaRegionNames, Context context) {
        android.text.SpannableString[] banglaText = new android.text.SpannableString[banglaRegionNames.length];
        for (int counter = 0; counter < banglaRegionNames.length; counter++) {
            banglaText[counter] = getBanglaSpannableString(banglaRegionNames[counter], context);
        }
        return banglaText;
    }

    public static String replaceBanglaCharacter(String input) {
        char[] array = input.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stringBuilder.append(Character.toChars((int) array[i] + 2486));
        }
        return stringBuilder.toString();
    }

    public static boolean isBuildAboveThirteen() {
        return Build.VERSION.SDK_INT >= 14 ? true : false;
    }

    public static SpannableString getSpannableStringWithBanglaSupport(String text) {
        return new SpannableString(BengaliUnicodeString.getBengaliUTF(text));
    }


    public static boolean isBanglaAvailable() {
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            if (locale.getDisplayName().toLowerCase().contains("bengali")) {
                return true;
            }
        }
        return false;
    }

    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionName;
    }

    public static Bitmap getTypefaceBitmap(Context context, String text, float textSize, boolean bold) {
        int font_size_pixel = convertDipToPix(context, textSize);
        int padding = font_size_pixel / 9;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFakeBoldText(bold);
        paint.setTypeface(getFont(context));
        paint.setColor(Color.WHITE);
        paint.setTextSize(font_size_pixel);

        int textWidth = (int) (paint.measureText(text) + padding * 2);
        int height = (int) (font_size_pixel / 0.75);
        Bitmap bitmap = Bitmap.createBitmap(textWidth, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        float x_original = padding;
        canvas.drawText(text, x_original, font_size_pixel, paint);
        return bitmap;
    }

    public static int convertDipToPix(Context context, float dip) {
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return value;
    }
}
