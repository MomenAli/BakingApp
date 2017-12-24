package com.example.momenali.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.momenali.bakingapp.ingredient.Ingredient;
import com.example.momenali.bakingapp.ui.MainActivity;
import com.example.momenali.bakingapp.utils.RecipeJSONUtils;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Momen Ali on 12/21/2017.
 */

public class GridWidgetService extends RemoteViewsService {
    private static final String TAG = "IngredientsWidget";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        try {
            Log.d(TAG, "onGetViewFactory: ");
            return new GridRemoteViewsFactory(this.getApplicationContext(), intent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}


class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = "IngredientsWidget";

    Context mContext;
    String mJSONResult;
    Ingredient[] ingredients;
    int recipeID;

    public GridRemoteViewsFactory(Context context, Intent intent) throws IOException, JSONException {
        mContext = context;
        Bundle extras = intent.getExtras();
        mJSONResult = extras.getString(MainActivity.INTENT_JSON_EXTRA_KEY);
        recipeID = extras.getInt(MainActivity.INTENT_ID_EXTRA_KEY);
        Log.d(TAG, "GridRemoteViewsFactory: " + recipeID);
        if (mJSONResult != null)
            ingredients = RecipeJSONUtils.getIngredients(mContext, recipeID, mJSONResult);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mJSONResult = IngredientsWidget.mJSONResult;
        recipeID = IngredientsWidget.recipeID;
        try {
            Log.d(TAG, "onDataSetChanged: " + recipeID);
            if (mJSONResult != null)
                ingredients = RecipeJSONUtils.getIngredients(mContext, recipeID, mJSONResult);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredients == null) return 0;
        return ingredients.length;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingred_item);
        views.setTextViewText(R.id.tvIngredName, ingredients[position].getIngredientName());
        views.setTextViewText(R.id.tvIngredQuantity, String.valueOf(ingredients[position].getQuantity()));
        views.setTextViewText(R.id.tvIngredUnit, ingredients[position].getMeasure().toString());
        Log.d(TAG, "getViewAt: " + ingredients[position].getIngredientName());
       /* holder.ingredientName.setText(mIngredient[position].getIngredientName());
        holder.IngredQuantity.setText(String.valueOf(mIngredient[position].getQuantity()));
        holder.IngredUnit.setText(mIngredient[position].getMeasure().toString());*/
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}