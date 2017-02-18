package com.threebetasonematt.a420game;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameOverFragment extends Fragment {


    public GameOverFragment() {
        // Required empty public constructor
    }

    TextView mWinnerMessage;
    Button mGG;
    String mVictorText;
    private backToLobbyListener mListener;
    float mWinningAltitude;

    SensorManager mSensorManager;
    Sensor mPressure;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_over, container, false);

        mWinnerMessage = (TextView)view.findViewById(R.id.victor_message);
        mWinnerMessage.setText(getVictorName() + " was the victor with an altitude of " + getWinningAltitude() + "!");

        //button returns you to lobby
        mGG = (Button)view.findViewById(R.id.button_gg);
        mGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.returnToLobby();;
            }
        });

        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        mSensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                mWinningAltitude = event.values[0];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //if sensor accuracy changes
            }
        }, mPressure, SensorManager.SENSOR_DELAY_FASTEST);


        return view;
    }

    //interface to handle returning to the lobby
    public interface backToLobbyListener{
        public void returnToLobby();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (backToLobbyListener)context;
    }

    //return the victor's name
    public String getVictorName(){
        return "Billy";
    }

    //return victor's altitude
    public String getWinningAltitude(){






        return Float.toString(mWinningAltitude);
    }


}
