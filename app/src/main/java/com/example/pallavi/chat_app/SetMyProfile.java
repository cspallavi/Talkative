package com.example.pallavi.chat_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.RadioButton;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Pallavi on 24/07/2017.
 */

public class SetMyProfile extends AppCompatActivity {
    Toolbar t;CircleImageView ci;EditText Name1,status1,lives_in1,studied_in1,workplace1,marital_status1,friends1,qualify1,age1;RadioButton female,male,other;
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_my_profile);
        t=(Toolbar)findViewById(R.id.tb);


        setSupportActionBar(t);

    }
}
