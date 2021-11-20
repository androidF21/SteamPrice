package com.example.csgomoney;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "RegisterActivity";
    public EditText tilRUser;
    public EditText tilRPass;
    private Button btnReg;
    public EditText tilSID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        tilRUser = findViewById(R.id.tilRUser);
        tilRPass = findViewById(R.id.tilRPass);
        tilSID  = findViewById(R.id.tilRPass);
        btnReg = findViewById(R.id.btnReg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Register Button");
                String newUser = tilRUser.getText().toString();
                String newPass = tilRPass.getText().toString();
                String SteamID = tilSID.getText().toString();
                registerUser(newUser,newPass,SteamID);
            }
        });

    }

    private void registerUser(String newUser, String newPass,String SteamID) {
        ParseUser nUser = new ParseUser();
        nUser.setUsername(newUser);
        nUser.setPassword(newPass);
        nUser.put("SteamID",SteamID);
        nUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.i(TAG,"Issue with Registering" + e);
                    return;
                }
                tilRUser.setText("");
                tilRPass.setText("");
                tilSID.setText("");
                Toast.makeText(RegisterActivity.this, newUser+" Registered", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
