package com.example.momenali.bakingapp.ui;

import android.content.IntentFilter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.momenali.bakingapp.R;
import com.example.momenali.bakingapp.Recipe;
import com.example.momenali.bakingapp.RecipeRecycleView;
import com.example.momenali.bakingapp.utils.NetworkUtils;
import com.example.momenali.bakingapp.utils.RecipeJSONUtils;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Momen Ali on 12/15/2017.
 */
public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<String> {
    private static final String TAG = "MainActivity";

    private static final int _LOADER_ID = 930;

    RecipeRecycleView mAdapter;
    RecyclerView recyclerView;
    Recipe[] recipes = new Recipe[]{new Recipe()};
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
                }  catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d(TAG, "onLoadFinished: "+data);
        if (data == null){
            Toast.makeText(this,this.getResources().getString(R.string.networkError),Toast.LENGTH_LONG).show();
            return;
        }
        try {
            recipes = RecipeJSONUtils.getRecipe(this , data);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mAdapter = new RecipeRecycleView( this  ,recipes);

        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        loader = null;
    }
}
