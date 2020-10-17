package com.winbee.successcentersikar.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.successcentersikar.CourseDetailsActivity;
import com.winbee.successcentersikar.LocalData;
import com.winbee.successcentersikar.MyPurchaseSubjectActivity;
import com.winbee.successcentersikar.NewModels.CourseContentArray;
import com.winbee.successcentersikar.NewModels.PdfSellArray;
import com.winbee.successcentersikar.PdfDetailsActivity;
import com.winbee.successcentersikar.PdfWebActivity;
import com.winbee.successcentersikar.R;

import java.util.List;

public class AllPdfSellAdapter extends RecyclerView.Adapter<AllPdfSellAdapter.ViewHolder> {
    private Context context;
    private List<PdfSellArray> list1;

    public AllPdfSellAdapter(Context context, List<PdfSellArray> horizontalList){
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

        holder.txt_course.setText(Html.fromHtml(list1.get(position).getSubject()));
        holder.txt_type.setText(Html.fromHtml(list1.get(position).getDescription()));
        holder.txt_discount.setText(list1.get(position).getDisplayAmount());
        holder.txt_discount.setPaintFlags(holder.txt_discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txt_actual_price.setText(list1.get(position).getSellingAmount());
        Picasso.get().load(list1.get(position).getThumbnail()).placeholder(R.drawable.dummyimage).fit().into(holder.course_image);

        if (list1.get(position).getVersion().equalsIgnoreCase("1")){
            holder.layout_free.setVisibility(View.VISIBLE);
            holder.layout_coming_soon.setVisibility(View.GONE);
            holder.layout_buy.setVisibility(View.GONE);
            holder.layout_purchased.setVisibility(View.GONE);
            holder.img_rupee1.setVisibility(View.GONE);
            holder.layout_view.setVisibility(View.GONE);
            holder.txt_actual_price.setVisibility(View.GONE);
            holder.img_rupee.setVisibility(View.GONE);
            holder.txt_discount.setVisibility(View.GONE);
            holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.PdfUrl=list1.get(position).getContent();
                    Intent intent = new Intent(context, PdfWebActivity.class);
                    context.startActivity(intent);
                }
            });
        }else if (list1.get(position).getVersion().equalsIgnoreCase("2")){
            if (list1.get(position).getIsPaid().equals(0)) {
                holder.layout_coming_soon.setVisibility(View.GONE);
                holder.layout_free.setVisibility(View.GONE);
                holder.layout_purchased.setVisibility(View.GONE);
                holder.img_rupee1.setVisibility(View.VISIBLE);
                holder.layout_view.setVisibility(View.GONE);
                holder.txt_actual_price.setVisibility(View.VISIBLE);
                holder.img_rupee.setVisibility(View.VISIBLE);
                holder.txt_discount.setVisibility(View.VISIBLE);
                holder.layout_buy.setVisibility(View.VISIBLE);
                holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.PdfUrl = list1.get(position).getContent();
                        LocalData.CourseImage=list1.get(position).getCoverImage();
                        LocalData.CourseName=list1.get(position).getSubject();
                        LocalData.CourseDiscription=list1.get(position).getDescription();
                        LocalData.PdfDiscountPrice=list1.get(position).getDisplayAmount();
                        LocalData.PdfPrice = list1.get(position).getSellingAmount();
                        LocalData.CourseId=list1.get(position).getContentId();
                        Intent intent = new Intent(context, PdfDetailsActivity.class);
                        context.startActivity(intent);
                    }
                });
            }else if (list1.get(position).getIsPaid().equals(1)){
                holder.layout_purchased.setVisibility(View.VISIBLE);
                holder.layout_free.setVisibility(View.GONE);
                holder.layout_coming_soon.setVisibility(View.GONE);
                holder.layout_buy.setVisibility(View.GONE);
                holder.img_rupee1.setVisibility(View.GONE);
                holder.layout_view.setVisibility(View.GONE);
                holder.txt_actual_price.setVisibility(View.GONE);
                holder.img_rupee.setVisibility(View.GONE);
                holder.txt_discount.setVisibility(View.GONE);
                holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.PdfUrl=list1.get(position).getContent();
                        Intent intent = new Intent(context, PdfWebActivity.class);
                        context.startActivity(intent);
                    }
                });
            }
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

