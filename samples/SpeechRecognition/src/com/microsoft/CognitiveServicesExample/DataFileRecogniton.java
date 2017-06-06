package com.microsoft.CognitiveServicesExample;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.ecnu.enums.FinalResponseStatus;
import com.microsoft.cognitiveservices.speechrecognition.DataRecognitionClient;
import com.microsoft.cognitiveservices.speechrecognition.ISpeechRecognitionServerEvents;
import com.microsoft.cognitiveservices.speechrecognition.RecognitionResult;
import com.microsoft.cognitiveservices.speechrecognition.RecognitionStatus;
import com.microsoft.cognitiveservices.speechrecognition.SpeechRecognitionMode;
import com.microsoft.cognitiveservices.speechrecognition.SpeechRecognitionServiceFactory;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
public class DataFileRecogniton extends TitleActivity implements ISpeechRecognitionServerEvents {
    RadioButton chinese;
    private Spinner spinner;
    private String fileName;
    RadioGroup radio_group;
    RadioButton rb_about;
    RadioButton rb_me;
    RadioButton rb_home;
    RadioButton rb_help;
    int m_waitSeconds = 0;
    DataRecognitionClient dataClient = null;
    FinalResponseStatus isReceivedResponse = FinalResponseStatus.NotReceived;
    TextView _displayDFR;
    RadioGroup _groupLanguage;
    Button _startDataFileRecogButton;
    Button _resetDFR;
    String language = "en-US";
    public String getPrimaryKey() {
        return this.getString(R.string.primaryKey);
    }

    private String getLuisAppId() {
        return this.getString(R.string.luisAppID);
    }

    private String getLuisSubscriptionID() {
        return this.getString(R.string.luisSubscriptionID);
    }




    private String getLanguage() {
        int id = this._groupLanguage.getCheckedRadioButtonId();
        String str = "";
        if(id == R.id.chinese){
            str = "zh-CN";
        }
        else if(id == R.id.english){
            str = "en-US";
        }
        else if(id == R.id.deutsch){
            str = "de-DE";
        }
        else{
            str = "en-US";
        }
       return str;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_file_recogniton);
        setTitle(R.string.df_title);


        this.chinese = (RadioButton)findViewById(R.id.chinese);
        this._groupLanguage = (RadioGroup)findViewById(R.id.groupLanguage);
        this._displayDFR = (TextView) findViewById(R.id.displayDFR);
        this._startDataFileRecogButton = (Button) findViewById(R.id.startDataFileRecog);
        this._resetDFR = (Button) findViewById(R.id.resetDFR);
        this.radio_group = (RadioGroup)findViewById(R.id.radio_group);
        this.rb_home = (RadioButton)findViewById(R.id.rb_home);
        this.rb_help = (RadioButton)findViewById(R.id.rb_help);
        this.rb_about = (RadioButton)findViewById(R.id.rb_about);
        this.rb_me = (RadioButton)findViewById(R.id.rb_me);

        _displayDFR.setMovementMethod(ScrollingMovementMethod.getInstance());

        this.spinner = (Spinner)findViewById(R.id.spinner);
        fileName = "english1.wav";

