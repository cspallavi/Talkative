package com.example.pallavi.chat_app;

/**
 * Created by Pallavi on 28/06/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>  {
String path;File f;
    private List<Movie> moviesList;
    Activity main1;
    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView username,user_status,senderid;
        CircleImageView image1;
        //RelativeLayout l;

        public MyViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.username);
            user_status = (TextView) view.findViewById(R.id.user_status);
            senderid=(TextView) view.findViewById(R.id.senderid);
            image1=(CircleImageView)view.findViewById(R.id.user_image);
           view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), PrivateChats.class);

                   // intent.putExtra("username",username.getText().toString());
                    intent.putExtra("senderid", senderid.getText()); //this one on makes app crash
                    v.getContext().startActivity(intent);

                }
            });

            // l=(RelativeLayout)view.findViewById(R.id.list);
        }
    }


    public MoviesAdapter(List<Movie> moviesList, Activity main1) {
        this.moviesList = moviesList;
        this.main1=main1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_chat, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.username.setText(movie.getusername());
        holder.user_status.setText(movie.getuser_status());
        path="http://www.palzone.ml/service_pallavi";
        f=new File(movie.getimage());
        Log.v("PALLAVIIIIIIIIIIIIIII",path+f);
        Picasso.with(main1).load(path+f).into(holder.image1);

        try {
            holder.senderid.setText(movie.getsenderid()+"");

        }
       catch (Exception e)
       {
           Log.v("EXCEPTION ","Exceptionnnnnnnnnn");
          e.printStackTrace();
       }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
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
    };*/
}