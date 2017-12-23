package com.example.momenali.bakingapp;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.momenali.bakingapp.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {
    public static final String TAG = "IngredientsWidget";
    static int recipeID;
    static String mJSONResult;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);
        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, GridWidgetService.class);
        Bundle extras = new Bundle();
        /*mJSONResult = extras.getString(MainActivity.INTENT_JSON_EXTRA_KEY);
        recipeID = extras.getInt(MainActivity.INTENT_ID_EXTRA_KEY);*/
        extras.putString(MainActivity.INTENT_JSON_EXTRA_KEY,mJSONResult);
        extras.putInt(MainActivity.INTENT_ID_EXTRA_KEY,recipeID);
        intent.putExtras(extras);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);
        Log.d(TAG, "updateAppWidget: ");
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager, String json , int recipeId,
                                           int[] appWidgetIds) {
        mJSONResult = json;
        recipeID = recipeId;
        Log.d(TAG, "updateIngredientWidgets: " );
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager,appWidgetId);
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

