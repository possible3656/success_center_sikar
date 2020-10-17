/*
 * Copyright (c) 2020. rogergcc
 */

package com.winbee.successcentersikar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardCourseActivity extends AppCompatActivity {

  private TextView studentNameMyProfile,mobile,email;
  String Name,Mobile,Email;
  RelativeLayout layout_purchase,layout_test,layout_txn,layout_pdf;
  Button btn_courses;
    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification;
    ImageView WebsiteHome,img_share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_course);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Name=SharedPrefManager.getInstance(this).refCode().getName();
        Mobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        Email=SharedPrefManager.getInstance(this).refCode().getEmail();
        layout_purchase=findViewById(R.id.layout_purchase);
        layout_txn=findViewById(R.id.layout_txn);
        layout_txn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardCourseActivity.this,MyTransactionActivity.class);
                startActivity(intent);
                finish();
            }
        });
        layout_pdf=findViewById(R.id.layout_pdf);
        layout_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardCourseActivity.this,AllPurchasedPdfActivity.class);
                startActivity(intent);
                finish();
            }
        });
        layout_test=findViewById(R.id.layout_test);
        layout_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardCourseActivity.this,MyPurchasedCourseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        layout_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardCourseActivity.this, AllPurchasedTestActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        btn_courses=findViewById(R.id.btn_courses);
//        btn_courses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DashboardCourseActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });


        WebsiteHome=findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardCourseActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        img_share=findViewById(R.id.img_share);
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Success Center Sikar");
                    String shareMessage= "\nSuccess Center Sikar download the application.\n ";
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                }
            }
        });

        studentNameMyProfile=findViewById(R.id.studentNameMyProfile);
        studentNameMyProfile.setText(Name);
        mobile=findViewById(R.id.mobile);
        mobile.setText("Mobile No-"+Mobile);
        email=findViewById(R.id.email);
        email.setText(Email);
        layout_user=findViewById(R.id.layout_user);
        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DashboardCourseActivity.this,DashboardCourseActivity.class);
                startActivity(profile);
            }
        });
        layout_test_series=findViewById(R.id.layout_test_series);
        layout_test_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent test = new Intent(DashboardCourseActivity.this,SubjectActivity.class);
                startActivity(test);
                // Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        layout_home=findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DashboardCourseActivity.this,MainActivity.class);
                startActivity(profile);
            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DashboardCourseActivity.this,DiscussionActivity.class);
                startActivity(profile);
            }
        });
        layout_notification=findViewById(R.id.layout_notification);
        layout_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DashboardCourseActivity.this,NotificationActivity.class);
                startActivity(profile);
            }
        });

    }
}
