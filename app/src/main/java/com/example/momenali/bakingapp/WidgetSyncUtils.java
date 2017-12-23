package com.example.momenali.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.momenali.bakingapp.ui.MainActivity;

/**
 * Created by Momen Ali on 12/22/2017.
 */

public class WidgetSyncUtils {
    public static void startImmediateSync(Context context , int recipeId , String mJSONResult){
        Bundle extras = new Bundle();
        extras.putString(MainActivity.INTENT_JSON_EXTRA_KEY,mJSONResult);
        extras.putInt(MainActivity.INTENT_ID_EXTRA_KEY,recipeId);
        Intent servIntent = new Intent(context, IngredientWidgetService.class);
        servIntent.putExtras(extras);
        Log.d(IngredientsWidget.TAG, "onCreate: service begin" );
        context.startService(servIntent);
    }
}
