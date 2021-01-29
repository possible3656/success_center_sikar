package com.winbee.successcentersikar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.Utils.ProgressBarUtil;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.AskDoubtAdapter;
import com.winbee.successcentersikar.model.AskDoubtQuestion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoubtActivity extends AppCompatActivity {
    private Button btn_asked;
    private ProgressBarUtil progressBarUtil;
    private AskDoubtAdapter adapter;
    String Referal_code;
    private ArrayList<AskDoubtQuestion> list;
    private RecyclerView askedQuestion;
    private RelativeLayout today_classes;
    SwipeRefreshLayout doubt_activity;
    private ImageView WebsiteHome,img_share,img_noti;
    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Referal_code= SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();
        btn_asked=findViewById(R.id.btn_asked);
        askedQuestion = findViewById(R.id.gec_asked_question_recycle);
        progressBarUtil   =  new ProgressBarUtil(this);
        today_classes=findViewById(R.id.today_classes);
        doubt_activity=findViewById(R.id.doubt_activity);
        WebsiteHome=findViewById(R.id.WebsiteHome);
        img_share=findViewById(R.id.img_share);
        layout_user=findViewById(R.id.layout_user);
        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DoubtActivity.this,DashboardCourseActivity.class);
                startActivity(profile);
            }
        });
        img_noti=findViewById(R.id.img_noti);
        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DoubtActivity.this,NotificationActivity.class);
                startActivity(profile);
            }
        });
        layout_test_series=findViewById(R.id.layout_test_series);
        layout_test_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent profile = new Intent(MainActivity.this,DashboardCourseActivity.class);
//                startActivity(profile);
                Toast.makeText(DoubtActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        layout_home=findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DoubtActivity.this, MainActivity.class);
                startActivity(profile);
            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DoubtActivity.this,DoubtActivity.class);
                startActivity(profile);
            }
        });
        layout_notification=findViewById(R.id.layout_notification);
        layout_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DoubtActivity.this, ReferalActivity.class);
                startActivity(profile);
            }
        });
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoubtActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Success Center Sikar");
                    String shareMessage= "\nSuccess Center Sikar download the application.\n ";
                    String referalMessage= "\nMy Referal Code is .\n "+Referal_code;
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage+referalMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                }
            }
        });
        doubt_activity.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAskedQuestionApiService();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubt_activity.setRefreshing(false);
                    }
                },4000);
            }
        });
        btn_asked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoubtActivity.this, NewDoubtActivity.class);
                startActivity(intent);
            }
        });
        callAskedQuestionApiService();
    }
    private void callAskedQuestionApiService(){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<AskDoubtQuestion>> call =apiCall.getQuestion();
        call.enqueue(new Callback<ArrayList<AskDoubtQuestion>>() {
            @Override
            public void onResponse(Call<ArrayList<AskDoubtQuestion>> call, Response<ArrayList<AskDoubtQuestion>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new AskDoubtAdapter(DoubtActivity.this,list);
                    askedQuestion.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<AskDoubtQuestion>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                // Toast.makeText(getApplicationContext(),"Failed"+t.getMessage() ,Toast.LENGTH_SHORT).show();
                today_classes.setVisibility(View.VISIBLE);

            }
        });
    }



}
