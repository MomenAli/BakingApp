package com.example.momenali.bakingapp.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.momenali.bakingapp.R;
import com.example.momenali.bakingapp.ingredient.Ingredient;
import com.example.momenali.bakingapp.step.Step;
import com.example.momenali.bakingapp.StepDetailsFragment;
import com.example.momenali.bakingapp.step.StepRecycleView;
import com.example.momenali.bakingapp.WidgetSyncUtils;
import com.example.momenali.bakingapp.utils.RecipeJSONUtils;

import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailsFragment.OnDetailsFragmentListener {

    public static final String TAG = "DetailActivity";
    Boolean tablet;
    int recipeID;
    String mJSONResult;
    int currentStep = -1;
    private static final int REQEST_CODE = 990;

    private static final String STEP_DETAILS_VISIBLE_KEY = "visible";

    @Nullable
    @BindView(R.id.step_details_container)
    FrameLayout stepDetailsContainer;
    @BindView(R.id.details_container)
    FrameLayout detailsContainer;

    Step mStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        recipeID = extras.getInt(MainActivity.INTENT_ID_EXTRA_KEY, 0);

        setTitle(extras.getString(MainActivity.INTENT_NAME_EXTRA_KEY));

        mJSONResult = extras.getString(MainActivity.INTENT_JSON_EXTRA_KEY);

        WidgetSyncUtils.startImmediateSync(this, recipeID, mJSONResult);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.land_linear_layout);
        if (linearLayout != null) tablet = true;
        else tablet = false;

        if (savedInstanceState != null) return;

        FragmentManager fragmentManager;
        if (tablet) {
            currentStep = 0;
            DetailsFragment detailsFragment = DetailsFragment.newInstance(recipeID, mJSONResult, 0);


            fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.details_container, detailsFragment)
                    .commit();
            try {
                mStep = RecipeJSONUtils.getStep(getBaseContext(), recipeID, 0, mJSONResult);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(mStep);

            fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.step_details_container, stepDetailsFragment)
                    .commit();


        } else {
            DetailsFragment detailsFragment = DetailsFragment.newInstance(recipeID, mJSONResult, -1);


            fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.details_container, detailsFragment)
                    .commit();
        }

    }

    @Override
    protected void onResume() {

        DetailsFragment detailsFragment = DetailsFragment.newInstance(recipeID, mJSONResult, currentStep );


        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.details_container, detailsFragment)
                .commit();
        Log.d(TAG, "onResume: " + currentStep);
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: ");
        if (resultCode == RESULT_OK && requestCode == REQEST_CODE) {
            Log.d(TAG, "onActivityResult: in");
            if (data.hasExtra(StepDetialsActivity.STEP_NUMBER_TO_PERANTE)) {

                currentStep = Integer.parseInt(data.getExtras().getString(StepDetialsActivity.STEP_NUMBER_TO_PERANTE));
                Log.d(TAG, "onActivityResult: in" + currentStep);
                /*DetailsFragment detailsFragment = DetailsFragment.newInstance(recipeID, mJSONResult,  );


                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.details_container, detailsFragment)
                        .commit();*/
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void onStepSelectListner(Step step) {
        mStep = step;
        if (tablet) {
            StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(step);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_container, stepDetailsFragment)
                    .commit();


        } else {
            Intent intent = new Intent(this, StepDetialsActivity.class);
            Bundle extras = new Bundle();
            extras.putParcelable(StepRecycleView.INTENT_STEP_EXTRA_KEY, step);
            extras.putString(MainActivity.INTENT_JSON_EXTRA_KEY, mJSONResult);
            intent.putExtras(extras);
            startActivityForResult(intent, REQEST_CODE);
        }
    }

}
