package com.microsoft.CognitiveServicesExample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ecnu.dao.UserOperation;
import com.ecnu.entity.User;

public class ManageDatabase extends TitleActivity {

    private UserOperation userOperation = new UserOperation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_database);

        setTitle("管理数据库");


    }

    public void saveUsers(View view){
        userOperation.addUsers(this,new User());
    }

}
