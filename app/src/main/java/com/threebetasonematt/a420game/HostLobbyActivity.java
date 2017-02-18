package com.threebetasonematt.a420game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HostLobbyActivity extends AppCompatActivity {

    String mLobbyAddress;
    TextView mAddressLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_lobby);

        Intent intent = getIntent();
        mLobbyAddress = intent.getStringExtra(constants.KEY_HOSTING_LOBBY_ADDRESS);

        mAddressLabel = (TextView)findViewById(R.id.hostlobby_address);
        mAddressLabel.setText(mLobbyAddress);

    }


}
