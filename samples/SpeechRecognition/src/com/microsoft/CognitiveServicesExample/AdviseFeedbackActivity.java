package com.microsoft.CognitiveServicesExample;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

public class AdviseFeedbackActivity extends TitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advise_feedback);
        setTitle(R.string.advise_feedback);
    }
}
