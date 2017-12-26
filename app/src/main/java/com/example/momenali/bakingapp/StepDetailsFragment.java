package com.example.momenali.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.momenali.bakingapp.step.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepDetailsFragment extends Fragment {
    private static final String TAG = "StepDetailsFragment";

    /* const for get data when make newinstance */
    private static final String ARG_PARAM1 = "param1";

    /* const used to save current postion in bundle */
    private static final String VIDEO_POSTION_KEY = "videoPostion";


    boolean tabletLandSingle;
    boolean landScape = false;


    private SimpleExoPlayer mExoPlayer;
    long currentVideoPostion;
    @BindView(R.id.player_View)
    SimpleExoPlayerView mPlayerView;

    @Nullable
    @BindView(R.id.ivThumbnial)
    ImageView ivThumbnial;

    @Nullable
    @BindView(R.id.tvStepDescription)
    TextView tvStepDescription;


    private Step mStep;

    private OnFragmentInteractionListener mListener;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mStep Parameter 1.
     * @return A new instance of fragment StepDetailsFragment.
     */
    public static StepDetailsFragment newInstance(Step mStep) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, mStep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStep = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);
        setUpVideo();
        if (tvStepDescription != null)
            tvStepDescription.setText(mStep.getDescription());

        if (savedInstanceState != null) {
            currentVideoPostion = savedInstanceState.getLong(VIDEO_POSTION_KEY);
            if (mExoPlayer != null)
                mExoPlayer.seekTo(currentVideoPostion);
        }
        Log.d(TAG, "onCreateView: " + currentVideoPostion);

        return rootView;
    }

    public void setUpVideo() {


        if (TextUtils.isEmpty(mStep.getVideoURL()) && TextUtils.isEmpty(mStep.getThumbnailURL())){
            mPlayerView.setVisibility(View.GONE);
            ivThumbnial.setVisibility(View.GONE);
            tvStepDescription.setVisibility(View.VISIBLE);
        } else {
            if (TextUtils.isEmpty(mStep.getVideoURL())) {
                mPlayerView.setVisibility(View.GONE);
                ivThumbnial.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(mStep.getThumbnailURL()).into(ivThumbnial);

            } else {
                ivThumbnial.setVisibility(View.GONE);
                mPlayerView.setVisibility(View.VISIBLE);
                initializePlayer(Uri.parse(mStep.getVideoURL()));
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity..
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "bakingapp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(VIDEO_POSTION_KEY, currentVideoPostion);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            currentVideoPostion = mExoPlayer.getCurrentPosition();
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpVideo();
        if (mExoPlayer != null)
            mExoPlayer.seekTo(currentVideoPostion);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}
