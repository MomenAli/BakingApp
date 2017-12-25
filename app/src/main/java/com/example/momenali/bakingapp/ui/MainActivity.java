package com.example.momenali.bakingapp.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.momenali.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.momenali.bakingapp.R;
import com.example.momenali.bakingapp.recipe.Recipe;
import com.example.momenali.bakingapp.recipe.RecipeRecycleView;
import com.example.momenali.bakingapp.utils.NetworkUtils;
import com.example.momenali.bakingapp.utils.RecipeJSONUtils;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Momen Ali on 12/15/2017.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, RecipeRecycleView.onRecipeClickListener {
    private static final String TAG = "MainActivity";


    public static final String INTENT_ID_EXTRA_KEY = "id";
    public static final String INTENT_NAME_EXTRA_KEY = "name";
    public static final String INTENT_JSON_EXTRA_KEY = "json";

    private static final int _LOADER_ID = 930;
    String mJSONResult;
    RecipeRecycleView mAdapter;
    RecyclerView recyclerView;
    Recipe[] recipes = new Recipe[]{new Recipe()};


    @Nullable
    private SimpleIdlingResource mIdlingResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // data to populate the RecyclerView with


        // set up the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.rvMain);
        int numberOfColumns = getResources().getInteger(R.integer.column_number);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> SearchLoader = loaderManager.getLoader(_LOADER_ID);
        if (SearchLoader == null) {
            loaderManager.initLoader(_LOADER_ID, null, this);
        } else {
            loaderManager.restartLoader(_LOADER_ID, null, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh){
            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<String> SearchLoader = loaderManager.getLoader(_LOADER_ID);
            if (SearchLoader == null) {
                loaderManager.initLoader(_LOADER_ID, null, this);
            } else {
                loaderManager.restartLoader(_LOADER_ID, null, this);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {

        Log.d(TAG, "onCreateLoader: ");
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                try {
                    return NetworkUtils.getResponseFromHttpUrl();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d(TAG, "onLoadFinished: " + data);
        if (data == null) {
            Toast.makeText(this, this.getResources().getString(R.string.networkError), Toast.LENGTH_LONG).show();
            return;
          /*  try {
                data = RecipeJSONUtils.loadJSONFromAsset(this.getBaseContext());
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        mJSONResult = data;
        try {
            recipes = RecipeJSONUtils.getRecipe(this, data);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onLoadFinished: " + recipes[0].toString());
        mAdapter = new RecipeRecycleView(this, recipes);
        mAdapter.setmClickListener(this);
        recyclerView.setAdapter(mAdapter);

        if (mIdlingResource !=null)
            mIdlingResource.setIdleState(true);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        loader = null;
    }

    @Override
    public void onRecipeClickListener(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle extras = new Bundle();
        extras.putString(INTENT_NAME_EXTRA_KEY, recipe.getName());
        extras.putInt(INTENT_ID_EXTRA_KEY, recipe.getId());
        extras.putString(INTENT_JSON_EXTRA_KEY, mJSONResult);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        mIdlingResource.setIdleState(false);
        return mIdlingResource;
    }
}
