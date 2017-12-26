package com.example.momenali.bakingapp.ui;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.momenali.bakingapp.R;
import com.example.momenali.bakingapp.step.Step;
import com.example.momenali.bakingapp.StepDetailsFragment;
import com.example.momenali.bakingapp.step.StepRecycleView;
import com.example.momenali.bakingapp.utils.RecipeJSONUtils;

import org.json.JSONException;

import java.io.IOException;

public class StepDetialsActivity extends AppCompatActivity {
    private static final String TAG = "StepDetialsActivity";
    public static final String STEP_NUMBER_TO_PERANTE = "data";

    Step mStep;
    String mJSONResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detials);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        mStep = extras.getParcelable(StepRecycleView.INTENT_STEP_EXTRA_KEY);
        mJSONResult = extras.getString(MainActivity.INTENT_JSON_EXTRA_KEY);
        Log.d(TAG, "onCreate: " + mStep.getShortDescription());
        if (savedInstanceState != null) return;
        StepDetailsFragment fragment = StepDetailsFragment.newInstance(mStep);


        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.step_details_container, fragment)
                .commit();


    }


    public void getPreviousStep(View view) {

        if (mStep.getId().equals("0")) {
            Toast.makeText(this, getString(R.string.first_step_notification), Toast.LENGTH_LONG).show();
        }

        try {
            mStep = RecipeJSONUtils.getStep(getBaseContext(), mStep.getRecipeID(), Integer.parseInt(mStep.getId()) - 1, mJSONResult);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        StepDetailsFragment fragment = StepDetailsFragment.newInstance(mStep);


        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.step_details_container, fragment)
                .commit();
    }

    public void getNextStep(View view) {
        Step oldStep = mStep;
        try {
            mStep = RecipeJSONUtils.getStep(getBaseContext(), mStep.getRecipeID(), Integer.parseInt(mStep.getId()) + 1, mJSONResult);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mStep == null) {
            Toast.makeText(getBaseContext(), getBaseContext().getResources().getString(R.string.last_step_notification), Toast.LENGTH_LONG).show();
            mStep = oldStep;
            return;
        }

        StepDetailsFragment fragment = StepDetailsFragment.newInstance(mStep);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.step_details_container, fragment)
                .commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            Intent data = new Intent();
            data.putExtra(STEP_NUMBER_TO_PERANTE, mStep.getId());
            setResult(RESULT_OK, data);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        Intent data = new Intent();
        data.putExtra(STEP_NUMBER_TO_PERANTE, mStep.getId());
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(DetailActivity.TAG, "onDestroy: ");
        Intent data = new Intent();
        data.putExtra(STEP_NUMBER_TO_PERANTE, mStep.getId());
        setResult(RESULT_OK, data);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
