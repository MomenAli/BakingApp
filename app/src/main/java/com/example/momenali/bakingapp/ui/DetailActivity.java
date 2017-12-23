package com.example.momenali.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.momenali.bakingapp.R;
import com.example.momenali.bakingapp.step.Step;
import com.example.momenali.bakingapp.StepDetailsFragment;
import com.example.momenali.bakingapp.step.StepRecycleView;
import com.example.momenali.bakingapp.WidgetSyncUtils;
import com.example.momenali.bakingapp.utils.RecipeJSONUtils;

import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailsFragment.OnDetailsFragmentListener{

    private static final String TAG = "DetailActivity";
    Boolean tablet;
    int recipeID;
    String mJSONResult;

    private static final String STEP_DETAILS_VISIBLE_KEY = "visible";

    @BindView(R.id.step_details_container)FrameLayout stepDetailsContainer;
    @BindView(R.id.details_container)FrameLayout detailsContainer;

    Step mStep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        recipeID = extras.getInt(MainActivity.INTENT_ID_EXTRA_KEY,0);

        setTitle(extras.getString(MainActivity.INTENT_NAME_EXTRA_KEY));

        mJSONResult = extras.getString(MainActivity.INTENT_JSON_EXTRA_KEY);

        WidgetSyncUtils.startImmediateSync(this,recipeID,mJSONResult);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.land_linear_layout);
        if (linearLayout != null) tablet = true;
        else tablet = false;

        if (savedInstanceState != null)return;

        FragmentManager fragmentManager;
        if (tablet){
            DetailsFragment detailsFragment =  DetailsFragment.newInstance(recipeID, mJSONResult,0);


            fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.details_container, detailsFragment)
                    .commit();
            try {
                 mStep = RecipeJSONUtils.getStep(getBaseContext(),recipeID,0,mJSONResult);
            }  catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StepDetailsFragment stepDetailsFragment =  StepDetailsFragment.newInstance(mStep);

            fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.step_details_container, stepDetailsFragment)
                    .commit();


        }else{
            DetailsFragment detailsFragment =  DetailsFragment.newInstance(recipeID, mJSONResult,-1);


            fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.details_container, detailsFragment)
                    .commit();
        }

    }


    @Override
    public void onStepSelectListner(Step step) {
        mStep = step;
        if (tablet){
            StepDetailsFragment stepDetailsFragment =  StepDetailsFragment.newInstance(step);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_container, stepDetailsFragment)
                    .commit();


        }else {
            Intent intent = new Intent(this, StepDetialsActivity.class);
            Bundle extras = new Bundle();
            extras.putParcelable(StepRecycleView.INTENT_STEP_EXTRA_KEY, step);
            extras.putString(MainActivity.INTENT_JSON_EXTRA_KEY,mJSONResult);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

}
