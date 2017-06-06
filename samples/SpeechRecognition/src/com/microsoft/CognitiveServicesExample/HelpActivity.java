package com.microsoft.CognitiveServicesExample;

import android.os.Bundle;
public class HelpActivity extends TitleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setTitle(R.string.help);
    }
}
