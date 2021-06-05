package com.id.socketio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class activity_login extends AppCompatActivity {

    private Button btn_login;
    private Button btn_register;
    private EditText ET_ID;
    private EditText ET_PW;
    private String data;
    private String ID;
    private String PW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        ET_ID = (EditText) findViewById(R.id.ET_ID);
        ET_PW = (EditText) findViewById(R.id.ET_PW);

        btn_login.setEnabled(true);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_login.this, activity_beforesearch.class);
                ID = ET_ID.getText().toString();
                PW = ET_PW.getText().toString();
                intent.putExtra("userid", ID);
                intent.putExtra("userpw", PW);
                Log.d("tlqkf", "tlqkf");
                startActivity(intent);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_login.this, activity_home.class);
                startActivity(intent);
            }
        });
    }
}