package com.example.momenali.bakingapp.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.example.momenali.bakingapp.Ingredient;
import com.example.momenali.bakingapp.Recipe;
import com.example.momenali.bakingapp.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import javax.sql.DataSource;

/**
 * Created by Momen Ali on 12/14/2017.
 */

public class RecipeJSONUtils {
    private static final String TAG = "RecipeJSONUtils";

    public static Recipe[] getRecipe(Context context) throws JSONException {
        //definre the parameter of the page


        final String OWM_ID = "id";
        final String OWM_TITLE = "name";
        final String OWM_SERVING = "servings";
        final String OWM_IMAGE = "image";

        String JsonStr = loadJSONFromAsset(context);
        Log.d(TAG, "getRecipe: " + JsonStr);
        JSONArray recipeJsonArray = new JSONArray(JsonStr);
        Log.d(TAG, "getRecipe: " + recipeJsonArray.length());
        Recipe[] recipes = new Recipe[recipeJsonArray.length()];

        for (int i = 0; i < recipeJsonArray.length(); i++) {
            JSONObject recipeJSONItem = recipeJsonArray.getJSONObject(i);
            Log.d(TAG, "getRecipe: " + recipeJSONItem.getString(OWM_ID));
            recipes[i] = new Recipe();
            recipes[i].setId(Integer.parseInt(recipeJSONItem.getString(OWM_ID)));
            recipes[i].setName(recipeJSONItem.getString(OWM_TITLE));
            recipes[i].setServings(recipeJSONItem.getString(OWM_SERVING));
            recipes[i].setImageURL(recipeJSONItem.getString(OWM_IMAGE));
        }
        return recipes;
    }

    public static Ingredient[] getIngredients(Context context, int id) throws JSONException {
        //definre the parameter of the page


        final String OWM_ID = "id";
        final String OWM_INGREDIENTS = "ingredients";
        final String OWM_QUANTITY = "quantity";
        final String OWM_MEASURE = "measure";
        final String OWM_INGRED_NAME = "ingredient";

        String JsonStr = loadJSONFromAsset(context);
        Log.d(TAG, "getIngredient: " + JsonStr);
        JSONArray recipeJsonArray = new JSONArray(JsonStr);
        Log.d(TAG, "getIngredient: " + recipeJsonArray.length());
        Ingredient[] ingredients = new Ingredient[0];

        for (int i = 0; i < recipeJsonArray.length(); i++) {
            JSONObject recipeJSONItem = recipeJsonArray.getJSONObject(i);
            if (recipeJSONItem.getInt(OWM_ID) == id) {
                JSONArray ingredjSONArray = recipeJSONItem.getJSONArray(OWM_INGREDIENTS);
                ingredients = new Ingredient[ingredjSONArray.length()];
                for (int j = 0; j < ingredjSONArray.length(); j++) {
                    JSONObject ingredJSONItem = ingredjSONArray.getJSONObject(j);
                    Log.d(TAG, "getIngredient: " + ingredJSONItem.getString(OWM_INGRED_NAME));

                    ingredients[j] = new Ingredient();
                    Log.d(TAG, "getIngredients: " + ingredJSONItem.getString(OWM_INGRED_NAME));
                    ingredients[j].setIngredientName(ingredJSONItem.getString(OWM_INGRED_NAME));
                    ingredients[j].setMeasure(Ingredient.Measure.valueOf(ingredJSONItem.getString(OWM_MEASURE)));
                    ingredients[j].setQuantity(ingredJSONItem.getDouble(OWM_QUANTITY));

                }
            }
        }
        return ingredients;
    }

