package com.example.momenali.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.momenali.bakingapp.ui.MainActivity;

/**
 * Created by Momen Ali on 12/21/2017.
 */

public class IngredientWidgetService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    private static final String TAG = "IngredientWidgetService";
    public IngredientWidgetService() {
        super("IngredientWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        //Trigger data update to handle the GridView widgets and force a data refresh
        Bundle extas = intent.getExtras();

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidget.class));
        Log.d(IngredientsWidget.TAG, "onHandleIntent: " + appWidgetIds[0]);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        //Now update all widgets

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);

        IngredientsWidget.updateIngredientWidgets(this,
                appWidgetManager,extas.getString(MainActivity.INTENT_JSON_EXTRA_KEY),
                extas.getInt(MainActivity.INTENT_ID_EXTRA_KEY),appWidgetIds);

    }
}
