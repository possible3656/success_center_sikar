package com.winbee.successcentersikar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.successcentersikar.NewModels.TxnModel;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.AllTxnAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTransactionActivity extends AppCompatActivity {
    private ArrayList<TxnModel> txnModels;
    private RecyclerView txn_recycle;
    private ProgressBarUtil progressBarUtil;
    private AllTxnAdapter adapter;
    String UserID;
    private RelativeLayout today_classes;
    ImageView WebsiteHome,img_share,companylogo;
    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transaction);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        progressBarUtil = new ProgressBarUtil(this);
        txn_recycle = findViewById(R.id.all_txn);
        today_classes = findViewById(R.id.today_classes);
        UserID = SharedPrefManager.getInstance(this).refCode().getUserId();
        layout_user=findViewById(R.id.layout_user);
        WebsiteHome=findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyTransactionActivity.this,MainActivity.class);
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
        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(MyTransactionActivity.this,DashboardCourseActivity.class);
                startActivity(profile);
            }
        });
        layout_test_series=findViewById(R.id.layout_test_series);
        layout_test_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent test = new Intent(MyTransactionActivity.this,SubjectActivity.class);
                startActivity(test);
                // Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        layout_home=findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(MyTransactionActivity.this,MainActivity.class);
                startActivity(profile);
            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(MyTransactionActivity.this,DiscussionActivity.class);
                startActivity(profile);
            }
        });
        layout_notification=findViewById(R.id.layout_notification);
        layout_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(MyTransactionActivity.this,NotificationActivity.class);
                startActivity(profile);
            }
        });
        callCourseApiService();


    }
    private void callCourseApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<TxnModel>> call = apiCAll.getPaymentData(UserID, "WB_010");
        call.enqueue(new Callback<ArrayList<TxnModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TxnModel>> call, Response<ArrayList<TxnModel>> response) {
                int statusCode = response.code();
                txnModels = new ArrayList();
                if (statusCode == 200) {
                        txnModels = response.body();
                        System.out.println("Suree body: " + response.body());
                        adapter = new AllTxnAdapter(MyTransactionActivity.this, txnModels);
                        txn_recycle.setAdapter(adapter);
                        progressBarUtil.hideProgress();


                } else {
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(MyTransactionActivity.this, "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TxnModel>> call, Throwable t) {
                Toast.makeText(MyTransactionActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }
}