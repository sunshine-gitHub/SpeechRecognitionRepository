package com.microsoft.CognitiveServicesExample;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

public class AboutSpecchRecogActivity extends TitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_specch_recog);

        setTitle(R.string.xiangguan);
    }


}
