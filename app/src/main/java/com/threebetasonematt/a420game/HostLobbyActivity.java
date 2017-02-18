package com.threebetasonematt.a420game;

import android.content.Intent;
import android.hardware.SensorEvent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HostLobbyActivity extends AppCompatActivity{

    String mLobbyAddress;
    TextView mAddressLabel;
    Button mButtonReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_lobby);

        Intent intent = getIntent();
        mLobbyAddress = intent.getStringExtra(constants.KEY_HOSTING_LOBBY_ADDRESS);

        mAddressLabel = (TextView)findViewById(R.id.hostlobby_address);
        mAddressLabel.setText(mLobbyAddress);

        //handle ready button
        mButtonReady = (Button)findViewById(R.id.button_host_ready);
        mButtonReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonReady.setEnabled(false);

                //TODO: wait for all players to be ready


                //begin game
                Intent intent = new Intent(HostLobbyActivity.this, GameActivity.class);
                intent.putExtra(constants.KEY_GAME_DURATION, constants.DEFAULT_DURATION);
                startActivityForResult(intent, constants.RC_GAME); //not sure if the forResult is needed
            }
        });

    }


}
