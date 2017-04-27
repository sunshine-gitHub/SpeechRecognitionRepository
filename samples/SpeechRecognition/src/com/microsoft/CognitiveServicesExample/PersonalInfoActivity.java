package com.microsoft.CognitiveServicesExample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.ecnu.entity.User;

public class PersonalInfoActivity extends TitleActivity {
    TextView username;
    TextView userage;
    TextView usersex;
    TextView useraddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        setTitle(R.string.pi_title);

        User user = (User)this.getApplication();
        username = (TextView)findViewById(R.id.username);
        userage = (TextView)findViewById(R.id.userage);
        usersex = (TextView)findViewById(R.id.usersex);
        useraddress = (TextView)findViewById(R.id.useraddress);

        //String uname = getIntent().getStringExtra("name");
        username.setText(user.getUsername());
        int uage = getIntent().getIntExtra("age",20);
        userage.setText(user.getAge()+"");
        //int usex = getIntent().getIntExtra("sex",1);
        if (user.getSex() == 1){
            usersex.setBackgroundResource(R.drawable.sex_male);
        }
        else{
            usersex.setBackgroundResource(R.drawable.sex_female);
        }

        //String uaddress = getIntent().getStringExtra("address");
        useraddress.setText(user.getAddress());

    }



}
