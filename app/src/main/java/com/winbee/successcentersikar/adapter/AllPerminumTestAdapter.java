package com.winbee.successcentersikar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.successcentersikar.LocalData;
import com.winbee.successcentersikar.OnlineTestActivity;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.TestSubscriptionActivity;
import com.winbee.successcentersikar.Utils.OnlineTestData;
import com.winbee.successcentersikar.model.SectionDetailsDataModel;


import java.util.List;

public class AllPerminumTestAdapter extends RecyclerView.Adapter<AllPerminumTestAdapter.ViewHolder> {
    private Context context;
    private List<SectionDetailsDataModel> list1;

    public AllPerminumTestAdapter(Context context, List<SectionDetailsDataModel> horizontalList){
        this.context = context;
        this.list1 = horizontalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_popular_courses,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        if (list1.get(position).getIsPremium().equalsIgnoreCase("1")) {
            if (list1.get(position).getIsPaid().equals(0)){
                holder.Course_layout.setVisibility(View.VISIBLE);
                holder.txt_course.setText(list1.get(position).getBucketName());
                Picasso.get().load(list1.get(position).getItemAttachment())
                        .placeholder(R.drawable.dummyimage)
                        .fit().into(holder.course_image);
                holder.txt_type.setText("Test Series");
                holder.layout_coming_soon.setVisibility(View.GONE);
                holder.layout_free.setVisibility(View.GONE);
                holder.layout_buy.setVisibility(View.VISIBLE);
                holder.layout_purchased.setVisibility(View.GONE);
                holder.img_rupee1.setVisibility(View.GONE);
                holder.layout_view.setVisibility(View.GONE);
                holder.txt_actual_price.setVisibility(View.GONE);
                holder.img_rupee.setVisibility(View.GONE);
                holder.txt_discount.setVisibility(View.GONE);

            }else if (list1.get(position).getIsPaid().equals(1)){
                holder.Course_layout.setVisibility(View.VISIBLE);
                holder.txt_course.setText(list1.get(position).getBucketName());
                Picasso.get().load(list1.get(position).getItemAttachment())
                        .placeholder(R.drawable.dummyimage)
                        .fit().into(holder.course_image);
                holder.txt_type.setText("Test Series");
                holder.layout_coming_soon.setVisibility(View.GONE);
                holder.layout_free.setVisibility(View.GONE);
                holder.layout_buy.setVisibility(View.GONE);
                holder.layout_purchased.setVisibility(View.VISIBLE);
                holder.img_rupee1.setVisibility(View.GONE);
                holder.layout_view.setVisibility(View.GONE);
                holder.txt_actual_price.setVisibility(View.GONE);
                holder.img_rupee.setVisibility(View.GONE);
                holder.txt_discount.setVisibility(View.GONE);
                holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OnlineTestData.CoachingID=list1.get(position).getCoachingID();
                        OnlineTestData.bucketID=list1.get(position).getBucketID();
                        OnlineTestData.bucketName=list1.get(position).getBucketName();
                        OnlineTestData.bucketInfo=list1.get(position).getBucketInfo();
                        OnlineTestData.logData=list1.get(position).getLogData();
                        OnlineTestData.status=list1.get(position).getStatus();
                        OnlineTestData.totalTest=list1.get(position).getTotalTest();
                        Intent intent=new Intent(context, OnlineTestActivity.class);
                        context.startActivity(intent);

                    }
                });
            }

        }else if(list1.get(position).getIsPremium().equalsIgnoreCase("0")){
            holder.Course_layout.setVisibility(View.VISIBLE);
            holder.txt_course.setText(list1.get(position).getBucketName());
            Picasso.get().load(list1.get(position).getItemAttachment())
                    .placeholder(R.drawable.dummyimage)
                    .fit().into(holder.course_image);
            holder.txt_type.setText("Test Series");
            holder.layout_coming_soon.setVisibility(View.GONE);
            holder.layout_free.setVisibility(View.VISIBLE);
            holder.layout_purchased.setVisibility(View.GONE);
            holder.layout_buy.setVisibility(View.GONE);
            holder.img_rupee1.setVisibility(View.GONE);
            holder.layout_view.setVisibility(View.GONE);
            holder.txt_actual_price.setVisibility(View.GONE);
            holder.img_rupee.setVisibility(View.GONE);
            holder.txt_discount.setVisibility(View.GONE);
            holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnlineTestData.CoachingID=list1.get(position).getCoachingID();
                    OnlineTestData.bucketID=list1.get(position).getBucketID();
                    OnlineTestData.bucketName=list1.get(position).getBucketName();
                    OnlineTestData.bucketInfo=list1.get(position).getBucketInfo();
                    OnlineTestData.logData=list1.get(position).getLogData();
                    OnlineTestData.status=list1.get(position).getStatus();
                    OnlineTestData.totalTest=list1.get(position).getTotalTest();
                    Intent intent=new Intent(context, OnlineTestActivity.class);
                    context.startActivity(intent);

                }
            });

        }else if(list1.get(position).getIsPaid().equals(1)){
            holder.Course_layout.setVisibility(View.VISIBLE);
            holder.txt_course.setText(list1.get(position).getBucketName());
            Picasso.get().load(list1.get(position).getItemAttachment())
                    .placeholder(R.drawable.dummyimage)
                    .fit().into(holder.course_image);
            holder.txt_type.setText("Test Series");
            holder.layout_coming_soon.setVisibility(View.GONE);
            holder.layout_free.setVisibility(View.GONE);
            holder.layout_buy.setVisibility(View.GONE);
            holder.img_rupee1.setVisibility(View.GONE);
            holder.layout_view.setVisibility(View.GONE);
            holder.txt_actual_price.setVisibility(View.GONE);
            holder.img_rupee.setVisibility(View.GONE);
            holder.txt_discount.setVisibility(View.GONE);
            holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnlineTestData.CoachingID=list1.get(position).getCoachingID();
                    OnlineTestData.bucketID=list1.get(position).getBucketID();
                    OnlineTestData.bucketName=list1.get(position).getBucketName();
                    OnlineTestData.bucketInfo=list1.get(position).getBucketInfo();
                    OnlineTestData.logData=list1.get(position).getLogData();
                    OnlineTestData.status=list1.get(position).getStatus();
                    OnlineTestData.totalTest=list1.get(position).getTotalTest();
                    Intent intent=new Intent(context, OnlineTestActivity.class);
                    context.startActivity(intent);

                }
            });

        }

        if (list1.get(position).getIsPremium().equalsIgnoreCase("1") && list1.get(position).getIsPaid().equals(0)){
            //premium hai but not purchased
            holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.TestBuckedId=list1.get(position).getBucketID();
                    LocalData.TestName=list1.get(position).getBucketName();
                    LocalData.TestDiscription=list1.get(position).getDecription();
                    LocalData.TotalTest=list1.get(position).getTotalTest();
                    Intent intent = new Intent(context, TestSubscriptionActivity.class);
                    context.startActivity(intent);
                }
            });
        }else if (list1.get(position).getIsPremium().equalsIgnoreCase("0") && list1.get(position).getIsPaid().equals(0)){
            //premium hai aur purchase also
            holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnlineTestData.CoachingID=list1.get(position).getCoachingID();
                    OnlineTestData.bucketID=list1.get(position).getBucketID();
                    OnlineTestData.bucketName=list1.get(position).getBucketName();
                    OnlineTestData.bucketInfo=list1.get(position).getBucketInfo();
                    OnlineTestData.logData=list1.get(position).getLogData();
                    OnlineTestData.status=list1.get(position).getStatus();
                    OnlineTestData.totalTest=list1.get(position).getTotalTest();
                    Intent intent=new Intent(context, OnlineTestActivity.class);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list1==null ? 0 : list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_course,txt_discount,txt_actual_price,txt_type;
        private ImageView course_image,img_rupee1,img_rupee;
        private RelativeLayout Course_layout,layout_coming_soon,layout_free,layout_buy,layout_view,layout_purchased;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_course = itemView.findViewById(R.id.txt_course);
            img_rupee1 = itemView.findViewById(R.id.img_rupee1);
            img_rupee = itemView.findViewById(R.id.img_rupee);
            layout_coming_soon = itemView.findViewById(R.id.layout_coming_soon);
            layout_free = itemView.findViewById(R.id.layout_free);
            layout_purchased = itemView.findViewById(R.id.layout_purchased);
            layout_buy = itemView.findViewById(R.id.layout_buy);
            layout_view = itemView.findViewById(R.id.layout_view);
            Course_layout = itemView.findViewById(R.id.Course_layout);
            course_image=itemView.findViewById(R.id.course_image);
            txt_discount=itemView.findViewById(R.id.txt_discount);
            txt_actual_price=itemView.findViewById(R.id.txt_actual_price);
            txt_type=itemView.findViewById(R.id.txt_type);
        }
    }
}

