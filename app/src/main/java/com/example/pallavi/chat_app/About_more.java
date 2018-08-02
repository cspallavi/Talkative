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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.pallavi.chat_app.R.id.friendList;
import static com.example.pallavi.chat_app.R.id.friendRequests;
import static com.example.pallavi.chat_app.R.id.friends1;
import static com.example.pallavi.chat_app.R.id.logout;
import static com.example.pallavi.chat_app.R.id.myProfile;
import static com.example.pallavi.chat_app.R.id.recentChats;
import static com.example.pallavi.chat_app.R.id.registeredUsers;
import static com.example.pallavi.chat_app.R.id.passwordChange;


/**
 * Created by Pallavi on 17/07/2017.
 */

public class About_more extends AppCompatActivity {
    CircleImageView profile_photo;JSONObject jo;String n1,n2,n3,n6,n7,n8,n9,n10,n11,n12,n13;int n4;int response1,n0;String n5;File f;
    TextView friend_status,friend_status1,studied_in1,workplace1,userid1,marital_status1,qualify1,age1,name1,friends,status1,lives_in1,gender1;Button start_chat;Toolbar t;int sessionid;String s;
    String data;String s1,s2;SharedPreferences sp;int Response;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_more);

        t=(Toolbar)findViewById(R.id.tb);

        setSupportActionBar(t);

        start_chat=(Button)findViewById(R.id.start_chat);
        friend_status=(TextView)findViewById(R.id.friend_status);
        friend_status1=(TextView)findViewById(R.id.friend_status1);
        get_details();
        start_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("PaLLAVIIIII","INSIDE ABOUT MORE WHEN CLICKED START_CHAT");
                //Intent intent=new Intent("com.example.pallavi.chat_app.PrivateChats");
                Intent intent=new Intent(v.getContext(),PrivateChats.class);
                Log.v("PALLAVIIII","INSIDE START_CHAT SENDERID="+s);
                intent.putExtra("senderid",s);
               v.getContext().startActivity(intent);
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inf=getMenuInflater();
        inf.inflate(R.menu.overflow_menu,menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
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
            SharedPreferences spr=PreferenceManager.getDefaultSharedPreferences(About_more.this);
            SharedPreferences.Editor ed=spr.edit();
            ed.remove("sessionid").commit();
            Intent in=new Intent(About_more.this,MainActivity.class);
            startActivity(in);
        }
        if(item.getItemId()==myProfile)
        {

            Intent in=new Intent("com.example.pallavi.chat_app.SetMyProfile");
            startActivity(in);
        }
        return true;
    }
    public void get_details()
    {
        sp= PreferenceManager.getDefaultSharedPreferences(About_more.this);
        sessionid=sp.getInt("sessionid",-1);
        s=getIntent().getStringExtra("userid");
        Response=getIntent().getIntExtra("Response",0);
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                Log.v("PALLAVIIII","ENTERED NEW THREAD About more"+ Response);
                if(Response==2)
                {
                    About_more.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            friend_status.setVisibility(View.GONE);
                            friend_status1.setVisibility(View.GONE);
                            //start_chat.setEnabled(false);
                            start_chat.setVisibility(View.GONE);
                        }
                    });

                }
                data="{\"userid\":"+s+"}";
                Log.v("PALLAVIIIII","THE DATA SENT IS "+data);
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url("http://www.palzone.ml/service_pallavi/about_user.php")
                        .post(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"),data))
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        About_more.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(About_more.this,"client request not sent",Toast.LENGTH_LONG);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.v("PALLAVIIII   ","ENTERED RESPONSE");
                        try{
                            s1=response.body().string();
                            s2=s1.substring(s1.indexOf('[')+1,s1.length()-1);

                        }
                        catch (Exception e1)
                        {
                            e1.printStackTrace();
                        }
                        Log.v("YOUR RESPONSE IS",s2);

                           About_more.this.runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   try
                                   {
                                       jo=new JSONObject(s2);
                                       n0=Integer.parseInt(String.valueOf(jo.getInt("userid")));

                                       n1=String.valueOf(jo.getString("username"));

                                       n2=String.valueOf(jo.getString("status"));

                                       n3=String.valueOf(jo.getString("gender"));

                                       n4=Integer.parseInt(String.valueOf(jo.getInt("no_of_friends")));

                                       n5=String.valueOf(jo.getString("profile_pic"));



                                       n6=String.valueOf(jo.getString("lives_in"));

                                       n7=String.valueOf(jo.getString("studied_in"));

                                       n8=String.valueOf(jo.getString("gender"));

                                       n9=String.valueOf(jo.getString("age"));

                                       n10=String.valueOf(jo.getString("qualification"));

                                       n11=String.valueOf(jo.getString("workplace"));

                                       n12=String.valueOf(jo.getString("marital_status"));

                                       n13=String.valueOf(jo.getString("no_of_friends"));

                                       name1=(TextView) findViewById(R.id.name1);
                                   profile_photo=(CircleImageView)findViewById(R.id.user_profile_photo);
                                   friends= (TextView) findViewById(friends1);
                                   status1=(TextView) findViewById(R.id.status1);
                                   gender1=(TextView) findViewById(R.id.gender1);
                                   start_chat=(Button)findViewById(R.id.start_chat);
                                   lives_in1=(TextView) findViewById(R.id.lives_in1);
                                   studied_in1=(TextView) findViewById(R.id.studied_in1);
                                   workplace1=(TextView) findViewById(R.id.workplace1);
                                   marital_status1=(TextView) findViewById(R.id.marital_status1);
                                   qualify1=(TextView) findViewById(R.id.qualify1);
                                   age1=(TextView) findViewById(R.id.age1);
                                   userid1=(TextView)findViewById(R.id.userid1);

                                   userid1.setText(n0+"");
                                   name1.setText(n1);
                                       Log.v("PALLLAVVIIIII","NAME SET="+name1.getText());
                                   status1.setText(n2);
                                   gender1.setText(n3);
                                   friends.setText(n4+"");
                                   String path="http://www.palzone.ml/service_pallavi";
                                   f=new File(n5);
                                   Picasso.with(About_more.this).load(path+f).into(profile_photo);
                                   lives_in1.setText(n3);
                                   studied_in1.setText(n7);
                                   gender1.setText(n8);
                                   age1.setText(n9+"");
                                   qualify1.setText(n10);
                                   workplace1.setText(n11);
                                   marital_status1.setText(n12);
                                   friends.setText(n13+"");
                                   }
                                    catch (JSONException je)
                                   {
                                       je.printStackTrace();
                                   }
                               }

                           });






                    }
                });

            }
        });
        th.start();

    }
}
