package com.example.pallavi.chat_app;

import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
import okhttp3.ResponseBody;

import static com.example.pallavi.chat_app.R.id.friendList;
import static com.example.pallavi.chat_app.R.id.friendRequests;
import static com.example.pallavi.chat_app.R.id.logout;
import static com.example.pallavi.chat_app.R.id.myProfile;
import static com.example.pallavi.chat_app.R.id.recentChats;
import static com.example.pallavi.chat_app.R.id.registeredUsers;
import static com.example.pallavi.chat_app.R.id.passwordChange;
//import com.squareup.picasso.Callback;

public class FriendList extends AppCompatActivity {
    CircleImageView imageView1;String image1;
    Toolbar t;int sessionid;String data;String s1;JSONObject jo;int friendid,no_of_friends;String friend_name,status;
    ResponseBody s0;JSONArray array;
    private List<Friend> friendList1 = new ArrayList<>();
    private RecyclerView recyclerView;
    private FriendsAdapter mAdapter;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Log.v("Pallavi","entered chat");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_main);
        t=(Toolbar)findViewById(R.id.tb);
        // imageView1 = (CircleImageView) findViewById(R.id.imageView);
        setSupportActionBar(t);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //imageView = (ImageView) findViewById(R.id.imageView);
        //Picasso.with(MainActivity.this).load(R.drawable.talkative).into(imageView);
        mAdapter = new FriendsAdapter(friendList1,FriendList.this);
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
            SharedPreferences spr=PreferenceManager.getDefaultSharedPreferences(FriendList.this);
            SharedPreferences.Editor ed=spr.edit();
            ed.remove("sessionid").commit();
            Intent in=new Intent(FriendList.this,MainActivity.class);
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


        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(FriendList.this);
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
                        .url("http://www.palzone.ml/service_pallavi/friend_list.php")
                        .post(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"),data))
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        FriendList.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FriendList.this,"CLIENT REQUEST NOT SENT ",Toast.LENGTH_LONG).show();
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
                        s1.substring(s1.indexOf('[')+1, s1.length() - 1);

                        try {
                            jo = new JSONObject(s2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        FriendList.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    array=new JSONArray(s1);
                                    for(int i=0;i<array.length();i++) {
                                        jo=array.getJSONObject(i);
                                        friendid = Integer.parseInt(String.valueOf(jo.getInt("friendid")));

                                        friend_name=String.valueOf(jo.getString("friend_name"));
                                        no_of_friends=Integer.parseInt(String.valueOf(jo.getInt("no_of_friends")));
                                        status=String.valueOf(jo.getString("status"));

                                        image1=String.valueOf(jo.getString("image_path"));
                                        //Picasso.with(Chat.this).load(R.drawable.image).into(imageView1);
                                        // Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015","http://api.androidhive.info/music/images/adele.png");
                                        //movieList.add(movie);
                                        Friend friend=new Friend(friendid,friend_name,no_of_friends,status,image1);
                                        friendList1.add(friend);
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

   public class Friend{
       int friendid,no_of_friends;String friend_name,status;String image1;
       public Friend(int friendid,String friend_name,int no_of_friends,String status,String image1){
            this.friendid=friendid;
            this.friend_name=friend_name;
            this.no_of_friends=no_of_friends;
            this.status=status;
            this.image1=image1;

        }

    }
   public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder>  {
        String path;File f;
        private List<Friend> friendList1;
        Activity main1;
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView username, user_status,friendid;
            CircleImageView image1;
            //RelativeLayout l;

            public MyViewHolder(View view) {
                super(view);
                username = (TextView) view.findViewById(R.id.username);
                user_status = (TextView) view.findViewById(R.id.user_status);
                friendid=(TextView)view.findViewById(R.id.friendid);
                image1 = (CircleImageView) view.findViewById(R.id.user_image);

               view.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {

                        int position = getAdapterPosition();
                        Intent intent1 = new Intent(v.getContext(), PrivateChats.class);

                        //intent1.putExtra("username",username.toString());
                        //Log.v("FRIENDID=",+"");
                        intent1.putExtra("senderid", friendid.getText()); //this one on makes app crash
                        v.getContext().startActivity(intent1);

                    }
                });

                // l=(RelativeLayout)view.findViewById(R.id.list);
            }
        }


        public FriendsAdapter(List<Friend> friendList1, Activity main1) {
            this.friendList1 = friendList1;
            this.main1=main1;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_friend_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Friend friend = friendList1.get(position);
            holder.username.setText(friend.friend_name);
            holder.user_status.setText(friend.status);
            holder.friendid.setText(friend.friendid+"");
            path="http://www.palzone.ml/service_pallavi";
            f=new File(friend.image1);
            Log.v("PALLAVIIIIIIIIIIIIIII",path+f);
            Picasso.with(main1).load(path+f).into(holder.image1);


        }

        @Override
       public int getItemCount() {
            return friendList1.size();
        }
  /* class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.listText);
            icon=(ImageView)itemView.findViewById(R.id.listIcon);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(context,"The Item Clicked is: "+getPosition(),Toast.LENGTH_SHORT).show();
        }
    }*/
    }

}
