package com.threebetasonematt.a420game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterAddressActivity extends AppCompatActivity {

    EditText mEnterAddress;
    Button mConnectToAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_address);

        mEnterAddress = (EditText)findViewById(R.id.edittext_enterAddress);

        mConnectToAddress = (Button)findViewById(R.id.button_enterAddress);
        mConnectToAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = mEnterAddress.getText().toString();

                if(address.equals("") || address.equals(null)) Toast.makeText(EnterAddressActivity.this, R.string.blank_address_error, Toast.LENGTH_SHORT).show();
                else if (verifyAddress(address)){
                    Intent intent = new Intent(EnterAddressActivity.this, JoinLobbyActivity.class);
                    intent.putExtra(constants.KEY_JOINING_LOBBY_ADDRESS, address);
                    startActivity(intent);
                }
                else Toast.makeText(EnterAddressActivity.this, R.string.invalid_address_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //contact server and make sure that the address you're trying to connect to is real
     private boolean verifyAddress(String address){
         return true; //TODO: this is temporary
     }
}
