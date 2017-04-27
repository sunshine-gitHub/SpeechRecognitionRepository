package com.microsoft.CognitiveServicesExample;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ecnu.dao.UserOperation;
import com.ecnu.entity.User;

public class ChangePassword extends TitleActivity {

    private UserOperation userOperation = new UserOperation();
    private Button alterPwd;
    private EditText oldPwd;
    private EditText newPwd;
    private EditText confirmPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle(R.string.cp_title);

        final ChangePassword This = this;
        this.alterPwd = (Button)findViewById(R.id.alterPwd);
        this.oldPwd = (EditText)findViewById(R.id.oldPwd);
        this.newPwd = (EditText)findViewById(R.id.newPwd);
        this.confirmPwd = (EditText)findViewById(R.id.confirmPwd);

        this.alterPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                This.changePwd(v);
            }
        });
    }





    public void changePwd(View view){
        User user = (User)this.getApplication();

        if (!oldPwd.getText().toString().equals(user.getPassword())){
            Drawable right=getResources().getDrawable(R.drawable.error);
            right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
            oldPwd.setCompoundDrawables(null, null, right, null);
        }
        else if (!newPwd.getText().toString().equals(confirmPwd.getText().toString())){
            Drawable right=getResources().getDrawable(R.drawable.error);
            right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
            newPwd.setCompoundDrawables(null, null, right, null);
        }
        else {
            String newPassword = newPwd.getText().toString();
            String id = user.getId().toString();

            System.out.println(newPassword+"  "+id);
            boolean result = userOperation.changePwd(this,newPwd.getText().toString(),user.getId().toString());
            if (result){
                Intent intent = new Intent();
                intent.setClass(this,LoginActivity.class);
                this.startActivity(intent);
            }

        }
    }
}
