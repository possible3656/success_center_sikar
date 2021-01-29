package com.winbee.successcentersikar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.winbee.successcentersikar.NewModels.AboutModel;
import com.winbee.successcentersikar.NewModels.RedeemModel;
import com.winbee.successcentersikar.NewModels.ReferalModel;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.Utils.Constants;
import com.winbee.successcentersikar.Utils.LocalData;
import com.winbee.successcentersikar.Utils.ProgressBarUtil;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.model.RefCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferalActivity extends AppCompatActivity {
    ImageView WebsiteHome,img_share,img_more,img_less,img_more_work,img_less_work,img_noti;
    TextView txt_referal_code,txt_total_referal,txt_total_points,txt_termscondition,txt_howwork,txt_terms_bold,txt_terms,txt_works,
            txt_works_bold;
    RelativeLayout btn_copy_referal,btn_share_referal,layout_termscondition,layout_howwork;
    Button        btn_redeem;
    String User_id,Referal_code,Coupan;
    EditText editTextEmail;
    SwipeRefreshLayout refresh_referral;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private ProgressBarUtil progressBarUtil;
    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referal);
        User_id=SharedPrefManager.getInstance(this).refCode().getUserId();
        Referal_code=SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();
        progressBarUtil = new ProgressBarUtil(this);
        refresh_referral=findViewById(R.id.refresh_referral);
        layout_termscondition=findViewById(R.id.layout_termscondition);
        layout_howwork=findViewById(R.id.layout_howwork);
        refresh_referral.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callReferalService();
//                redeemValidation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh_referral.setRefreshing(false);
                    }
                },4000);
            }
        });
        layout_user=findViewById(R.id.layout_user);
        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(ReferalActivity.this, DashboardCourseActivity.class);
                startActivity(profile);
            }
        });
        img_noti=findViewById(R.id.img_noti);
        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(ReferalActivity.this, NotificationActivity.class);
                startActivity(profile);
            }
        });
        layout_test_series=findViewById(R.id.layout_test_series);
        layout_test_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent test = new Intent(ReferalActivity.this, SubjectActivity.class);
                startActivity(test);
                // Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        layout_home=findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(ReferalActivity.this,MainActivity.class);
                startActivity(profile);
            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(ReferalActivity.this, DiscussionActivity.class);
                startActivity(profile);
            }
        });
        layout_notification=findViewById(R.id.layout_notification);
        layout_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(ReferalActivity.this,ReferalActivity.class);
                startActivity(profile);
            }
        });
        WebsiteHome=findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReferalActivity.this, MainActivity.class);
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
                    String referalMessage= "\nMy Referral Code is .\n "+Referal_code;
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage+referalMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                }
            }
        });
        btn_share_referal=findViewById(R.id.btn_share_referal);
        btn_share_referal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Success Center Sikar");
                    String shareMessage= "\nSuccess Center Sikar download the application.\n ";
                    String referalMessage= "\nMy Referral Code is .\n "+Referal_code;
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage+referalMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                }
            }
        });
        txt_referal_code=findViewById(R.id.txt_referal_code);
        txt_referal_code.setText(Referal_code);
        txt_termscondition=findViewById(R.id.txt_termscondition);
        txt_terms_bold=findViewById(R.id.txt_terms_bold);
        txt_terms=findViewById(R.id.txt_terms);
        txt_works=findViewById(R.id.txt_works);
        txt_works_bold=findViewById(R.id.txt_works_bold);
        txt_howwork=findViewById(R.id.txt_howwork);
        txt_total_referal=findViewById(R.id.txt_total_referal);
        txt_total_points=findViewById(R.id.txt_total_points);
        img_more_work=findViewById(R.id.img_more_work);
        img_less_work=findViewById(R.id.img_less_work);
        editTextEmail=findViewById(R.id.editTextEmail);
        btn_copy_referal=findViewById(R.id.btn_copy_referal);
        btn_copy_referal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String text;
                text = txt_referal_code.getText().toString();

                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getApplicationContext(), "Text Copied",Toast.LENGTH_SHORT).show();
            }
        });
        btn_redeem=findViewById(R.id.btn_redeem);
        btn_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //api call
                redeemValidation();
            }
        });
        img_less=findViewById(R.id.img_less);
        img_more=findViewById(R.id.img_more);
        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_less.setVisibility(View.VISIBLE);
                txt_termscondition.setVisibility(View.VISIBLE);
                layout_termscondition.setVisibility(View.VISIBLE);
                txt_terms_bold.setVisibility(View.VISIBLE);
                img_more.setVisibility(View.GONE);
                txt_terms.setVisibility(View.GONE);
            }
        });

        img_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_more.setVisibility(View.VISIBLE);
                txt_terms.setVisibility(View.VISIBLE);
                img_less.setVisibility(View.GONE);
                txt_terms_bold.setVisibility(View.GONE);
                txt_termscondition.setVisibility(View.GONE);
                layout_termscondition.setVisibility(View.GONE);
                //set condition in text
            }
        });
        img_more_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_less_work.setVisibility(View.VISIBLE);
                txt_howwork.setVisibility(View.VISIBLE);
                layout_howwork.setVisibility(View.VISIBLE);
                img_more_work.setVisibility(View.GONE);
                txt_works_bold.setVisibility(View.VISIBLE);
                txt_works.setVisibility(View.GONE);

            }
        });

        img_less_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_more_work.setVisibility(View.VISIBLE);
                img_less_work.setVisibility(View.GONE);
                txt_howwork.setVisibility(View.GONE);
                layout_howwork.setVisibility(View.GONE);
                txt_works_bold.setVisibility(View.GONE);
                txt_works.setVisibility(View.VISIBLE);
                //set condition in text
            }
        });

        callReferalService();


    }

    private void callReferalService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<ReferalModel> call = apiCAll.fetchReferalData(User_id, "WB_010");
        call.enqueue(new Callback<ReferalModel>() {
            @Override
            public void onResponse(Call<ReferalModel> call, Response<ReferalModel> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getSuccess()==true) {
                    txt_total_referal.setText(response.body().getTotal_Referral());
                    txt_total_points.setText(String.valueOf(response.body().getTotal_Points()));
                    txt_termscondition.setText(Html.fromHtml(response.body().getTerms_Conditions()));
                    txt_howwork.setText(Html.fromHtml(response.body().getHow_it_works()));
                    progressBarUtil.hideProgress();
                } else {
                    progressBarUtil.hideProgress();
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReferalModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }

    private void redeemValidation() {
         Coupan = editTextEmail.getText().toString();
        if (TextUtils.isEmpty(Coupan)) {
            editTextEmail.setError("Please enter Coupan Code");
            editTextEmail.requestFocus();
            return;
        }
        callRefCodeSignInApi();
    }
    private void callRefCodeSignInApi() {
        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<RedeemModel> call = mService.fetchRedeemData(User_id,"WB_010",Coupan);
        Log.i("tag", "callRefCodeSignInApi: "+Coupan);
        call.enqueue(new Callback<RedeemModel>() {
            @Override
            public void onResponse(Call<RedeemModel> call, Response<RedeemModel> response) {
                int statusCode = response.code();
                if (statusCode==200 && response.body().getSuccess()){
                    progressBarUtil.hideProgress();
                    callReferalService();
                    Toast.makeText(ReferalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    progressBarUtil.hideProgress();
                    Toast.makeText(ReferalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RedeemModel> call, Throwable t) {
                Toast.makeText(ReferalActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }
}