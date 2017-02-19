package com.threebetasonematt.a420game;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class HostLobbyActivity extends AppCompatActivity{

    String mLobbyAddress;
    TextView mAddressLabel;
    Button mButtonReady;
    private SensorManager mSensorManager = null;
    float mCurrentPressure = 0;

    ListView mLobbyList;
    ArrayAdapter<String> mUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_lobby);

        //set up listview
        String[] templist = {};
        mUserList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, templist);
        mLobbyList = (ListView)findViewById(R.id.host_user_list);
        mLobbyList.setAdapter(mUserList);



        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),SensorManager.SENSOR_DELAY_FASTEST);

        Intent intent = getIntent();
        mLobbyAddress = intent.getStringExtra(constants.KEY_HOSTING_LOBBY_ADDRESS);

        mAddressLabel = (TextView)findViewById(R.id.hostlobby_address);
        mAddressLabel.setText(mLobbyAddress);


        Socket soc=null;
        PrintWriter pw=null;
        try {
            soc = new Socket("184.72.127.7", 4333);
            SocketHandler.setSocket(soc);
            pw = SocketHandler.getPW();
            pw.println("ready");
            pw.flush();

            
            Thread.sleep(1000);
            pw.println("done");
            pw.flush();
            Thread.sleep(1000);
            pw.println("1000\n Bob");
            pw.flush();
        }
        catch(Exception e){}



        //handle ready button
        mButtonReady = (Button)findViewById(R.id.button_host_ready);
        mButtonReady.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View v) {
                mButtonReady.setEnabled(false);

                //TODO: wait for all players to be ready

                //stop listening for pressure
                mSensorManager.unregisterListener(mSensorListener);

                //calculate initial altitude
                float initialAltitude = mSensorManager.getAltitude(mSensorManager.PRESSURE_STANDARD_ATMOSPHERE, mCurrentPressure);


                //wait for start from server
                try{
                    String thisLine;
                    BufferedReader reader = SocketHandler.getBR();
                    while((reader.readLine()).equalsIgnoreCase("ready")){

                    }

                }catch(Exception e){}


                //begin game
                Intent intent = new Intent(HostLobbyActivity.this, GameActivity.class);

                intent.putExtra(constants.KEY_GAME_DURATION, constants.DEFAULT_DURATION);
                intent.putExtra(constants.KEY_INITIAL_ALTITUDE, initialAltitude);
                startActivityForResult(intent, constants.RC_GAME); //not sure if the forResult is needed
            }
        });


    }

    //handles getting barometric pressure data
    private SensorEventListener mSensorListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // when accuracy changed, this method will be called.
        }

        @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
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
    };


    //fills the displayed list with users from the server
    public void populateList(String[] userList){
        mUserList.clear();
        mUserList.addAll(userList);
    }

}
