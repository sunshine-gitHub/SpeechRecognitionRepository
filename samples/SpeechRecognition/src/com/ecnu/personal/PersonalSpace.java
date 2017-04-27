package com.ecnu.personal;

import android.content.Intent;

import com.microsoft.CognitiveServicesExample.DataFileRecogniton;

/**
 * Created by CY on 2017/4/15.
 */

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
