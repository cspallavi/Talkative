package com.example.pallavi.chat_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
//import com.squareup.picasso.Callback;

public class Chat extends AppCompatActivity {
    CircleImageView imageView1;String image1;
Toolbar t;int sessionid;String data;String s1;JSONObject jo;int cid, senderid;String message,view_sender_name;okhttp3.ResponseBody s0;JSONArray array;
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // Log.v("Pallavi","entered chat");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_main);
       t=(Toolbar)findViewById(R.id.tb);

       // imageView1 = (CircleImageView) findViewById(R.id.imageView);
        setSupportActionBar(t);
        //Chat.this.setTitle("Title");
        getSupportActionBar().setTitle("Talkative");
        //t.setLogo(R.drawable.talkative33);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //imageView = (ImageView) findViewById(R.id.imageView);
        //Picasso.with(MainActivity.this).load(R.drawable.talkative).into(imageView);
        mAdapter = new MoviesAdapter(movieList,Chat.this);
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

// set the adapter
        recyclerView.setAdapter(mAdapter);
        prepareMovieData();
     /*   recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(Chat.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(Chat.this,"HIIIIIIIIIIIIIII"+senderid,Toast.LENGTH_LONG).show();
                        //Intent intent1 = new Intent("com.example.pallavi.chat_app.PrivateChats");
                        // intent.putExtra("SESSIONID", sessionid);
                       // Chat.this.startActivity(intent1);
                        // do whatever
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );*/

        //recyclerView.setAdapter(mAdapter);


    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.overflow_menu,menu);
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
            SharedPreferences spr=PreferenceManager.getDefaultSharedPreferences(Chat.this);
            SharedPreferences.Editor ed=spr.edit();
            ed.remove("sessionid").commit();
            Intent in=new Intent(Chat.this,MainActivity.class);
            startActivity(in);
        }
        if(item.getItemId()==myProfile)
        {

            Intent in=new Intent("com.example.pallavi.chat_app.SetMyProfile");
            startActivity(in);
        }
        return true;
    }

    private void prepareMovieData() {


       SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(Chat.this);
        sessionid=sp.getInt("sessionid",-1);
        //sessionid=1;
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                Log.v("PALLAVIIIIII","ENTERED NEW THREAD");
                data="{\"sessionid\":"+sessionid+"}";
                Log.v("THE REQUESTED DATA IS :",data);
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url("http://www.palzone.ml/service_pallavi/load_conversation_list.php")
                        .post(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"),data))
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Chat.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Chat.this,"CLIENT REQUEST NOT SENT ",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call,final Response response) throws IOException {
                        Log.v("PALLAVI", "ENTERED ON RESPONSE");
                        try {
                             s0=response.body();
                            s1 = response.body().string();
                        } catch (Exception e2) {
                            e2.printStackTrace();

                        }
                        Log.v("THE JSON OBJ IS CREATED", s1);

                        final String s2 = s1.substring(s1.indexOf('[') + 1, s1.length() - 1);


                        try {
                            jo = new JSONObject(s2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Chat.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                   array=new JSONArray(s1);
                                    for(int i=0;i<array.length();i++) {
                                    jo=array.getJSONObject(i);
                                        cid = Integer.parseInt(String.valueOf(jo.getInt("cid")));
                                        senderid=Integer.parseInt(String.valueOf(jo.getInt("senderid")));
                                        message=String.valueOf(jo.getString("message"));
                                        view_sender_name=String.valueOf(jo.getString("view_sender_name"));
                                        image1=String.valueOf(jo.getString("image_path"));
                                        //Picasso.with(Chat.this).load(R.drawable.image).into(imageView1);
                                        // Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015","http://api.androidhive.info/music/images/adele.png");
                                        //movieList.add(movie);
                                        Movie movie=new Movie(view_sender_name,message,senderid,image1);
                                        movieList.add(movie);
                                       // Log.v("PALLAVIIIIII","id"+senderid);

                                    }
                                    mAdapter.notifyDataSetChanged();

                                }
                                catch (JSONException e1) {
                                    e1.printStackTrace();


                                }
                            }
                        });


                    }
                });

            }
        });
        t.start();

    }



}
