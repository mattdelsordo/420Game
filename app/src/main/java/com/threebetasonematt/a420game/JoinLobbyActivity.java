package com.threebetasonematt.a420game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JoinLobbyActivity extends AppCompatActivity {

    String mLobbyAddress = "error";
    TextView mAddressLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);

        Intent intent = getIntent();
        mLobbyAddress = intent.getStringExtra(constants.KEY_JOINING_LOBBY_ADDRESS);

        mAddressLabel = (TextView)findViewById(R.id.joinlobby_addressindicator);
        mAddressLabel.setText(mLobbyAddress);
    }
}
