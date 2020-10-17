package com.winbee.successcentersikar.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.successcentersikar.AllPurchasedCourseTestActivity;
import com.winbee.successcentersikar.CourseDetailsActivity;
import com.winbee.successcentersikar.LocalData;
import com.winbee.successcentersikar.MyPurchaseSubjectActivity;
import com.winbee.successcentersikar.NewModels.CourseContentArray;
import com.winbee.successcentersikar.R;

import java.util.List;

public class AllCourseAdapter extends RecyclerView.Adapter<AllCourseAdapter.ViewHolder> {
    private Context context;
    private List<CourseContentArray> list1;

    public AllCourseAdapter(Context context, List<CourseContentArray> horizontalList){
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

        if (list1.get(position).getRelease_status().equals(0)){
            //coming soon
            holder.layout_coming_soon.setVisibility(View.VISIBLE);
            holder.layout_free.setVisibility(View.GONE);
            holder.layout_view.setVisibility(View.GONE);
            holder.layout_buy.setVisibility(View.GONE);
            holder.layout_purchased.setVisibility(View.GONE);
            holder.img_rupee1.setVisibility(View.VISIBLE);
            holder.txt_actual_price.setVisibility(View.VISIBLE);
            holder.img_rupee.setVisibility(View.GONE);
            holder.txt_discount.setVisibility(View.GONE);
        }else if (list1.get(position).getRelease_status().equals(1)){
            //version check

            if (list1.get(position).getVersion().equalsIgnoreCase("1")){
                //buy now
                holder.layout_coming_soon.setVisibility(View.GONE);
                holder.layout_free.setVisibility(View.GONE);
                holder.layout_buy.setVisibility(View.VISIBLE);
                holder.layout_view.setVisibility(View.GONE);
                holder.layout_purchased.setVisibility(View.GONE);
                holder.img_rupee1.setVisibility(View.VISIBLE);
                holder.txt_actual_price.setVisibility(View.VISIBLE);
                holder.img_rupee.setVisibility(View.VISIBLE);
                holder.txt_discount.setVisibility(View.VISIBLE);
            }else if (list1.get(position).getVersion().equalsIgnoreCase("0")){
                //free course
                holder.layout_coming_soon.setVisibility(View.GONE);
                holder.layout_free.setVisibility(View.VISIBLE);
                holder.layout_buy.setVisibility(View.GONE);
                holder.layout_purchased.setVisibility(View.GONE);
                holder.img_rupee1.setVisibility(View.VISIBLE);
                holder.layout_view.setVisibility(View.GONE);
                holder.txt_actual_price.setVisibility(View.VISIBLE);
                holder.img_rupee.setVisibility(View.GONE);
                holder.txt_discount.setVisibility(View.GONE);
            }
        }

        if (list1.get(position).getPaid().equals(1)){
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
                    LocalData.CourseName = list1.get(position).getBucket_Name();
                    LocalData.CourseImage = list1.get(position).getBucket_Cover_Image();
                    LocalData.CoursePrice = list1.get(position).getDiscount_price();
                    LocalData.CourseDiscription = (String) list1.get(position).getDescription();
                    LocalData.CourseChildLink = list1.get(position).getChild_Link();
                    LocalData.CourseTotalVideos = list1.get(position).getTotal_Video();
                    LocalData.CourseTotalTest = list1.get(position).getTotalPapers();
                    LocalData.CourseTotalPdf = list1.get(position).getTotal_Document();
                    LocalData.CourseDiscountPrice = list1.get(position).getDisplay_price();
                    LocalData.CourseId = list1.get(position).getBucket_ID();
                    Intent intent = new Intent(context, AllPurchasedCourseTestActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        if (list1.get(position).getCourse_show().equals(1)) {
            holder.Course_layout.setVisibility(View.VISIBLE);
            holder.txt_course.setText(list1.get(position).getBucket_Name());
            holder.txt_type.setText(String.valueOf(list1.get(position).getDescription()));
            holder.txt_discount.setText(String.valueOf(list1.get(position).getDisplay_price()));
            holder.txt_discount.setPaintFlags(holder.txt_discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txt_actual_price.setText(String.valueOf(list1.get(position).getDiscount_price()));
            Picasso.get().load(list1.get(position).getBucket_Image())
                    .placeholder(R.drawable.dummyimage)
                    .fit()
                    .into(holder.course_image);

            if (list1.get(position).getRelease_status().equals(1) &&
                    list1.get(position).getVersion().equalsIgnoreCase("1") && list1.get(position).getPaid().equals(0) ) {

                holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.CourseName = list1.get(position).getBucket_Name();
                        LocalData.CourseImage = list1.get(position).getBucket_Cover_Image();
                        LocalData.CoursePrice = list1.get(position).getDiscount_price();
                        LocalData.CourseDiscription = (String) list1.get(position).getDescription();
                        LocalData.CourseChildLink = list1.get(position).getChild_Link();
                        LocalData.CourseTotalVideos = list1.get(position).getTotal_Video();
                        LocalData.CourseTotalTest = list1.get(position).getTotalPapers();
//                        LocalData.CourseInternalTotalVideos = list1.get(position).getVideos_Details();
//                        LocalData.NotesDetails = list1.get(position).getNotes_Details();
                        LocalData.CourseTotalPdf = list1.get(position).getTotal_Document();
                        LocalData.CourseDiscountPrice = list1.get(position).getDisplay_price();
                        LocalData.TotalDiscount = list1.get(position).getTotal_discount();
                        LocalData.CourseId = list1.get(position).getChild_Link();
                        LocalData.TestBuckedId = list1.get(position).getExamSectionId();
                        Intent intent = new Intent(context, CourseDetailsActivity.class);
                        context.startActivity(intent);
                    }
                });
            }else if (list1.get(position).getRelease_status().equals(1) &&
                    list1.get(position).getVersion().equalsIgnoreCase("0") && list1.get(position).getPaid().equals(0)){


                holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.CourseName = list1.get(position).getBucket_Name();
                        LocalData.CourseImage = list1.get(position).getBucket_Cover_Image();
                        LocalData.CoursePrice = list1.get(position).getDiscount_price();
                        LocalData.CourseDiscription = (String) list1.get(position).getDescription();
                        LocalData.CourseChildLink = list1.get(position).getChild_Link();
                        LocalData.CourseTotalVideos = list1.get(position).getTotal_Video();
                        LocalData.CourseTotalPdf = list1.get(position).getTotal_Document();
                        LocalData.CourseDiscountPrice = list1.get(position).getDisplay_price();
                        LocalData.CourseTotalTest = list1.get(position).getTotalPapers();

                        LocalData.CourseId = list1.get(position).getBucket_ID();
                        Intent intent = new Intent(context, MyPurchaseSubjectActivity.class);
                        context.startActivity(intent);
                    }
                });
            }
        }else if (list1.get(position).getRelease_status().equals(1) &&
                list1.get(position).getVersion().equalsIgnoreCase("1") && list1.get(position).getPaid().equals(1)){
            holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.CourseName = list1.get(position).getBucket_Name();
                    LocalData.CourseImage = list1.get(position).getBucket_Cover_Image();
                    LocalData.CoursePrice = list1.get(position).getDiscount_price();
                    LocalData.CourseDiscription = (String) list1.get(position).getDescription();
                    LocalData.CourseChildLink = list1.get(position).getChild_Link();
                    LocalData.CourseTotalVideos = list1.get(position).getTotal_Video();
                    LocalData.CourseTotalPdf = list1.get(position).getTotal_Document();
                    LocalData.CourseDiscountPrice = list1.get(position).getDisplay_price();
                    LocalData.CourseTotalTest = list1.get(position).getTotalPapers();
                    LocalData.CourseId = list1.get(position).getBucket_ID();
                    Intent intent = new Intent(context, MyPurchaseSubjectActivity.class);
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
            layout_buy = itemView.findViewById(R.id.layout_buy);
            layout_view = itemView.findViewById(R.id.layout_view);
            layout_purchased = itemView.findViewById(R.id.layout_purchased);
            Course_layout = itemView.findViewById(R.id.Course_layout);
            course_image=itemView.findViewById(R.id.course_image);
            txt_discount=itemView.findViewById(R.id.txt_discount);
            txt_actual_price=itemView.findViewById(R.id.txt_actual_price);
            txt_type=itemView.findViewById(R.id.txt_type);
        }
    }
}

