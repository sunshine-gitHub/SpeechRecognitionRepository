package com.microsoft.CognitiveServicesExample;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.ecnu.dao.UserOperation;
import com.microsoft.cognitiveservices.speechrecognition.ISpeechRecognitionServerEvents;
import com.microsoft.cognitiveservices.speechrecognition.MicrophoneRecognitionClient;
import com.microsoft.cognitiveservices.speechrecognition.RecognitionResult;
import com.microsoft.cognitiveservices.speechrecognition.RecognitionStatus;
import com.microsoft.cognitiveservices.speechrecognition.SpeechRecognitionMode;
import com.microsoft.cognitiveservices.speechrecognition.SpeechRecognitionServiceFactory;
import android.widget.TextView;

public class MicRecognition extends TitleActivity implements ISpeechRecognitionServerEvents
{


    private UserOperation userOperation;

    MicrophoneRecognitionClient micClient = null;
    FinalResponseStatus isReceivedResponse = FinalResponseStatus.NotReceived;
    TextView _displayReponse;

    RadioGroup _groupLanguage;
    RadioGroup _shortOrLong;
    RadioButton _shortPhrase;
    RadioButton _longPhrase;

    RadioGroup radio_group;
    RadioButton rb_about;
    RadioButton rb_me;
    RadioButton rb_home;
    RadioButton rb_help;


    Button _startRecogButton;


    Button _resetButton;

    String language = "en-US";



    public enum FinalResponseStatus { NotReceived, OK, FinalResponseStatus, Timeout }

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
     * Gets a value indicating whether or not to use the microphone.
     * @return true if [use microphone]; otherwise, false.
     */



    /**
     * Gets the current speech recognition mode.
     * @return The speech recognition mode.
     */
    private SpeechRecognitionMode getMode() {
        int id = this._shortOrLong.getCheckedRadioButtonId();
        if (id == R.id.shortPhrase) {
            return SpeechRecognitionMode.ShortPhrase;

        }
        else{
            return SpeechRecognitionMode.LongDictation;
        }
    }

    /**
     * Gets the default locale.
     * @return The default locale.
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


    /**
     * Gets the Cognitive Service Authentication Uri.
     * @return The Cognitive Service Authentication Uri.  Empty if the global default is to be used.
     */
    private String getAuthenticationUri() {
        return this.getString(R.string.authenticationUri);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mic_recognition);

        setTitle(R.string.sp_title);


        this._groupLanguage = (RadioGroup)findViewById(R.id.groupLanguage);
        this._displayReponse = (TextView) findViewById(R.id.displayReponse);

        this._shortPhrase = (RadioButton)findViewById(R.id.shortPhrase);
        this._longPhrase = (RadioButton)findViewById(R.id.longPhrase);
        this._shortOrLong = (RadioGroup)findViewById(R.id.shortOrLong);

        this.radio_group = (RadioGroup)findViewById(R.id.radio_group);
        this.rb_home = (RadioButton)findViewById(R.id.rb_home);
        this.rb_help = (RadioButton)findViewById(R.id.rb_help);
        this.rb_about = (RadioButton)findViewById(R.id.rb_about);
        this.rb_me = (RadioButton)findViewById(R.id.rb_me);


        _displayReponse.setMovementMethod(ScrollingMovementMethod.getInstance());

        this._startRecogButton = (Button) findViewById(R.id.startRecog);
        this._resetButton = (Button) findViewById(R.id.reset);





