package com.threebetasonematt.a420game;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment {


    public TimerFragment() {
        // Required empty public constructor
    }

    public static final String TAG = "TimerFragment";
    TextView mCountdownView;
    int mGameDuration;
    private GameOverListener mListener;
    private CountDownTimer mGameTimer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        //get passed arguments
        Bundle args = getArguments();

        mCountdownView = (TextView)view.findViewById(R.id.countdown_clock);

        //set game duration
        mGameDuration = args.getInt(constants.KEY_GAME_DURATION, constants.DEFAULT_DURATION); //get game duration from whatever called this

        //start new countdowntimer
        mGameTimer = new CountDownTimer(mGameDuration * constants.SECOND, constants.SECOND){

            @Override
            public void onTick(long millisUntilFinished) {
                int timeLeft = ((int)millisUntilFinished/constants.SECOND);
                if(timeLeft +1 != mGameDuration) mCountdownView.setText(Integer.toString(timeLeft));
                //Log.i(TAG, timeLeft + " " + mGameDuration);

            }

            @Override
            public void onFinish() {
                mListener.gameOver();
            }
        };

        startTimer();

        return view;
    }


    public void startTimer(){
        mGameTimer.start();
    }

    //interface to return gameover code from timer
    public interface GameOverListener{
        public void gameOver();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (GameOverListener)context;
    }
}
