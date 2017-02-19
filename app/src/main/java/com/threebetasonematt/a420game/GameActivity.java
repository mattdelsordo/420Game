package com.threebetasonematt.a420game;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.PrintWriter;
import java.net.Socket;


public class GameActivity extends AppCompatActivity implements TimerFragment.GameOverListener, GameOverFragment.backToLobbyListener {

    public static final String TAG = "Game.tag";
    TimerFragment mTimer;
    GameOverFragment mGameOver;
    float mInitialAltitude;
    String mUsername;

    private SensorManager mSensorManager = null;
    float mCurrentPressure = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_FASTEST);

        //make timer fragment
        mTimer = new TimerFragment();
        mTimer.setArguments(extras);

        mGameOver = new GameOverFragment();

        getFragmentManager().beginTransaction().add(R.id.activity_game, mTimer).commit();
        //mTimer.countdown();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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


        PrintWriter pw=null;
        try {

            Socket soc=SocketHandler.getSocket();
            pw = SocketHandler.getPW();
            pw.println("done");
            pw.flush();
            Thread.sleep(1500);
            pw.println(String.valueOf(altitudeChange)+"\n"+SocketHandler.username);
            pw.flush();
        }
        catch(Exception e){}

        getFragmentManager().beginTransaction().remove(mTimer).add(R.id.activity_game, mGameOver).commit();
    }

    //return to lobby
    @Override
    public void returnToLobby() {
        finish();
    }

    private SensorEventListener mSensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // when pressure value is changed, this method will be called.
            float pressure_value = 0.0f;

            // if you use this listener as listener of only one sensor (ex, Pressure), then you don't need to check sensor type.
            if (Sensor.TYPE_PRESSURE == event.sensor.getType()) {
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Game Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