        if (getString(R.string.primaryKey).startsWith("Please")) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.add_subscription_key_tip_title))
                    .setMessage(getString(R.string.add_subscription_key_tip))
                    .setCancelable(false)
                    .show();
        }


        final MicRecognition This = this;
        //set the Language




        this._startRecogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                This.StartButton_Click(arg0);
            }
        });



        this._resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                This.resetButton_Click(arg0);
            }
        });



        this._shortOrLong.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rGroup, int checkedId) {

                This.RadioButton_Click(rGroup, checkedId);
            }
        });

        this._groupLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
                //关闭了麦克风客户端
                This.RadioButton_Click(rGroup, checkedId);
            }
        });

        this.rb_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MicRecognition.this,SelectRecogMode.class);
                startActivity(intent);
            }
        });
        this.rb_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MicRecognition.this,PersonalSpaceActivity.class);
                startActivity(intent);
            }
        });
        this.rb_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MicRecognition.this,AboutSpecchRecogActivity.class);
                startActivity(intent);
            }
        });
        this.rb_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MicRecognition.this,HelpActivity.class);
                startActivity(intent);
            }
        });



    }


    /**
     * Handles the Click event of the _startButton control.
     */
    private void StartButton_Click(View arg0) {
        this._startRecogButton.setEnabled(false);

        //this.getMode() 返回你选择的语音识别模式（长和短）




        this.LogRecognitionStart();

        //使用麦克风
            if (this.micClient == null) {
//                if (this.getWantIntent()) {
//                    this.WriteLine("--- Start microphone dictation with Intent detection ----");
//                    this.micClient =
//                            SpeechRecognitionServiceFactory.createMicrophoneClientWithIntent(
//                                    this,                               //当前activity
//                                    language,            //语言
//                                    this,
//                                    this.getPrimaryKey(),               //primaryKey
//                                    this.getLuisAppId(),                //获取LUIS ID
//                                    this.getLuisSubscriptionID());      //获取LUIS 订阅ID
//                }
//                else
//                {
                    this.micClient = SpeechRecognitionServiceFactory.createMicrophoneClient(
                            this,
                            this.getMode(),                         //长语音或短语音
                            getLanguage(),                //语言
                            this,
                            this.getPrimaryKey());                  //primaryKey
//                }

                //Cognitive Service Authentication Uri. Empty if the global default is to be used.
                this.micClient.setAuthenticationUri(this.getAuthenticationUri());
            }
            this.micClient.startMicAndRecognition();
    }

    private void resetButton_Click(View arg0){
        if (this.micClient != null) {
            this.micClient.endMicAndRecognition();
            try {
                this.micClient.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            this.micClient = null;
        }
        if (getLanguage().equals("zh-CN")){
            this._displayReponse.setText("语音识别");
        }
        else if (getLanguage().equals("en-US")){
            this._displayReponse.setText("Speech Recognition");
        }
        else{
            this._displayReponse.setText("Spracherkennung Mikrofon");
        }

        this._shortPhrase.setChecked(true);
        this._startRecogButton.setEnabled(true);
    }



    private void DataFileRecogButton_Click(View arg0){
        Intent intent = new Intent();
        intent.setClass(this,DataFileRecogniton.class);

        this.startActivity(intent);
    }



    /**
     * Logs the recognition start.
     */
    private void LogRecognitionStart() {
        String recoSource;
        this.language = getLanguage();
        if (this.getMode() == SpeechRecognitionMode.ShortPhrase) {
            if (language.equals("zh-CN")){
                recoSource = "短对话";
            }
            else if (language.equals("en-US")){
                recoSource = "short Phrase";
            }
            else {
                recoSource = "kurzes Gespräch";
            }
        } else {
            if (language.equals("zh-CN")){
                recoSource = "长对话";
            }
            else if (language.equals("en-US")){
                recoSource = "long Phrase";
            }
            else {
                recoSource = "lange Gespräche";
            }
        }
        this._displayReponse.setText("");
        if (language.equals("zh-CN")){

            this.WriteLine("开始语音识别使用麦克风---" + recoSource+"\n");
        }
        else if (language.equals("en-US")){

            this.WriteLine("Start speech recognition using MicroPhone with"+recoSource+"\n");
        }
        else {

            this.WriteLine("Starten Sie die Spracherkennung Mikrofon---"+recoSource+"\n");
        }
    }



    //最后响应完毕调用
    public void onFinalResponseReceived(final RecognitionResult response) {
        //识别结束或许停顿超时返回true；
        boolean isFinalDicationMessage = this.getMode() == SpeechRecognitionMode.LongDictation &&
                (response.RecognitionStatus == RecognitionStatus.EndOfDictation ||
                        response.RecognitionStatus == RecognitionStatus.DictationEndSilenceTimeout);
        if (null != this.micClient  && ((this.getMode() == SpeechRecognitionMode.ShortPhrase) || isFinalDicationMessage)) {
            // we got the final result, so it we can end the mic reco.  No need to do this
            // for dataReco, since we already called endAudio() on it as soon as we were done
            // sending all the data.
            this.micClient.endMicAndRecognition();
        }

        if (isFinalDicationMessage) {
            this._startRecogButton.setEnabled(true);
            this.isReceivedResponse = FinalResponseStatus.OK;
        }

        if (!isFinalDicationMessage) {
            //this._displayReponse.setText("");
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
            int len = response.Results.length;
            if (len > 2){
                len = 2;
            }

            this.WriteLine(str+"\n");
            for (int i = 0; i < len; i++) {
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
//        this.WriteLine("--- Partial result received by onPartialResponseReceived() ---");
//        this.WriteLine(response);
//        this.WriteLine();
        //this._displayReponse.setText("");
    }

    public void onError(final int errorCode, final String response) {
        this._startRecogButton.setEnabled(true);
        this._displayReponse.setText("");
//        this.WriteLine("--- Error received by onError() ---");
//        this.WriteLine("Error code: " + SpeechClientStatus.fromInt(errorCode) + " " + errorCode);
//        this.WriteLine("Error text: " + response);

        this.language = language;
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

    /**
     * Writes the line.
     */
    private void WriteLine() {
        this.WriteLine("");
    }

    /**
     * Writes the line.
     * @param text The line to write.
     */
    private void WriteLine(String text) {
        this._displayReponse.append(text);
    }

    /**
     * Handles the Click event of the RadioButton control.
     * @param rGroup The radio grouping.
     * @param checkedId The checkedId.
     */
    private void RadioButton_Click(RadioGroup rGroup, int checkedId) {
        // Reset everything
        if (this.micClient != null) {
            this.micClient.endMicAndRecognition();
            try {
                this.micClient.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            this.micClient = null;
        }
        if (getLanguage().equals("zh-CN")){
            this._displayReponse.setText("语音识别");
        }
        else if (getLanguage().equals("en-US")){
            this._displayReponse.setText("Speech Recognition");
        }
        else if (getLanguage().equals("de-DE")){
            this._displayReponse.setText("Spracherkennung Mikrofon");
        }
        this._startRecogButton.setEnabled(true);
    }








}

