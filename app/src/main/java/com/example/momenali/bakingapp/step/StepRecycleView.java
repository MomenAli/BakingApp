package com.example.momenali.bakingapp.step;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.momenali.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Momen Ali on 12/15/2017.
 */

public class StepRecycleView extends RecyclerView.Adapter<StepRecycleView.ViewHolder> {
    private static final String TAG = "StepRecycleView";
    int selectedPostion = -1;

    StepClickListener mClickListener;

    public static final String INTENT_RECIPE_ID_EXTRA_KEY  ="recipeID";
    public static final String INTENT_STEP_ID_EXTRA_KEY  ="stepID";
    public static final String INTENT_STEP_EXTRA_KEY  ="step";

    private Step[] mStep = new Step[0];
    private LayoutInflater mInflater;
    Context mContext;

    public StepRecycleView(Context context, Step[] mStep) {
        this.mStep = mStep;
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = mInflater.inflate(R.layout.step_item,parent,false);
        return new ViewHolder(view);
    }

    public int getSelectedPostion() {
        return selectedPostion;
    }

    public void setSelectedPostion(int selectedPostion) {
        this.selectedPostion = selectedPostion;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+mStep.length+"  "+ position);

        holder.tvStepNumber.setText(String.valueOf(position+1));
        holder.tvShortDescription.setText(mStep[position].shortDescription);
        Log.d(TAG, "onBindViewHolder: " +position + " selected " + selectedPostion);
        if (position == selectedPostion){
            holder.tvStepNumber.setSelected(true);
            holder.tvStepNumber.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.step_background.setSelected(true);
            holder.tvShortDescription.setTextColor(mContext.getResources().getColor(R.color.white));
        }else{
            holder.tvStepNumber.setSelected(false);
            holder.tvStepNumber.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.step_background.setSelected(false);
            holder.tvShortDescription.setTextColor(mContext.getResources().getColor(R.color.black));
        }

    }

    @Override
    public int getItemCount() {
        return mStep.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tvStepNumber) TextView tvStepNumber;
        @BindView(R.id.tvStepShortDescription) TextView tvShortDescription;
        @BindView(R.id.step_background)
        LinearLayout step_background;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: ");
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: iye "+ selectedPostion );
            int oldItem = selectedPostion;
            selectedPostion = getAdapterPosition();
            Log.d(TAG, "after onClick: iye "+ selectedPostion );
            mClickListener.onStepItemClick(v,getAdapterPosition());
            notifyItemChanged(oldItem);
            notifyItemChanged(selectedPostion);
        }
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
    // allows clicks events to be caught
    public void setmClickListener(StepClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface StepClickListener {
        void onStepItemClick(View view, int position);
    }
}
