package com.threebetasonematt.a420game;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment {


    public TimerFragment() {
        // Required empty public constructor
    }

    TextView mCountdownView;
    int mGameDuration;
    private GameOverListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        //get passed arguments
        Bundle args = getArguments();

        mCountdownView = (TextView)view.findViewById(R.id.countdown_clock);

        //set game duration
        mGameDuration = args.getInt(constants.KEY_GAME_DURATION, 30); //get game duration from whatever called this

        return view;
    }

    //animate the countdown of the clock
    private void countdown(){


        //at end call game over
        mListener.gameOver();
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
