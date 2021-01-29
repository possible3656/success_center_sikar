package com.winbee.successcentersikar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.winbee.successcentersikar.NewModels.AboutModel;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.Utils.ProgressBarUtil;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.WebApi.ClientApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends AppCompatActivity {
    ImageView WebsiteHome,img_share,companylogo,img_noti;
    TextView aboutus_textview1,txt_about1;
    String Referal_code;
    private ProgressBarUtil progressBarUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Referal_code= SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();
        companylogo=findViewById(R.id.companylogo);
        progressBarUtil = new ProgressBarUtil(this);
        txt_about1=findViewById(R.id.txt_about1);
        WebsiteHome=findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutUsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        img_noti=findViewById(R.id.img_noti);
        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutUsActivity.this, NotificationActivity.class);
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
                    String referalMessage= "\nMy Referal Code is .\n "+Referal_code;
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage+referalMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                }
            }
        });
        callBannerService();
        Picasso.get().load("https://edu.shekhawatiacademy.com/assets/images/Sekhawati-Defence-Academy.jpg")
                .placeholder(R.drawable.dummyimage).fit().into(companylogo);


    }
    private void callBannerService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<AboutModel> call = apiCAll.getAbout();
        call.enqueue(new Callback<AboutModel>() {
            @Override
            public void onResponse(Call<AboutModel> call, Response<AboutModel> response) {
                int statusCode = response.code();
                if (statusCode == 200 ) {
                    System.out.println("Suree body: " + response.body().getBanner());
                    txt_about1.setText(response.body().getMessage());
                    Picasso.get().load(response.body().getBanner())
                            .placeholder(R.drawable.dummyimage)
                            .into(companylogo);
                    progressBarUtil.hideProgress();
                } else {
                    progressBarUtil.hideProgress();
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AboutModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }

}
