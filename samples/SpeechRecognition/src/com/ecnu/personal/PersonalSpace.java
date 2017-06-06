package com.ecnu.personal;

import android.content.Intent;
public class PersonalSpace {
    public Intent getPersonalInfo(String username){
        Intent intent = new Intent();
        if (true){
            intent.putExtra("username","admin");
            intent.putExtra("image","cy");
            return intent;
        }
        else{
            return null;
        }
    }
}
