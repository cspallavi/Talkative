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
import static com.example.pallavi.chat_app.R.id.logout;
import static com.example.pallavi.chat_app.R.id.myProfile;
import static com.example.pallavi.chat_app.R.id.recentChats;
import static com.example.pallavi.chat_app.R.id.registeredUsers;
import static com.example.pallavi.chat_app.R.id.passwordChange;

/**
 * Created by Pallavi on 17/07/2017.
 */

public class About_less extends AppCompatActivity {
    CircleImageView profile_photo;JSONObject jo;String n1,n2,n3;int n4;int response1;String n5;File f;int Response;
    TextView friend_status1,name1,message,status,friends,status1,lives_in1,gender,gender1;Button send_request,accept_deny;Toolbar tb;int sessionid;String s;
    String data;String s1,s2;SharedPreferences sp;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_less);
        tb=(Toolbar)findViewById(R.id.tb);

        setSupportActionBar(tb);

        getSupportActionBar();
        name1=(TextView) findViewById(R.id.name1);
        profile_photo=(CircleImageView)findViewById(R.id.user_profile_photo);
        friends= (TextView) findViewById(R.id.friends1);
        status1=(TextView) findViewById(R.id.status1);
        gender1=(TextView) findViewById(R.id.gender1);
        send_request=(Button)findViewById(R.id.send_request);
        message=(TextView)findViewById(R.id.message);
        friend_status1=(TextView)findViewById(R.id.friend_status1);
        accept_deny=(Button)findViewById(R.id.accept_deny);

        get_details();
        send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(About_less.this, "send_request clicked", Toast.LENGTH_SHORT).show();
                // sp=PreferenceManager.getDefaultSharedPreferences(About_less.this);
                //sessionid=sp.getInt("sessionid",-1);
               // s=getIntent().getStringExtra("userid");
                Thread th1=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data="{\"sessionid\":"+sessionid+",\"friend_request_sent_to\":"+s+"}";
                        Log.v("DATA SENT IN REQUEST",data);
                        OkHttpClient client=new OkHttpClient();
                        Request request=new Request.Builder()
                                .url("http://www.palzone.ml/service_pallavi/friend_request_send.php")
                                .post(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"),data))
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                About_less.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(About_less.this,"Client request not sent",Toast.LENGTH_LONG).show();
                                        Intent intent=new Intent("com.example.pallavi.chat_app.FriendList");
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Log.v("PALLLAVIIIII","ENTERED RESPONSE of Friend request sent");
                                try{
                                s1=response.body().string();
                                s2=s1.substring(s1.indexOf('[')+1,s1.length()-1);
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }

                                try{
                                    jo=new JSONObject(s2);
                                    response1=jo.getInt("response");
                                    //int sessionid1=jo.getInt("sessionid");
                                    int friend_request_sent_to1=jo.getInt("friend_request_sent_to");
                                    Log.v("PALLAVIIII","RESPONSE IS="+response1+" "+friend_request_sent_to1);
                                    if(response1==1)
                                    {
                                        About_less.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(About_less.this,"Friend Request sent",Toast.LENGTH_LONG).show();
                                                friend_status1.setText("friend request sent");
                                                send_request.setVisibility(View.GONE);
                                                send_request.setEnabled(false);
                                            }
                                        });

                                    }

                                }
                                catch (Exception je)
                                {
                                    je.printStackTrace();
                                }
                            }
                        });

                    }
                });
                th1.start();

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
            SharedPreferences spr=PreferenceManager.getDefaultSharedPreferences(About_less.this);
            SharedPreferences.Editor ed=spr.edit();
            ed.remove("sessionid").commit();
            Intent in=new Intent(About_less.this,MainActivity.class);
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
         sp= PreferenceManager.getDefaultSharedPreferences(About_less.this);
        sessionid=sp.getInt("sessionid",-1);
        s=getIntent().getStringExtra("userid");
        Response=getIntent().getIntExtra("Response",0);
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                Log.v("PALLAVIIII","ENTERED NEW THREAD ABOUT LESS");
                About_less.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(Response==3)
                        {
                            message.setVisibility(View.GONE);
                            send_request.setVisibility(View.GONE);
                            friend_status1.setText("Friend Request sent");
                        }
                        if(Response==4)
                        {
                            message.setText("You have been sent friend request by this user,click on accept/deny button to reach friend request page.");

                            send_request.setVisibility(View.GONE);
                            accept_deny.setVisibility(View.VISIBLE);
                            accept_deny.setEnabled(true);
                            accept_deny.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i=new Intent("com.example.pallavi.chat_app.FriendRequests");

                                    startActivity(i);
                                }
                            });



                        }
                    }
                });
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
                        About_less.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(About_less.this,"client request not sent",Toast.LENGTH_LONG);
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

                            About_less.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        jo = new JSONObject(s2);

                                        n1 = String.valueOf(jo.getString("username"));
                                        name1.setText(n1);
                                        n2 = String.valueOf(jo.getString("status"));
                                        status1.setText(n2);
                                        n3 = String.valueOf(jo.getString("gender"));
                                        gender1.setText(n3);
                                        n4 = Integer.parseInt(String.valueOf(jo.getInt("no_of_friends")));
                                        friends.setText(n4+"");
                                        n5 = String.valueOf(jo.getString("profile_pic"));
                                        String path = "http://www.palzone.ml/service_pallavi";
                                        f = new File(n5);
                                        Picasso.with(About_less.this).load(path + f).into(profile_photo);
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
