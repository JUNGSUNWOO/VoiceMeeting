package com.remotemonster.sdktest.sample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.remon.sdktest.R;
import com.remotemonster.sdk.data.AudioType;

/**
 * Created by lucas on 2018. 5. 11..
 */

public class ConfigDialog extends Dialog {

    Button btnOk;
    EditText etChannelName;
    LinearLayout llChannelName;
    Spinner spAudioType;
    Spinner spAudioCodec;

    private Activity activity;
    private RemonApplication remonApplication;
    private IConfigSettingListener onINameInputDlgListener;

    public ConfigDialog(@NonNull Activity activity, boolean isCreate, IConfigSettingListener iConfigSettingListener) {
        super(activity, R.style.FullScreenDialogStyle);
        this.onINameInputDlgListener = iConfigSettingListener;
        this.activity = activity;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_set_config);


        btnOk = (Button) findViewById(R.id.btnOk);
        etChannelName = (EditText) findViewById(R.id.etChannelName);
        llChannelName = (LinearLayout) findViewById(R.id.llChannelName);
        spAudioType = (Spinner) findViewById(R.id.spAudioType);
        spAudioCodec = (Spinner) findViewById(R.id.spAudioCodec);

        setCanceledOnTouchOutside(false);
        remonApplication = (RemonApplication) activity.getApplicationContext();

        if (isCreate) {
            llChannelName.setVisibility(View.VISIBLE);
        } else {
            llChannelName.setVisibility(View.GONE);
        }

        btnOk.setOnClickListener(v -> {
            if (isCreate) {
                if (!etChannelName.getText().toString().equals("")) {
                    onINameInputDlgListener.configSetResult(etChannelName.getText().toString());
                    dismiss();
                } else {
                    Toast.makeText(activity, "Please enter your channel name.", Toast.LENGTH_SHORT).show();
                }
            } else {
                onINameInputDlgListener.configSetResult(etChannelName.getText().toString());
                dismiss();
            }
        });


        spAudioCodec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        remonApplication.getConfig().setAudioCodec("OPUS");
                        break;

                    case 1:
                        remonApplication.getConfig().setVideoCodec("ISAC");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spAudioCodec.setSelection(0);

      spAudioType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        remonApplication.getConfig().setAudioType(AudioType.VOICE);
                        break;

                    case 1:
                        remonApplication.getConfig().setAudioType(AudioType.MUSIC);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (activity instanceof CallActivity) {
            spAudioType.setSelection(0);
        } else {
            spAudioType.setSelection(1);
        }


    }

    private void closeSoftKey() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public interface IConfigSettingListener {
        public void configSetResult(String chid);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activity.finish();
    }
}
