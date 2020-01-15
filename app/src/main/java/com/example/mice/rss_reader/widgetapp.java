package com.example.mice.rss_reader;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;


import com.example.mice.rss_reader.modal.Docbao;

import java.util.ArrayList;

import io.paperdb.Paper;

/**
 * Implementation of App Widget functionality.
 */
public class widgetapp extends AppWidgetProvider {
//     listview;



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        Paper.init(context);
        ArrayList<Docbao> lisnews;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widgetapp);

//        listview.
        // Instruct the widget manager to update the widget



    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

