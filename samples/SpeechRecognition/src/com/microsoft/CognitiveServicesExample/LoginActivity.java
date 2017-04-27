package com.microsoft.CognitiveServicesExample;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecnu.dao.UserOperation;
import com.ecnu.entity.User;
import com.ecnu.view.ClearEditText;




public class LoginActivity extends Activity {

    private static final String TAG = "LoginActivity";
    private Context context = this;
    private ClearEditText mUsernameText, mPasswdText;
    private Button mLoginCfmBtn = null;
    private ImageView loginImg = null;
    private long mExitTime = 0;
    private View mForgetPsdTextVeiw;

    private TextView register;


    private final static int LOGIN_FAIL = 0;


    public void showToast(String text, int longint) {
        Toast.makeText(this, text, longint).show();
    }


    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }



    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN_FAIL:
//				((AnimationDrawable) loginImg.getBackground()).stop(); // 停止登录动画
                    // 登录按钮恢复原状
                    mLoginCfmBtn.setBackgroundResource(R.drawable.green_button_frame);
                    mLoginCfmBtn.setClickable(true);
                    // 提示用户
                    showToast("您的用户名或者密码有误", 0);
                    mUsernameText.setText("");
                    mPasswdText.setText("");
                    // 设置焦点
                    mUsernameText.setFocusable(true);
                    mUsernameText.setFocusableInTouchMode(true);
                    mUsernameText.requestFocus();
                    mUsernameText.requestFocusFromTouch();
                    break;

                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        setTitle(R.string.login);

        final LoginActivity This = this;

        

        mUsernameText = (ClearEditText) findViewById(R.id.login_name_input);
        mPasswdText = (ClearEditText) findViewById(R.id.login_password_input);
        mLoginCfmBtn = (Button) findViewById(R.id.login_cfm_btn);
        loginImg = (ImageView) findViewById(R.id.loading_img);

        register = (TextView) findViewById(R.id.register);

        mLoginCfmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameText.getText().toString().trim();
                String password = mPasswdText.getText().toString().trim();

               System.out.println("username:" + username + "/" + "password:"
                        + password);

                UserOperation userOperation = new UserOperation();
                String result = userOperation.verifyLogin(LoginActivity.this,username,password);

                //System.out.println(result);
                loginImg.setVisibility(View.VISIBLE);
                if (!result.equals("")){
                    User user = (User)This.getApplication();
                    User user1 = userOperation.queryUser(context,result);
                    user.setUsername(user1.getUsername());
                    user.setPassword(user1.getPassword());
                    user.setSex(user1.getSex());
                    user.setAge(user1.getAge());
                    user.setId(user1.getId());
                    user.setPhoto(user1.getPhoto());
                    user.setAddress(user1.getAddress());

                    Intent intent = new Intent();
                    intent.setClass(context, SelectRecogMode.class);
                    context.startActivity(intent);
                }



//                mLoginCfmBtn
//                        .setBackgroundResource(R.drawable.green_button_frame_pressed);
//                mLoginCfmBtn.setClickable(false);
            }
        });

        register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private static boolean isExit = false;

    Handler mmHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mmHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }


    public void notifyLoginFail(){
        mHandler.sendEmptyMessage(LOGIN_FAIL);
    }



}

