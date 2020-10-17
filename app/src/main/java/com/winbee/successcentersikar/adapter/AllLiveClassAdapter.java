package com.winbee.successcentersikar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.successcentersikar.LocalData;
import com.winbee.successcentersikar.ProgressBarUtil;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.YoutubePlayer;
import com.winbee.successcentersikar.model.LiveClass;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class AllLiveClassAdapter  extends RecyclerView.Adapter<AllLiveClassAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LiveClass> list;
    OnMenuCicked onMenuCicked;

    public AllLiveClassAdapter(Context context, ArrayList<LiveClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_frutorials,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.progressBarUtil.showProgress();
        Picasso.get().load(list.get(position).getThumbnails())
               // .placeholder(R.drawable.dummyimage)
                .fit().into(holder.youtubeThubnail);
        holder.title.setText(list.get(position).getTopic());
        if (list.get(position).getCS_type_code().equals(1)) {
            holder.image_gif.setVisibility(View.VISIBLE);
                } else if (list.get(position).getCS_type_code().equals(2)) {
            holder.image_gif.setVisibility(View.GONE);
                } else if (list.get(position).getCS_type_code().equals(0)) {
            holder.image_gif.setVisibility(View.GONE);
                }
        holder.progressBarUtil.hideProgress();

        if (list.get(position).getVersion().equalsIgnoreCase("0")){
            holder.img_Unlock.setVisibility(View.VISIBLE);
            holder.img_lock.setVisibility(View.GONE);
        }else if (list.get(position).getVersion().equalsIgnoreCase("1")){
            holder.img_Unlock.setVisibility(View.GONE);
            holder.img_lock.setVisibility(View.VISIBLE);
        }else{
            holder.img_Unlock.setVisibility(View.GONE);
            holder.img_lock.setVisibility(View.GONE);
        }


        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).getCS_type_code().equals(1)||list.get(position).getCS_type_code().equals(2)) {
                    LocalData.ContentLink = list.get(position).getContentLink();
                    LocalData.LiveId = list.get(position).getLiveClassID();
                    LocalData.DocumentId = list.get(position).getLiveClassID();//
                    Intent intent = new Intent(context, YoutubePlayer.class);
                    context.startActivity(intent);
                }else if (list.get(position).getCS_type_code().equals(0)){
                    Toast.makeText(context, "Class Not Started", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private CardView card_view;
        private ImageView youtubeThubnail,img_dots,img_lock,img_Unlock;
        GifImageView image_gif;
        OnMenuCicked onMenuCicked;
        private ProgressBarUtil progressBarUtil;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            youtubeThubnail = itemView.findViewById(R.id.youtubeThubnail);
            img_dots = itemView.findViewById(R.id.img_dots);
            title = itemView.findViewById(R.id.title);
            img_Unlock = itemView.findViewById(R.id.img_Unlock);
            img_lock = itemView.findViewById(R.id.img_lock);
            image_gif = itemView.findViewById(R.id.image_gif);
           // time = itemView.findViewById(R.id.time);
            card_view = itemView.findViewById(R.id.card_view);
            this.onMenuCicked=onMenuCicked;
            progressBarUtil   =  new ProgressBarUtil(context);

            img_dots.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onMenuCicked.onmenuClicked(getAdapterPosition(),view);
        }
    }
    public  interface OnMenuCicked{
        void onmenuClicked(int Position, View view);
    }

}