    public static Step[] getSteps(Context context, int id) throws JSONException {
        //definre the parameter of the page


        final String OWM_ID = "id";
        final String OWM_STEPS = "steps";
        final String OWM_SHORT_DESCRIPTION = "shortDescription";
        final String OWM_DESCRPTION = "description";
        final String OWM_VIDEO_URL = "videoURL";
        final String OWM_THUMBNAIL_URL = "thumbnailURL";

        String JsonStr = loadJSONFromAsset(context);
        Log.d(TAG, "getIngredient: " + JsonStr);
        JSONArray recipeJsonArray = new JSONArray(JsonStr);
        Log.d(TAG, "getIngredient: " + recipeJsonArray.length());
        Step[] steps = new Step[0];

        for (int i = 0; i < recipeJsonArray.length(); i++) {
            JSONObject recipeJSONItem = recipeJsonArray.getJSONObject(i);
            if (recipeJSONItem.getInt(OWM_ID) == id) {
                JSONArray stepjSONArray = recipeJSONItem.getJSONArray(OWM_STEPS);
                steps = new Step[stepjSONArray.length()];
                for (int j = 0; j < stepjSONArray.length(); j++) {
                    JSONObject stepJSONItem = stepjSONArray.getJSONObject(j);
                    Log.d(TAG, "getIngredient: " + stepJSONItem.getString(OWM_SHORT_DESCRIPTION));

                    steps[j] = new Step();
                    Log.d(TAG, "getIngredients: " + stepJSONItem.getString(OWM_SHORT_DESCRIPTION));

                    steps[j].setId(stepJSONItem.getString(OWM_ID));
                    steps[j].setShortDescription(stepJSONItem.getString(OWM_SHORT_DESCRIPTION));
                    steps[j].setDescription(stepJSONItem.getString(OWM_DESCRPTION));
                    steps[j].setVideoURL(stepJSONItem.getString(OWM_VIDEO_URL));
                    steps[j].setThumbnailURL(stepJSONItem.getString(OWM_THUMBNAIL_URL));
                    steps[j].setRecipeID(id);
                }
            }
        }
        return steps;
    }

    public static Step getStep(Context context, int recipeID, int stepID) throws JSONException {
        //definre the parameter of the page


        final String OWM_ID = "id";
        final String OWM_STEPS = "steps";
        final String OWM_SHORT_DESCRIPTION = "shortDescription";
        final String OWM_DESCRPTION = "description";
        final String OWM_VIDEO_URL = "videoURL";
        final String OWM_THUMBNAIL_URL = "thumbnailURL";

        String JsonStr = loadJSONFromAsset(context);
        Log.d(TAG, "getIngredient: " + JsonStr);
        JSONArray recipeJsonArray = new JSONArray(JsonStr);
        Log.d(TAG, "getIngredient: " + recipeJsonArray.length());
        Step step = new Step();

        for (int i = 0; i < recipeJsonArray.length(); i++) {
            JSONObject recipeJSONItem = recipeJsonArray.getJSONObject(i);
            if (recipeJSONItem.getInt(OWM_ID) == recipeID) {
                JSONArray stepjSONArray = recipeJSONItem.getJSONArray(OWM_STEPS);
                if (stepID >= stepjSONArray.length())return  null;
                JSONObject stepJSONItem = stepjSONArray.getJSONObject(stepID);
                Log.d(TAG, "getIngredient: " + stepJSONItem.getString(OWM_SHORT_DESCRIPTION));

                Log.d(TAG, "getIngredients: " + stepJSONItem.getString(OWM_SHORT_DESCRIPTION));

                step.setId(stepJSONItem.getString(OWM_ID));
                step.setShortDescription(stepJSONItem.getString(OWM_SHORT_DESCRIPTION));
                step.setDescription(stepJSONItem.getString(OWM_DESCRPTION));
                step.setVideoURL(stepJSONItem.getString(OWM_VIDEO_URL));
                step.setThumbnailURL(stepJSONItem.getString(OWM_THUMBNAIL_URL));
                step.setRecipeID(recipeID);

            }
        }
        return step;
    }

    /* this function taken from stackoverFlow as reader fuction for the json file */
    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("recipesList.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
