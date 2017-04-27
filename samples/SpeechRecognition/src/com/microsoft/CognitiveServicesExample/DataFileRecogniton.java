package com.microsoft.CognitiveServicesExample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    //RadioGroup _shortOrLongDataFile;
//    RadioButton _shortFile;
//    RadioButton _longFile;
    Button _startDataFileRecogButton;

    Button _resetDFR;

    String language = "en-US";


    /**
     * Gets the primary subscription key
     */
    public String getPrimaryKey() {
        return this.getString(R.string.primaryKey);
    }

    /**
     * Gets the LUIS application identifier.
     * @return The LUIS application identifier.
     */
    private String getLuisAppId() {
        return this.getString(R.string.luisAppID);
    }

    /**
     * Gets the LUIS subscription identifier.
     * @return The LUIS subscription identifier.
     */
    private String getLuisSubscriptionID() {
        return this.getString(R.string.luisSubscriptionID);
    }



    /**
     * Gets the long wave file path.
     * @return The long wave file.
     */


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
        //this._shortFile = (RadioButton)findViewById(R.id.shortFile);
        //this._longFile = (RadioButton)findViewById(R.id.longFile);
        //this._shortOrLongDataFile = (RadioGroup)findViewById(R.id.shortOrLongDataFile);
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
                //Toast.makeText(DataFileRecogniton.this, "你点击的是:"+languages[pos],Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
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
                //关闭了DataRecognitionClient对象
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
        // Reset everything
        if (getLanguage().equals("zh-CN")){
            this._displayDFR.setText("语音识别");
        }
        else if (getLanguage().equals("en-US")){
            this._displayDFR.setText("Speech Recognition");
        }
        else{
            this._displayDFR.setText("Spracherkennung Mikrofon");
        }
        //this._shortFile.setChecked(true);
        this.chinese.setChecked(true);
        this._startDataFileRecogButton.setEnabled(true);
    }

    private void shortOrLongDataFile_Click(RadioGroup rGroup, int checkedId) {
        // Reset everything



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


        //this.getMode() 返回你选择的语音识别模式（长和短）
        if (fileName.equals("english1.wav")){
            this.m_waitSeconds = 10;
        }

        else{
            this.m_waitSeconds = 50;
        }
       // this.m_waitSeconds = this.getMode() == SpeechRecognitionMode.ShortPhrase ? 20 : 200;

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

                //System.out.println(getLanguage()+"*****************88");
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
        //int id = this._shortOrLongDataFile.getCheckedRadioButtonId();
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

    /**
     * Writes the line.
     * @param text The line to write.
     */
    private void WriteLine(String text) {
        this._displayDFR.append(text);
    }

    //最后响应完毕调用
    public void onFinalResponseReceived(final RecognitionResult response) {
        //识别结束或许停顿超时返回true；
        boolean isFinalDicationMessage = this.getMode() == SpeechRecognitionMode.LongDictation &&
                (response.RecognitionStatus == RecognitionStatus.EndOfDictation ||
                        response.RecognitionStatus == RecognitionStatus.DictationEndSilenceTimeout);


        if (isFinalDicationMessage) {
            this._startDataFileRecogButton.setEnabled(true);
            isReceivedResponse = FinalResponseStatus.OK;
        }

        if (!isFinalDicationMessage) {
            //this._displayDFR.setText("");
            String str = "";
            this.language = getLanguage();
//            if (language.equals("zh-CN")){
//                str = "************最终的识别结果************";
//            }
//            else if (language.equals("en-US")){
//                str = "*********Final n-BEST Results*********";
//            }
//            else {
//                str = "***Die endgültige Erkennungsergebnis***";
//            }
            this.WriteLine(str+"\n");
            for (int i = 0; i < response.Results.length; i++) {
                this.WriteLine(response.Results[i].Confidence +
                        ": \"" + response.Results[i].DisplayText + "\"\n");
            }

            this.WriteLine();
        }
    }

    /**
     * Called when a final response is received and its intent is parsed
     * 在收到响应并解析其意图时调用
     */
    public void onIntentReceived(final String payload) {
        this.WriteLine("--- Intent received by onIntentReceived() ---");
        this.WriteLine(payload);
        this.WriteLine();
    }

    public void onPartialResponseReceived(final String response) {
        this.WriteLine("Partial result:");
        this.WriteLine(response+"\n");
        //this.WriteLine();

//        if (fileName.equals("english2")||fileName.equals("gemany1")||fileName.equals("gemany2")){
//            this.WriteLine(response);
//        }
    }

    public void onError(final int errorCode, final String response) {
        this._startDataFileRecogButton.setEnabled(true);
        this._displayDFR.setText("");
//        this.WriteLine("--- Error received by onError() ---");
//        this.WriteLine("Error code: " + SpeechClientStatus.fromInt(errorCode) + " " + errorCode);
//        this.WriteLine("Error text: " + response);

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


    /**
     * Called when the microphone status has changed.
     * 麦克风的状态发生改变的时候调用
     * @param recording The current recording state
     */
    public void onAudioEvent(boolean recording) {
//        this.WriteLine("--- Microphone status change received by onAudioEvent() ---");
//
//        this.WriteLine("********* Microphone status: " + recording + " *********");
//        if (recording) {
//            this.WriteLine("Please start speaking.");
//        }
//
//        WriteLine();
//        if (!recording) {
//            this.micClient.endMicAndRecognition();
//            this._startRecogButton.setEnabled(true);
//        }
    }

    private void SendAudioHelper(String filename) {
        DataFileRecogniton.RecognitionTask doDataReco = new RecognitionTask(this.dataClient, this.getMode(), filename);
        try
        {
            //get设置等待计算时长
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
                // Note for wave files, we can just send data from the file right to the server.
                // In the case you are not an audio file in wave format, and instead you have just
                // raw data (for example audio coming over bluetooth), then before sending up any
                // audio data, you must first send up an SpeechAudioFormat descriptor to describe
                // the layout and format of your raw audio data via DataRecognitionClient's sendAudioFormat() method.
                System.out.println(filename);
                InputStream fileStream = getAssets().open(filename);
                int bytesRead = 0;
                byte[] buffer = new byte[1024];

                do {
                    // Get  Audio data to send into byte buffer.
                    bytesRead = fileStream.read(buffer);

                    if (bytesRead > -1) {
                        // Send of audio data to service.
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
