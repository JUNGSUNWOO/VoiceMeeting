package com.id.socketio;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.JsonReader;
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

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static String uniqueId;
    private String LatLng;
    private String Longitude;
    private String Latitude;
    private Boolean hasConnection = false;
    private Button btn_cur_gps;
    private Socket mSocket;

    {
        try {
            mSocket = IO.socket("http://10.20.11.202:5000");
        } catch (URISyntaxException e) {
        }
    }

    JSONObject get_Current_GPS() throws JSONException {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert manager != null;
        JSONObject cur_gps = new JSONObject();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        cur_gps.put("latitude", latitude);
        cur_gps.put("longitude", longitude);
        //Log.d("get cur gps method", String.valueOf(cur_gps));
        return cur_gps;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_cur_gps = findViewById(R.id.btn_cur_gps);
        Longitude = getIntent().getStringExtra("longitude");
        Latitude = getIntent().getStringExtra("latitude");
        LatLng = String.format("gps : (%s, %s)", Latitude, Longitude);

        Log.d("gps_location", LatLng);
        uniqueId = UUID.randomUUID().toString();
        Log.i(TAG, "onCreate: " + uniqueId);

        if (savedInstanceState != null) {
            hasConnection = savedInstanceState.getBoolean("hasConnection");
        }

        if (hasConnection) {
        } else {
            mSocket.connect();
            mSocket.on("send gps", onCurrentGps);
            JSONObject gps_data = new JSONObject();
            try {
                gps_data.put("latitude", Latitude);
                gps_data.put("longitude", Longitude);
                Log.d("gps", String.valueOf(gps_data));

                mSocket.emit("updateGpsEvent", gps_data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.i(TAG, "onCreate: " + hasConnection);
        hasConnection = true;

//        Log.i(TAG, "onCreate: " + Username + " " + "Connected");
        btn_cur_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("TEST GPS METHOD", String.valueOf(get_Current_GPS()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("hasConnection", hasConnection);
    }


    Emitter.Listener onCurrentGps = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject receviedData = (JSONObject) args[0];
            Log.d("onCurGps", String.valueOf(receviedData));
            try {
                mSocket.emit("updateGpsEvent", get_Current_GPS());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (isFinishing()) {
            Log.i(TAG, "onDestroy: ");

//            JSONObject userId = new JSONObject();
            //                userId.put("username", Username + " DisConnected");
//            mSocket.emit("connect user", userId);

            mSocket.disconnect();
            mSocket.off("updateGpsEvent", onCurrentGps);
        } else {
            Log.i(TAG, "onDestroy: is rotating.....");
        }

    }

}
