package com.id.socketio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class activity_search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final ImageView iv = (ImageView) findViewById(R.id.imageView1);

        Animation anim = AnimationUtils
                .loadAnimation
                        (getApplicationContext(),
                                R.anim.animation);
        iv.startAnimation(anim);

    }
}