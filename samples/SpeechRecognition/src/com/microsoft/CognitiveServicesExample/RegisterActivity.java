package com.microsoft.CognitiveServicesExample;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.ecnu.dao.UserOperation;
import com.ecnu.entity.User;
public class RegisterActivity extends TitleActivity {
    private UserOperation userOperation = new UserOperation();
    private Button commit;
    private EditText name;
    private EditText pwd;
    private EditText conpwd;
    private EditText age;
    private RadioGroup sex;
    private EditText address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(R.string.reg_title);
        this.commit = (Button)findViewById(R.id.regiscommit);
        this.pwd = (EditText)findViewById(R.id.pwd);
        this.conpwd = (EditText)findViewById(R.id.conpwd);
        this.age = (EditText)findViewById(R.id.age);
        this.name = (EditText)findViewById(R.id.name);
        this.address = (EditText)findViewById(R.id.adress);
        this.sex = (RadioGroup) findViewById(R.id.sex);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pwd.getText().toString().equals(conpwd.getText().toString())){
                    Drawable right=getResources().getDrawable(R.drawable.error);
                    right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
                    pwd.setCompoundDrawables(null, null, right, null);
                }
                else{
                    int se = 0;
                    int id = RegisterActivity.this.sex.getCheckedRadioButtonId();
                    if (id == R.id.nan) {
                        se = 1;
                    }
                    User user = new User();
                    user.setUsername(name.getText().toString());
                    user.setPassword(pwd.getText().toString());
                    if (age.getText().toString().equals("")){
                        user.setAge(0);
                    }
                    else{
                        user.setAge(Integer.parseInt(age.getText().toString()));
                    }
                    user.setAddress(address.getText().toString());
                    user.setPhoto(name.getText().toString());
                    user.setSex(se);
                    boolean result = userOperation.register(RegisterActivity.this,user);
                    if (result){
                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Drawable right=getResources().getDrawable(R.drawable.error);
                        right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
                        name.setCompoundDrawables(null, null, right, null);
                    }
                }
            }
        });
    }
}
