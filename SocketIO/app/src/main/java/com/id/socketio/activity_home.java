package com.id.socketio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class activity_home extends AppCompatActivity {

    private Button btn_rg;
    private EditText RG_PW;
    private EditText RG_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_rg = (Button) findViewById(R.id.btn_rg);
        RG_ID = (EditText) findViewById(R.id.RG_ID);
        RG_PW = (EditText) findViewById(R.id.RG_PW);

        btn_rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_home.this, activity_login.class);
                startActivity(intent);
            }
        });
    }
}