package com.example.momenali.bakingapp.recipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.momenali.bakingapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Momen Ali on 12/14/2017.
 */

public class RecipeRecycleView extends RecyclerView.Adapter<RecipeRecycleView.ViewHolder> {
    private static final String TAG = "RecipeRecycleView";


    onRecipeClickListener mClickListener;


    private Recipe[] mRecipe = new Recipe[0];
    private LayoutInflater mInflater;
    Context mContext;

    public RecipeRecycleView(Context context, Recipe[] mRecipe) {
        this.mRecipe = mRecipe;
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.tvRecipeTitle.setText(mRecipe[position].getName());
        holder.tvServings.setText(mRecipe[position].getServings());
        if (!mRecipe[position].getImageURL().equals("")) {
            Picasso.with(mContext)
                    .load(mRecipe[position].getImageURL())
                    .placeholder(R.drawable.placeholder_food)
                    .error(R.drawable.placeholder_food)
                    .into(holder.ivRecipeImage);
        }
    }

    @Override
    public int getItemCount() {
        return mRecipe.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imageView)
        ImageView ivRecipeImage;
        @BindView(R.id.recipeTitle)
        TextView tvRecipeTitle;
        @BindView(R.id.tvServingNumber)
        TextView tvServings;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: iye" + mRecipe[getAdapterPosition()].toString());

            mClickListener.onRecipeClickListener(mRecipe[getAdapterPosition()]);
        }
    }

    public void setmClickListener(onRecipeClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface onRecipeClickListener {
        void onRecipeClickListener(Recipe recipe);
    }


}
