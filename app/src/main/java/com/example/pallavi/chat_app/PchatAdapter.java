package com.example.pallavi.chat_app;

/**
 * Created by Pallavi on 29/06/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.pallavi.chat_app.R.id.imageView2;

public class PchatAdapter extends RecyclerView.Adapter<PchatAdapter.MyViewHolder> {
    ImageView  imageView1,imageView3,imageView4;int sessionid;File f;Bitmap bmp;CircleImageView imageview2;LinearLayoutManager l;
    private List<Get_set_pchat> pchatList;
Context context;Activity main;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username,message,textView2;ImageView imageview2;
        //RelativeLayout l;
        //SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(PchatAdapter.this);
        //sessionid=sp.getInt("sessionid",-1);
        public MyViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.username);
            message = (TextView) view.findViewById(R.id.message);
            textView2 = (TextView) view.findViewById(R.id.textView2);
            imageView1 = (ImageView) view.findViewById(R.id.imageView1);
            imageview2 = (CircleImageView) view.findViewById(imageView2);
            imageView3 = (ImageView) view.findViewById(R.id.imageView3);
            imageView4 = (ImageView) view.findViewById(R.id.imageView4);

            // l=(RelativeLayout)view.findViewById(R.id.list);
        }



    }
  /* public void scroll_to_bottom(RecyclerView recyclerView,LinearLayoutManager lm)
    {
        recyclerView.setLayoutManager(lm);
        lm.setStackFromEnd(true);
        Log.v("inside scroll",":::::::::success");

        l.scrollToPosition(pchatList.size()-1);
    }*/
    public void addelement(Get_set_pchat pchatobj){
        pchatList.add(0,pchatobj);
        notifyItemInserted(0);
    }

    public PchatAdapter(List<Get_set_pchat> pchatList,Activity main) {
        this.pchatList = pchatList;
        this.main=main;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout1_pchat, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Get_set_pchat pchat = pchatList.get(position);

        holder.username.setText(pchat.getusername());
        holder.message.setText(pchat.getmessage());
        Log.v("PALLAVIIIIIIIIIIIIIIIII",pchat.getusername());
        String path="http://www.palzone.ml/service_pallavi";
        f=new File(pchat.getImage());
        /*try {
            FileOutputStream out = new FileOutputStream(path+f);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); //100-best quality
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        Log.v("Pallaviiiiiiiiiiiiiiiii",path+""+f);

        Picasso.with(main).load(path+f).into(holder.imageview2);
       }
       // holder.year.setText(movie.getYear());



    @Override
    public int getItemCount() {
        return pchatList.size();
    }

}
