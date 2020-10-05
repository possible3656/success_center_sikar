package com.winbee.successcentersikar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.SekHomeAdapter;
import com.winbee.successcentersikar.model.UpdateModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    private ProgressBarUtil progressBarUtil;
    private ArrayList<UpdateModel> updateModels;
    private SekHomeAdapter sekHomeAdapter;
    private RecyclerView sek_home_recycle;
    private RelativeLayout today_classes;
    private ImageView WebsiteHome,img_share;
    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        progressBarUtil   =  new ProgressBarUtil(this);
        sek_home_recycle=findViewById(R.id.sek_home_recycle);
        today_classes=findViewById(R.id.today_classes);
        WebsiteHome=findViewById(R.id.WebsiteHome);
        img_share=findViewById(R.id.img_share);
        layout_user=findViewById(R.id.layout_user);
        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(NotificationActivity.this,DashboardCourseActivity.class);
                startActivity(profile);
            }
        });
        layout_test_series=findViewById(R.id.layout_test_series);
        layout_test_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent test = new Intent(NotificationActivity.this,SubjectActivity.class);
                startActivity(test);
                // Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        layout_home=findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(NotificationActivity.this,MainActivity.class);
                startActivity(profile);
            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(NotificationActivity.this,DiscussionActivity.class);
                startActivity(profile);
            }
        });
        layout_notification=findViewById(R.id.layout_notification);
        layout_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(NotificationActivity.this,NotificationActivity.class);
                startActivity(profile);
            }
        });
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationActivity.this,MainActivity.class);
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
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                }
            }
        });
        today_classes=findViewById(R.id.today_classes);
        callUpdateApiService();
    }
    private void callUpdateApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<UpdateModel>> call = apiCAll.getDailyupdate();
        call.enqueue(new Callback<ArrayList<UpdateModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UpdateModel>> call, Response<ArrayList<UpdateModel>> response) {
                int statusCode = response.code();
                updateModels = new ArrayList();
                if(statusCode==200){
                    if (response.body().size()!=0){
                        updateModels = response.body();
                        sekHomeAdapter = new SekHomeAdapter(NotificationActivity.this, updateModels);
                        sek_home_recycle.setAdapter(sekHomeAdapter);
                        progressBarUtil.hideProgress();
                    }else{
                        today_classes.setVisibility(View.VISIBLE);
                        progressBarUtil.hideProgress();
                    }
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UpdateModel>> call, Throwable t) {
//                Toast.makeText(NotificationActivity.this, "Failed"+t.getMessage(), Toast.LENGTH_SHORT).show();
//                System.out.println("Suree: Error "+t.getMessage());
                today_classes.setVisibility(View.VISIBLE);
                progressBarUtil.hideProgress();
            }
        });
    }
}
