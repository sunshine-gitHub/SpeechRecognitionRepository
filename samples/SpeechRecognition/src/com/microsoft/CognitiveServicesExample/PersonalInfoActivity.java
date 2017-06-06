package com.microsoft.CognitiveServicesExample;

import android.os.Bundle;
import android.widget.TextView;
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
        username.setText(user.getUsername());
        int uage = getIntent().getIntExtra("age",20);
        userage.setText(user.getAge()+"");
        if (user.getSex() == 1){
            usersex.setBackgroundResource(R.drawable.sex_male);
        }
        else{
            usersex.setBackgroundResource(R.drawable.sex_female);
        }
        useraddress.setText(user.getAddress());
    }
}
