package com.microsoft.CognitiveServicesExample;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends TitleActivity {

    private Button logout_btn;
    private TextView about;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle(R.string.settings_title);

        this.logout_btn = (Button)findViewById(R.id.logout_btn);

        this.logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this,LoginActivity.class);
                SettingActivity.this.startActivity(intent);
            }
        });
        this.about = (TextView)findViewById(R.id.about);
        this.about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this,AboutSpecchRecogActivity.class);
                startActivity(intent);
            }
        });


    }
}
