package com.threebetasonematt.a420game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements TimerFragment.GameOverListener{



    TimerFragment mTimer;
    GameOverFragment mGameOver;

    //TODO: What I want to do here is make one fragment for the timer and one for the game winner and then switch the two

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        //make timer fragment
        mTimer = new TimerFragment();
        mTimer.setArguments(extras);

        mGameOver = new GameOverFragment();

        getFragmentManager().beginTransaction().add(R.id.activity_game, mTimer).commit();
    }

    //switch out Timer for Game Over message
    @Override
    public void gameOver() {
        getFragmentManager().beginTransaction().remove(mTimer).add(R.id.activity_game, mGameOver).commit();
    }
}
