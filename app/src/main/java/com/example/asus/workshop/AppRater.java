package com.example.asus.workshop;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

public class AppRater
{
    private final static String APP_PACAKEG_NAME = "com.example.appraterexample";

    //Initialized to 0 and 6 only for test purposes. In real app change this
    private final static int DAYS_UNTIL_PROMPT = 0;
    private final static int LAUNCH_UNTIL_PROMPT = 3;

    public static void app_launched(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("rate_app", 0);
        if (preferences.getBoolean("dontshowagain", false))
        {
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();
        long launch_count = preferences.getLong("launch_count",0) + 1;
        editor.putLong("launch_count",launch_count);

        Long date_firstLaunch = preferences.getLong("date_first_launch",0);
        if (date_firstLaunch == 0)
        {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_first_launch",date_firstLaunch);
        }

        if (launch_count >= LAUNCH_UNTIL_PROMPT )
        {
            if (System.currentTimeMillis() >= date_firstLaunch + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000))
            {
                showRateDialog(context,editor);
            }
        }
        editor.commit();
    }

    public static void showRateDialog(final Context context, final SharedPreferences.Editor editor)
    {
        Dialog dialog = new Dialog(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String message ="Thank You For Your Support!";

        builder.setMessage(message)
                .setTitle("Rate Us")
                .setIcon(context.getApplicationInfo().icon)
                .setCancelable(false)
                .setPositiveButton("Rate Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putBoolean("dontshowagain",true);
                        editor.commit();

                        //If your app has not been uploaded to market you'll get an exception.
                        //For test purpose we catch it here and show some text.
                        try {
                            context.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.easyalert.lenovo.timetable1")));

                        }catch (ActivityNotFoundException e) {
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No, Thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editor !=null)
                        {
                            editor.putBoolean("dontshowagain", true);
                            editor.commit();
                        }
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
        dialog.show();
    }
}
