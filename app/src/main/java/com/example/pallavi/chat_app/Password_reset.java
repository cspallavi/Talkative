package com.example.pallavi.chat_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.pallavi.chat_app.R.id.friendList;
import static com.example.pallavi.chat_app.R.id.friendRequests;
import static com.example.pallavi.chat_app.R.id.logout;
import static com.example.pallavi.chat_app.R.id.myProfile;
import static com.example.pallavi.chat_app.R.id.passwordChange;
import static com.example.pallavi.chat_app.R.id.recentChats;
import static com.example.pallavi.chat_app.R.id.registeredUsers;

/**
 * Created by Pallavi on 25/07/2017.
 */

public class Password_reset extends AppCompatActivity {
    Toolbar t;EditText pass,pass1,pass2;String p,p1,p2;Button save;int sessionid;String data;String s1,s2;JSONObject jo;int response1;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset);
        t=(Toolbar)findViewById(R.id.tb);
        setSupportActionBar(t);
        getSupportActionBar();
        pass=(EditText)findViewById(R.id.password);
        pass1=(EditText)findViewById(R.id.password1);
        pass2=(EditText)findViewById(R.id.password2);
        save=(Button)findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_pass();
            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater in=getMenuInflater();
        in.inflate(R.menu.overflow_menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==friendRequests)
        {
            Intent intent=new Intent("com.example.pallavi.chat_app.FriendRequests");
            startActivity(intent);
        }
        if(item.getItemId()==friendList)
        {
            Intent intent=new Intent("com.example.pallavi.chat_app.FriendList");
            startActivity(intent);
        }
        if(item.getItemId()==recentChats)
        {
            Intent intent=new Intent("com.example.pallavi.chat_app.Chat");
            startActivity(intent);
        }
        if(item.getItemId()==registeredUsers)
        {
            Intent intent=new Intent("com.example.pallavi.chat_app.Registered_users_list");
            startActivity(intent);
        }
        if(item.getItemId()==passwordChange)
        {
            Intent intent=new Intent("com.example.pallavi.chat_app.Password_reset");
            startActivity(intent);
        }
        if(item.getItemId()==logout)
        {
            SharedPreferences spr=PreferenceManager.getDefaultSharedPreferences(Password_reset.this);
            SharedPreferences.Editor ed=spr.edit();
            ed.remove("sessionid").commit();
            Intent in=new Intent(Password_reset.this,MainActivity.class);
            startActivity(in);
        }
        if(item.getItemId()==myProfile)
        {

            Intent in=new Intent("com.example.pallavi.chat_app.SetMyProfile");
            startActivity(in);
        }
        return true;
    }
    void reset_pass()
    {
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(Password_reset.this);
        sessionid=sp.getInt("sessionid",-1);
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                Log.v("PALLAVIIIII", "INSIDE THREAD OF PASSWORD CHANGE");
                p = pass.getText().toString();
                p1 = pass1.getText().toString();
                p2 = pass2.getText().toString();

                    data = "{\"sessionid\":" + sessionid + ",\"old_password\":" + "\"" + p + "\"" + ",\"new_password\":" + "\"" + p1 + "\"" + ",\"cnew_password\":" + "\"" + p2 + "\"" + "}";
                Log.v("PALLAVIII", "THE DATA SENT IS" + data);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://www.palzone.ml/service_pallavi/password_change.php")
                        .post(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), data))
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.v("PALLAVIII", "CLIENT REQUEST NOT SENT");
                        Password_reset.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Password_reset.this, "Client request not sent", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            s1 = response.body().string();
                            s2 = s1.substring(s1.indexOf('[') + 1, s1.length() - 1);
                            Log.v("THE RESPONSE IS", s2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        try {
                            jo = new JSONObject(s2);
                            response1 = Integer.parseInt(String.valueOf(jo.getInt("response")));
                            Password_reset.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(response1==5)
                                    {
                                        Log.v("PALLAVIIII",""+response1);
                                        Toast.makeText(Password_reset.this, "Please fill all the fields!!", Toast.LENGTH_LONG).show();
                                    }

                                    if (response1 == 4) {
                                        Toast.makeText(Password_reset.this, "New password and confirm new password mismatch", Toast.LENGTH_LONG).show();
                                    }
                                    if (response1 == 3) {
                                        Toast.makeText(Password_reset.this, "Entered wrong old password", Toast.LENGTH_LONG).show();
                                    }
                                    if (response1 == 2) {
                                        Toast.makeText(Password_reset.this, "New password and old Password same", Toast.LENGTH_LONG).show();
                                    }
                                    if (response1 == 1) {
                                        MaterialDialog m = new MaterialDialog.Builder(Password_reset.this)
                                                .content("Password changed successfully!!!!")
                                                .positiveText("OK")
                                                .show();

                                    }

                                }
                            });

                        } catch (JSONException je) {
                            je.printStackTrace();
                        }

                    }
                });

            }

        });
        th.start();
    }

}
