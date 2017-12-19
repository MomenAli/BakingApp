package com.example.momenali.bakingapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.momenali.bakingapp.R;
import com.example.momenali.bakingapp.RecipeRecycleView;
import com.example.momenali.bakingapp.Step;
import com.example.momenali.bakingapp.StepDetailsFragment;
import com.example.momenali.bakingapp.StepRecycleView;
import com.example.momenali.bakingapp.ui.DetailsFragment;
import com.example.momenali.bakingapp.utils.RecipeJSONUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity implements DetailsFragment.OnDetailsFragmentListener{

    private static final String TAG = "DetailActivity";
    Boolean tabletLand;
    int recipeID;

    Step mStep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        recipeID = extras.getInt(RecipeRecycleView.INTENT_ID_EXTRA_KEY,0);

        setTitle(extras.getString(RecipeRecycleView.INTENT_NAME_EXTRA_KEY));

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.land_linear_layout);
        if (linearLayout != null)tabletLand = true;
        else tabletLand = false;


        Log.d(TAG, "onCreate: " + (savedInstanceState == null));
        if (savedInstanceState != null)return;

        DetailsFragment detailsFragment =  DetailsFragment.newInstance(recipeID);


        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.details_container, detailsFragment)
                .commit();
        if (tabletLand){

            try {
                 mStep = RecipeJSONUtils.getStep(getBaseContext(),recipeID,0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StepDetailsFragment stepDetailsFragment =  StepDetailsFragment.newInstance(mStep);

            fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.step_details_container, stepDetailsFragment)
                    .commit();

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

    }

    @Override
    public void onStepSelectListner(Step step) {
        mStep = step;
        if (tabletLand){
            StepDetailsFragment stepDetailsFragment =  StepDetailsFragment.newInstance(step);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_container, stepDetailsFragment)
                    .commit();
        }else {
            Intent intent = new Intent(this, StepDetialsActivity.class);
            Bundle extras = new Bundle();
            extras.putParcelable(StepRecycleView.INTENT_STEP_EXTRA_KEY, step);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

}
