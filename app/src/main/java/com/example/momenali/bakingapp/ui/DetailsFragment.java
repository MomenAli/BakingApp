package com.example.momenali.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.momenali.bakingapp.ingredient.Ingredient;
import com.example.momenali.bakingapp.ingredient.IngredientsRecycleView;
import com.example.momenali.bakingapp.R;
import com.example.momenali.bakingapp.step.Step;
import com.example.momenali.bakingapp.step.StepRecycleView;
import com.example.momenali.bakingapp.utils.RecipeJSONUtils;

import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment implements StepRecycleView.StepClickListener{
    private static final String TAG  = "DetailsFragment";

    @BindView(R.id.svDetailsFragment)
    ScrollView svMain;
    int stepPostion;

    IngredientsRecycleView mIngredAdapter;
    StepRecycleView mStepAdapter;
    OnDetailsFragmentListener mListener;

    String mJSONResult;


    private static final String SCROLL_POSTION_KEY = "scrollPostion";

    private static final String STEP_POSTION_KEY = "stepPostion";

    Ingredient[] ingredients = new Ingredient[]{new Ingredient()};
    Step[] steps = new Step[]{new Step()};

    int recipeID = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";



 //   private OnFragmentInteractionListener mListener;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     * @param json Parameter 1.
      * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(int id , String json , int current) {

        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, json);

        args.putInt(ARG_PARAM3, current);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeID = getArguments().getInt(ARG_PARAM1);
            mJSONResult = getArguments().getString(ARG_PARAM2);
            stepPostion = getArguments().getInt(ARG_PARAM3);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_details,container,false);
        Log.d(TAG, "onCreateView: "+recipeID);
        /* get the Ingredients */
        try {
            ingredients = RecipeJSONUtils.getIngredients(viewRoot.getContext(), recipeID,mJSONResult);
            steps = RecipeJSONUtils.getSteps(viewRoot.getContext(),recipeID,mJSONResult);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ButterKnife.bind(this,viewRoot);
        // set up the ingredients RecyclerView
        RecyclerView ingredientRecyclerView = (RecyclerView) viewRoot.findViewById(R.id.rvIngredients);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(ingredientRecyclerView.getContext()));
        mIngredAdapter = new IngredientsRecycleView( viewRoot.getContext()  ,ingredients);
        ingredientRecyclerView.setAdapter(mIngredAdapter);


        // set up the steps RecyclerView
        RecyclerView stepRecyclerView = (RecyclerView) viewRoot.findViewById(R.id.rvSteps);
        stepRecyclerView.setLayoutManager(new LinearLayoutManager(stepRecyclerView.getContext()));
        mStepAdapter = new StepRecycleView( viewRoot.getContext()  , steps);
        mStepAdapter.setmClickListener(this);
        stepRecyclerView.setAdapter(mStepAdapter);
        mStepAdapter.setSelectedPostion(stepPostion);

        if (savedInstanceState != null){
            mStepAdapter.setSelectedPostion(savedInstanceState.getInt(STEP_POSTION_KEY));
            svMain.scrollTo(0,savedInstanceState.getInt(SCROLL_POSTION_KEY));
        }

        return viewRoot;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SCROLL_POSTION_KEY,svMain.getScrollY());
        outState.putInt(STEP_POSTION_KEY,mStepAdapter.getSelectedPostion());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailsFragmentListener) {
            mListener = (OnDetailsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStepItemClick(View view, int position) {
        Log.d(TAG, "onStepItemClick: ");
        mListener.onStepSelectListner(steps[position]);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDetailsFragmentListener {
        // TODO: Update argument type and name
        void onStepSelectListner(Step step);
    }
}
