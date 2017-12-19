package com.example.momenali.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Momen Ali on 12/15/2017.
 */

public class IngredientsRecycleView extends RecyclerView.Adapter<IngredientsRecycleView.ViewHolder> {
    private static final String TAG = "IngredientsRecycleView";

    private Ingredient[] mIngredient = new Ingredient[0];
    private LayoutInflater mInflater;

    public IngredientsRecycleView(Context context, Ingredient[] mIngredient) {
        this.mIngredient = mIngredient;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = mInflater.inflate(R.layout.ingred_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+mIngredient.length+"  "+ position);

        holder.ingredientName.setText(mIngredient[position].getIngredientName());
        holder.IngredQuantity.setText(String.valueOf(mIngredient[position].getQuantity()));
        holder.IngredUnit.setText(mIngredient[position].getMeasure().toString());
    }

    @Override
    public int getItemCount() {
        return mIngredient.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tvIngredName) TextView ingredientName;
        @BindView(R.id.tvIngredQuantity) TextView IngredQuantity;
        @BindView(R.id.tvIngredUnit) TextView IngredUnit;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: ");
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: iye" );
        }
    }
}
