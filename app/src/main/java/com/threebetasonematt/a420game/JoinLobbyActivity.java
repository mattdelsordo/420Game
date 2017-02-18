package com.threebetasonematt.a420game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JoinLobbyActivity extends AppCompatActivity {

    String mLobbyAddress = "error";
    TextView mAddressLabel;
    Button mButtonReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);

        Intent intent = getIntent();
        mLobbyAddress = intent.getStringExtra(constants.KEY_JOINING_LOBBY_ADDRESS);

        mAddressLabel = (TextView)findViewById(R.id.joinlobby_address);
        mAddressLabel.setText(mLobbyAddress);

        //handle ready button
        mButtonReady = (Button)findViewById(R.id.button_join_ready);
        mButtonReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonReady.setEnabled(false);

                //TODO: wait for rest of players to be ready

                //begin game
                Intent intent = new Intent(JoinLobbyActivity.this, GameActivity.class);
                intent.putExtra(constants.KEY_GAME_DURATION, constants.DEFAULT_DURATION);
                startActivityForResult(intent, constants.RC_GAME); //not sure if the forResult is needed
            }
        });
    }
}
