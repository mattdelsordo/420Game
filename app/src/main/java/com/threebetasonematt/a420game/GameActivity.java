package com.threebetasonematt.a420game;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements TimerFragment.GameOverListener, GameOverFragment.backToLobbyListener{

    public static final String TAG = "Game.tag";

    TimerFragment mTimer;
    GameOverFragment mGameOver;
    float mInitialAltitude;

    private SensorManager mSensorManager = null;
    float mCurrentPressure = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        //get initial altitude
        mInitialAltitude = extras.getFloat(constants.KEY_INITIAL_ALTITUDE);
        Log.i(TAG, "Initial altitude: " + Float.toString(mInitialAltitude));

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),SensorManager.SENSOR_DELAY_FASTEST);

        //make timer fragment
        mTimer = new TimerFragment();
        mTimer.setArguments(extras);

        mGameOver = new GameOverFragment();

        getFragmentManager().beginTransaction().add(R.id.activity_game, mTimer).commit();
        //mTimer.countdown();
    }

    //switch out Timer for Game Over message
    @Override
    public void gameOver() {
        //calculate final altitude
        float finalAltitude = mSensorManager.getAltitude(mSensorManager.PRESSURE_STANDARD_ATMOSPHERE, mCurrentPressure);
        float altitudeChange = finalAltitude - mInitialAltitude;

        Log.i(TAG, "Final altitude: " + finalAltitude);
        Log.i(TAG, "Altitude change: " + altitudeChange);

        Bundle altBundle = new Bundle();
        altBundle.putFloat(constants.KEY_FINAL_ALTITUDE, altitudeChange);
        mGameOver.setArguments(altBundle);

        getFragmentManager().beginTransaction().remove(mTimer).add(R.id.activity_game, mGameOver).commit();
    }


    //return to lobby
    @Override
    public void returnToLobby() {
        finish();
    }

    private SensorEventListener mSensorListener = new SensorEventListener(){

        @Override
        public void onSensorChanged(SensorEvent event) {
            // when pressure value is changed, this method will be called.
            float pressure_value = 0.0f;

            // if you use this listener as listener of only one sensor (ex, Pressure), then you don't need to check sensor type.
            if( Sensor.TYPE_PRESSURE == event.sensor.getType() ) {
                pressure_value = event.values[0];
                //mAddressLabel = (TextView)findViewById(R.id.hostlobby_address);
                // mAddressLabel.setText(String.valueOf(pressure_value));

                mCurrentPressure = pressure_value;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
