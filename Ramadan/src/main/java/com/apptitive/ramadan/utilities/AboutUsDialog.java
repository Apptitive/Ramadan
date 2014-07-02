package com.apptitive.ramadan.utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutUsDialog {
    private static final String TAG = "ChangeLogDialog";
    private final Context mContext;
    private String versionName;
    private String mStyle = "h1 { margin-left: 0px; font-size: 12pt; }"
            + "li { margin-left: 0px; font-size: 9pt; }"
            + "ul { padding-left: 30px; }"
            + "p {font-style:italic; margin-left: 8px; font-size: 9pt;}"
            + ".summary { font-size: 9pt; color: #606060; display: block; clear: left; }"
            + ".date { font-size: 9pt; color: #606060;  display: block; }";

    protected DialogInterface.OnDismissListener mOnDismissListener;

    public AboutUsDialog(final Context context) {
        mContext = context;
        try {
            versionName = Utilities.getVersionName(mContext);
        } catch (PackageManager.NameNotFoundException e) {
            versionName = "1.0";
            e.printStackTrace();
        }
    }

    protected Context getContext() {
        return mContext;
    }

    private String getStyle() {
        return String.format("<style type=\"text/css\">%s</style>", mStyle);
    }

    public void setStyle(final String style) {
        mStyle = style;
    }

    public AboutUsDialog setOnDismissListener(
            final DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
        return this;
    }

    private String getHTMLChangelog() {
        final StringBuilder changelogBuilder = new StringBuilder();
        changelogBuilder.append("<html><head>").append(getStyle())
                .append("</head><body>");
        changelogBuilder.append("<h1>Developed by</h1>");
        changelogBuilder.append("<p>Apptitive</p>");
        changelogBuilder.append("<a href=\"activity_a://a\">Rate this app</a>");
        changelogBuilder.append("</body></html>");
        return changelogBuilder.toString();
    }

    public void show() {
        show(0);
    }

    protected void show(final int version) {
        final String htmlChangelog = getHTMLChangelog();
        final String closeString = "Done";
        if (htmlChangelog.length() == 0) {
            return;
        }

        final WebView webView = new WebView(mContext);
        webView.loadDataWithBaseURL(null, htmlChangelog, "text/html", "utf-8",
                null);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equalsIgnoreCase("activity_a://a")) {
                    final String my_package_name = mContext.getPackageName();
                    String packurl = "";

                    try {
                        mContext.getPackageManager().getPackageInfo(
                                "com.android.vending", 0);
                        packurl = "market://details?id=" + my_package_name;
                    } catch (final Exception e) {
                        packurl = "https://play.google.com/store/apps/details?id="
                                + my_package_name;
                    }
                    final Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                            .parse(packurl));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    mContext.startActivity(intent);
                }
                return true;
            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle("Ramadan V" + versionName).setView(webView)
                .setPositiveButton(closeString, new Dialog.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                    }
                }).setOnCancelListener(new OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(final DialogInterface dialog) {
                if (mOnDismissListener != null) {
                    mOnDismissListener.onDismiss(dialog);
                }
            }
        });
        dialog.show();
    }

}