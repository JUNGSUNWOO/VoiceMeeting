package com.id.socketio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class activity_beforesearch extends AppCompatActivity {

    private EditText textField;
    private ImageButton sendButton;

    public static final String TAG  = "MainActivity";
    public static String uniqueId;

    private String Username;
    private String userid;
    private String userpw;

    private Boolean hasConnection = false;

    private Button btn_search;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://10.20.10.120:3000");
        } catch (URISyntaxException e) {}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beforesearch);

        btn_search = (Button) findViewById(R.id.btn_search);
        userid = getIntent().getStringExtra("userid");
        userpw = getIntent().getStringExtra("userpw");

        uniqueId = UUID.randomUUID().toString();
        Log.i(TAG, "onCreate: " + uniqueId);

        if(savedInstanceState != null){
            hasConnection = savedInstanceState.getBoolean("hasConnection");
        }

        if(hasConnection){
        }else {
            mSocket.connect();
            mSocket.on("connect user", onNewUser);
            JSONObject USER = new JSONObject();
            try {
                USER.put("userid", userid);
                USER.put("userpw", userpw);
                mSocket.emit("signInEvent", USER);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(activity_beforesearch.this, activity_search.class);
                    startActivity(intent);

            }
        });

        Log.i(TAG, "onCreate: " + hasConnection);
        hasConnection = true;

        Log.i(TAG, "onCreate: " + userid + userpw+ " " + "Connected");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("hasConnection", hasConnection);
    }

    Emitter.Listener onNewUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int length = args.length;

                    if(length == 0){
                        return;
                    }
                    //Here i'm getting weird error..................///////run :1 and run: 0
                    Log.i(TAG, "run: ");
                    Log.i(TAG, "run: " + args.length);
                    String username =args[0].toString();
                    try {
                        JSONObject object = new JSONObject(username);
                        username = object.getString("username");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };


    private void addMessage(String username, String message) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if(isFinishing()){
            Log.i(TAG, "onDestroy: ");

            JSONObject userId = new JSONObject();
            try {
                userId.put("username", Username + " DisConnected");
                mSocket.emit("connect user", userId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mSocket.disconnect();
            mSocket.off("connect user", onNewUser);
            Username = "";
        }else {
            Log.i(TAG, "onDestroy: is rotating.....");
        }

    }


}
