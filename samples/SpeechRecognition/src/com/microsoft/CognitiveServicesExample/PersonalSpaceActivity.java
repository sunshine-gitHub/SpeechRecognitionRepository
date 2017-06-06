package com.microsoft.CognitiveServicesExample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
        if (user.getSex() == 1){
            pssex.setBackgroundResource(R.drawable.sex_male);
        }
        else{
            pssex.setBackgroundResource(R.drawable.sex_female);
        }
        this.psaddress.setText(user.getAddress());
    }
    public void getPersonalInfo(View view){
        userOperation = new UserOperation();
        Intent intent = new Intent();
        intent.setClass(this,PersonalInfoActivity.class);
        this.startActivity(intent);
    }
    public void privateSettings(View view){
        userOperation = new UserOperation();
        Intent intent = new Intent();
        intent.setClass(this,PrivateSettingActivity.class);
        this.startActivity(intent);
    }
    public void alterPassword(View view){
        Intent intent = new Intent();
        intent.setClass(this,ChangePassword.class);
        this.startActivity(intent);
    }
}
