package com.microsoft.CognitiveServicesExample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecnu.dao.UserOperation;
import com.ecnu.entity.User;

public class PersonalSpaceActivity extends TitleActivity {

    private UserOperation userOperation;

    private TextView pssex;
    private TextView psaddress;
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_space);
        setTitle(R.string.ps_title);

        User user = (User)this.getApplication();
        this.pssex = (TextView)findViewById(R.id.pssex);
        this.psaddress = (TextView)findViewById(R.id.psaddress);
        this.imageView = (ImageView)findViewById(R.id.imageView);
        String str = user.getPhoto();
        if (str.equals("cy")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cy));
        }
        else if (str.equals("ljy")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ljy));
        }
        else if (str.equals("zk")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.zk));
        }
        else if (str.equals("dsy")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dsy));
        }
        else{
            if (user.getSex() == 1){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_male));
            }
            else{
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_female));
            }

        }

        //String uname = getIntent().getStringExtra("name");
        //this.pssex.setText(uname);
        if (user.getSex() == 1){
            pssex.setBackgroundResource(R.drawable.sex_male);
        }
        else{
            pssex.setBackgroundResource(R.drawable.sex_female);
        }
       // String uadd = getIntent().getStringExtra("address");
        //this.psaddress.setText(uadd);
        this.psaddress.setText(user.getAddress());

    }

    public void getPersonalInfo(View view){
        userOperation = new UserOperation();
       // String id = "1";
        //User user = userOperation.queryUser(this,id);
        Intent intent = new Intent();
        intent.setClass(this,PersonalInfoActivity.class);
//        intent.putExtra("name",user.getUsername());
//        intent.putExtra("age",user.getAge());
//        intent.putExtra("sex",user.getSex());
//        intent.putExtra("address",user.getAddress());
        this.startActivity(intent);
    }

    public void alterPassword(View view){

        Intent intent = new Intent();
        intent.setClass(this,ChangePassword.class);


        this.startActivity(intent);
    }
}
