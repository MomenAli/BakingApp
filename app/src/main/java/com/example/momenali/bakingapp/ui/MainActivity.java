package com.example.momenali.bakingapp.ui;

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
import com.example.momenali.bakingapp.utils.RecipeJSONUtils;

import org.json.JSONException;
/**
 * Created by Momen Ali on 12/15/2017.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    RecipeRecycleView mAdapter;
    Recipe[] recipes = new Recipe[]{new Recipe()};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // data to populate the RecyclerView with

        try {
            recipes = RecipeJSONUtils.getRecipe(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvMain);
        int numberOfColumns = getResources().getInteger(R.integer.column_number);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        mAdapter = new RecipeRecycleView( this  ,recipes);
       // mAdapter.setmClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }


}
