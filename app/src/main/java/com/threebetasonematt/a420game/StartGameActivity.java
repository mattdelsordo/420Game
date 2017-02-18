package com.threebetasonematt.a420game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartGameActivity extends AppCompatActivity {

    String mUsername;
    TextView mWelcomeText;
    Button mButtonHost, mButtonJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        Intent intent = getIntent();
        mUsername = intent.getStringExtra(constants.KEY_USERNAME);

        //initialize text
        mWelcomeText = (TextView)findViewById(R.id.startgame_welcometext);
        mWelcomeText.setText("Ready to get high, " + mUsername + "?");

        //set up buttons
        //goes to page to host a game
        mButtonHost = (Button)findViewById(R.id.button_host_game);
        mButtonHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartGameActivity.this, HostLobbyActivity.class);
                intent.putExtra(constants.KEY_HOSTING_LOBBY_ADDRESS, generateLobbyAddress());
                startActivity(intent);
            }
        });

        //goes to page to join a game
        mButtonJoin = (Button)findViewById(R.id.button_join_game);
        mButtonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartGameActivity.this, EnterAddressActivity.class);
                //intent.putExtra(constants.KEY_HOSTING_LOBBY_ADDRESS, generateLobbyAddress());
                startActivity(intent);
            }
        });
    }

    //this generates an address for the room
    //probably not going to end up as a string
    private String generateLobbyAddress(){
        return "0000000";
    }
}
