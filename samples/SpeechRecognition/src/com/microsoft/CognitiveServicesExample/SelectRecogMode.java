package com.microsoft.CognitiveServicesExample;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class SelectRecogMode extends TitleActivity {

    RadioGroup radio_group;
    RadioButton rb_about;
    RadioButton rb_me;
    RadioButton rb_home;
    RadioButton rb_help;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecct_recog_mode);
        setTitle(R.string.srm_title);

        this.radio_group = (RadioGroup)findViewById(R.id.radio_group);
        this.rb_home = (RadioButton)findViewById(R.id.rb_home);
        this.rb_help = (RadioButton)findViewById(R.id.rb_help);
        this.rb_about = (RadioButton)findViewById(R.id.rb_about);
        this.rb_me = (RadioButton)findViewById(R.id.rb_me);


        this.rb_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SelectRecogMode.this,SelectRecogMode.class);
                startActivity(intent);
            }
        });
        this.rb_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SelectRecogMode.this,PersonalSpaceActivity.class);
                startActivity(intent);
            }
        });
        this.rb_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SelectRecogMode.this,AboutSpecchRecogActivity.class);
                startActivity(intent);
            }
        });
        this.rb_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SelectRecogMode.this,HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void micphoneRecog(View view){
        Intent intent = new Intent();
        intent.setClass(this, MicRecognition.class);
        this.startActivity(intent);
    }

    public void dfRecog(View view){
        Intent intent = new Intent();
        intent.setClass(this, DataFileRecogniton.class);
        this.startActivity(intent);
    }

    //这个只是用来开发的时候跳转到原始app---对比测试
    public void duibiceshi(View view){
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
    }
}