        if (getString(R.string.primaryKey).startsWith("Please")) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.add_subscription_key_tip_title))
                    .setMessage(getString(R.string.add_subscription_key_tip))
                    .setCancelable(false)
                    .show();
        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.spingarr);
                fileName = languages[pos].toString()+".wav";
                DataFileRecogniton.this._startDataFileRecogButton.setEnabled(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final DataFileRecogniton This = this;





        this._startDataFileRecogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                This.StartDataFileRecogButton_Click(arg0);
            }
        });



        this._resetDFR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                This.resetButton_Click(arg0);
            }
        });




        this._groupLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
                This.shortOrLongDataFile_Click(rGroup, checkedId);
            }
        });



        this.rb_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DataFileRecogniton.this,SelectRecogMode.class);
                startActivity(intent);
            }
        });
        this.rb_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DataFileRecogniton.this,PersonalSpaceActivity.class);
                startActivity(intent);
            }
        });
        this.rb_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DataFileRecogniton.this,AboutSpecchRecogActivity.class);
                startActivity(intent);
            }
        });
        this.rb_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DataFileRecogniton.this,HelpActivity.class);
                startActivity(intent);
            }
        });

    }
    private void resetButton_Click(View arg0) {
        if (getLanguage().equals("zh-CN")){
            this._displayDFR.setText("语音识别");
        }
        else if (getLanguage().equals("en-US")){
            this._displayDFR.setText("Speech Recognition");
        }
        else{
            this._displayDFR.setText("Spracherkennung Mikrofon");
        }
        this.chinese.setChecked(true);
        this._startDataFileRecogButton.setEnabled(true);
    }

    private void shortOrLongDataFile_Click(RadioGroup rGroup, int checkedId) {



        if (getLanguage().equals("zh-CN")){
            this._displayDFR.setText("语音识别");
        }
        else if (getLanguage().equals("en-US")){
            this._displayDFR.setText("Speech Recognition");
        }
        else {
            this._displayDFR.setText("Spracherkennung Mikrofon");
        }
        this._startDataFileRecogButton.setEnabled(true);
    }

    private void returnBackDataFileRecogButton_Click(View arg0){
        Intent intent = new Intent();
        intent.setClass(this, MicRecognition.class);
        this.startActivity(intent);
    }

    private String getAuthenticationUri() {
        return this.getString(R.string.authenticationUri);
    }

    private void StartDataFileRecogButton_Click(View arg0) {
        this._startDataFileRecogButton.setEnabled(false);


        if (fileName.equals("english1.wav")){
            this.m_waitSeconds = 10;
        }

        else{
            this.m_waitSeconds = 50;
        }

        this.LogRecognitionStart();

        if (this.dataClient != null) {
            try {
                this.dataClient.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            this.dataClient = null;
        }
            if (null == this.dataClient) {

                    this.dataClient = SpeechRecognitionServiceFactory.createDataClient(
                            this,
                            this.getMode(),
                            getLanguage(),
                            this,
                            this.getPrimaryKey());

                this.dataClient.setAuthenticationUri(this.getAuthenticationUri());
            }

            this.SendAudioHelper(fileName);

    }

    private void LogRecognitionStart() {
        String recoSource;
        this.language = getLanguage();
        if (this.getMode() == SpeechRecognitionMode.ShortPhrase) {
            if (language.equals("zh-CN")){
                recoSource = "短文件";
            }
            else if (language.equals("en-US")){
                recoSource = "short Phrase";
            }
            else {
                recoSource = "kurzes Gespräch";
            }
        } else {
            if (language.equals("zh-CN")){
                recoSource = "长文件";
            }
            else if (language.equals("en-US")){
                recoSource = "long Phrase";
            }
            else {
                recoSource = "lange Gespräche";
            }
        }
        this._displayDFR.setText("");
        if (language.equals("zh-CN")){

            this.WriteLine("开始语音文件识别---"+"\n");
        }
        else if (language.equals("en-US")){

            this.WriteLine("Start voice file recognition---"+"\n");
        }
        else {

            this.WriteLine("Starten Sie die Spracherkennung Dateien---"+"\n");
        }
    }

    private SpeechRecognitionMode getMode() {
        if (m_waitSeconds<=20) {
            return SpeechRecognitionMode.ShortPhrase;

        }
        else{
            return SpeechRecognitionMode.LongDictation;
        }
    }

    private void WriteLine() {
        this.WriteLine("");
    }

    private void WriteLine(String text) {
        this._displayDFR.append(text);
    }

    public void onFinalResponseReceived(final RecognitionResult response) {
        boolean isFinalDicationMessage = this.getMode() == SpeechRecognitionMode.LongDictation &&
                (response.RecognitionStatus == RecognitionStatus.EndOfDictation ||
                        response.RecognitionStatus == RecognitionStatus.DictationEndSilenceTimeout);


        if (isFinalDicationMessage) {
            this._startDataFileRecogButton.setEnabled(true);
            isReceivedResponse = FinalResponseStatus.OK;
        }

        if (!isFinalDicationMessage) {
            String str = "";
            this.language = getLanguage();
            this.WriteLine(str+"\n");
            for (int i = 0; i < response.Results.length; i++) {
                this.WriteLine(response.Results[i].Confidence +
                        ": \"" + response.Results[i].DisplayText + "\"\n");
            }
            this.WriteLine();
        }
    }

    public void onIntentReceived(final String payload) {
        this.WriteLine("--- Intent received by onIntentReceived() ---");
        this.WriteLine(payload);
        this.WriteLine();
    }

    public void onPartialResponseReceived(final String response) {
        this.WriteLine("Partial result:");
        this.WriteLine(response+"\n");
    }

    public void onError(final int errorCode, final String response) {
        this._startDataFileRecogButton.setEnabled(true);
        this._displayDFR.setText("");
        this.language = getLanguage();
        if (language.equals("zh-CN")){
            this.WriteLine("---语音识别已结束---");
        }
        else if (language.equals("en-US")){
            this.WriteLine("---Speech recognition has ended---");
        }
        else{
            this.WriteLine("---Die Spracherkennung ist beendet---");
        }

    }


    public void onAudioEvent(boolean recording) {}

    private void SendAudioHelper(String filename) {
        DataFileRecogniton.RecognitionTask doDataReco = new RecognitionTask(this.dataClient, this.getMode(), filename);
        try
        {
            doDataReco.execute().get(m_waitSeconds, TimeUnit.SECONDS);
        }
        catch (Exception e)
        {
            doDataReco.cancel(true);
            this.isReceivedResponse = FinalResponseStatus.Timeout;
        }
    }

    private class RecognitionTask extends AsyncTask<Void, Void, Void> {
        DataRecognitionClient dataClient;
        SpeechRecognitionMode recoMode;
        String filename;
        RecognitionTask(DataRecognitionClient dataClient, SpeechRecognitionMode recoMode, String filename) {
            this.dataClient = dataClient;
            this.recoMode = recoMode;
            this.filename = filename;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                System.out.println(filename);
                InputStream fileStream = getAssets().open(filename);
                int bytesRead = 0;
                byte[] buffer = new byte[1024];

                do {
                    bytesRead = fileStream.read(buffer);
                    if (bytesRead > -1) {
                        dataClient.sendAudio(buffer, bytesRead);
                    }
                } while (bytesRead > 0);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            finally {
                dataClient.endAudio();
            }
            return null;
        }
    }
}
