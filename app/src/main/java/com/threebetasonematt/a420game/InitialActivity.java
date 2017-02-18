package com.threebetasonematt.a420game;

/*
Handles logging in and starting a game.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InitialActivity extends AppCompatActivity {

    public static final String TAG = "InitialActivity";

    Button mPlayButton;
    EditText mEnterUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get reference for username box
        mEnterUsername = (EditText)findViewById(R.id.edittext_enter_username);

        //handle play button
        mPlayButton = (Button)findViewById(R.id.button_splash_play);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEnterUsername.getText().toString();
                if(username.equals("") || username.equals(null)){
                    //display error if the username is bad
                    Toast.makeText(InitialActivity.this, R.string.blank_username_error, Toast.LENGTH_SHORT).show();
                }
                else{
                    //open next activity
                    Intent intent = new Intent(InitialActivity.this, StartGameActivity.class);
                    intent.putExtra(constants.KEY_USERNAME, username);
                    startActivity(intent);
                }
            }
        });


    }
}
