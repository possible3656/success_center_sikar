package com.winbee.successcentersikar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.winbee.successcentersikar.NewModels.LogOut;
import com.winbee.successcentersikar.NewModels.TestSubscription;
import com.winbee.successcentersikar.NewModels.TestSubscriptionArray;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.TestSubscriptionAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;

public class TestSubscriptionActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    private ArrayList<TestSubscriptionArray> list;
    private RecyclerView gec_test_recycle;
    private TestSubscriptionAdapter adapter;
    RelativeLayout description_layout,layout_discription_details,image_layout,layout_success,layout_failed;
    private ImageView WebsiteHome,img_share,image_expand_more,image_expand_less;
    String UserId,android_id,UserMobile,UserPassword;
    TextView txt_no_subject,course_name,txt_discription_click,txt_discription,txt_course_discription,txt_total_test,
            txt_amount,txt_course,txt_txn_id;
    private ProgressBarUtil progressBarUtil;
    Button btn_demo,go_back,btn_course,go_back_failed;
    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification,footer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_subscription);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        gec_test_recycle = findViewById(R.id.gec_test_recycle);
        txt_no_subject = findViewById(R.id.txt_no_subject);
        course_name = findViewById(R.id.course_name);
        course_name.setText(LocalData.TestName);

        progressBarUtil   =  new ProgressBarUtil(this);
        UserId=SharedPrefManager.getInstance(this).refCode().getUserId();
        android_id = Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();
        description_layout=findViewById(R.id.description_layout);
        layout_discription_details=findViewById(R.id.layout_discription_details);
        image_layout=findViewById(R.id.image_layout);
        txt_discription_click=findViewById(R.id.txt_discription_click);
        txt_discription=findViewById(R.id.txt_discription);
        txt_course_discription=findViewById(R.id.txt_course_discription);
        txt_course_discription.setText(LocalData.TestDiscription);
        txt_total_test=findViewById(R.id.txt_total_test);
        txt_total_test.setText("Total Test ( "+LocalData.TotalTest+" )");
        image_expand_more=findViewById(R.id.image_expand_more);
        image_expand_less=findViewById(R.id.image_expand_less);
        footer=findViewById(R.id.footer);
        layout_success=findViewById(R.id.layout_success);
        layout_failed=findViewById(R.id.layout_failed);
        txt_amount=findViewById(R.id.txt_amount);
        txt_amount.setText("Amount :-"+LocalData.TestDiscountPrice);
        txt_course=findViewById(R.id.txt_course);
        txt_course.setText("Test Name :-"+LocalData.TestName);
        txt_txn_id=findViewById(R.id.txt_txn_id);
        txt_txn_id.setText("Reference No :- "+LocalData.Org_id);
        btn_demo=findViewById(R.id.btn_demo);
        btn_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(TestSubscriptionActivity.this,DemoOnlineTestActivity.class);
                startActivity(intent);
            }
        });

        go_back=findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(TestSubscriptionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        go_back_failed=findViewById(R.id.go_back_failed);
        go_back_failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(TestSubscriptionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btn_course=findViewById(R.id.btn_course);
        btn_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(TestSubscriptionActivity.this,SubjectActivity.class);
                startActivity(intent);
            }
        });
        description_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_discription_details.setVisibility(View.VISIBLE);
                txt_discription_click.setVisibility(View.VISIBLE);
                txt_discription.setVisibility(View.GONE);
                image_expand_more.setVisibility(View.GONE);
                image_expand_less.setVisibility(View.VISIBLE);

            }
        });

        image_expand_less=findViewById(R.id.image_expand_less);
        image_expand_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_discription_details.setVisibility(View.GONE);
                image_expand_more.setVisibility(View.VISIBLE);
                txt_discription_click.setVisibility(View.GONE);
                txt_discription.setVisibility(View.VISIBLE);
                image_expand_less.setVisibility(View.GONE);
            }
        });


        WebsiteHome=findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestSubscriptionActivity.this,MainActivity.class);
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
        layout_user=findViewById(R.id.layout_user);
        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(TestSubscriptionActivity.this,DashboardCourseActivity.class);
                startActivity(profile);
            }
        });
        layout_test_series=findViewById(R.id.layout_test_series);
        layout_test_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent test = new Intent(TestSubscriptionActivity.this,SubjectActivity.class);
                startActivity(test);
                // Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        layout_home=findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(TestSubscriptionActivity.this,MainActivity.class);
                startActivity(profile);
            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(TestSubscriptionActivity.this,DiscussionActivity.class);
                startActivity(profile);
            }
        });
        layout_notification=findViewById(R.id.layout_notification);
        layout_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(TestSubscriptionActivity.this,NotificationActivity.class);
                startActivity(profile);
            }
        });


        callCourseSubjectApiService();

    }

    private void callCourseSubjectApiService(){
        progressBarUtil.showProgress();
        ClientApi mService = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<TestSubscription> call = mService.getSubscriptionDetails(LocalData.TestBuckedId,"WB_010");
        call.enqueue(new Callback<TestSubscription>() {
            @Override
            public void onResponse(Call<TestSubscription> call, Response<TestSubscription> response) {
                TestSubscription testSubscription =response.body();

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                        System.out.println("Suree body: " + response.body());
                        list = new ArrayList<>(Arrays.asList(Objects.requireNonNull(testSubscription).getData()));
                        if (list.size()!=0){
                            txt_no_subject.setVisibility(View.GONE);
                            image_layout.setVisibility(View.VISIBLE);
                            adapter = new TestSubscriptionAdapter(TestSubscriptionActivity.this, list);
                            gec_test_recycle.setAdapter(adapter);
                            progressBarUtil.hideProgress();
                        }else{
                            txt_no_subject.setVisibility(View.VISIBLE);
                            image_layout.setVisibility(View.GONE);
                            progressBarUtil.hideProgress();
                        }

                } else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TestSubscription> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
            }
        });
    }

    private void logout() {

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<LogOut> call = mService.refCodeLogout(3, UserMobile, UserPassword, "SCS001",android_id);
        call.enqueue(new Callback<LogOut>() {
            @Override
            public void onResponse(Call<LogOut> call, Response<LogOut> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getLoginStatus()!=false) {
                    if (response.body().getError()==false){
                        progressBarUtil.hideProgress();
                        SharedPrefManager.getInstance(TestSubscriptionActivity.this).logout();
                        startActivity(new Intent(TestSubscriptionActivity.this, LoginActivity.class));
                        finish();
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                TestSubscriptionActivity.this);
                        alertDialogBuilder.setTitle("Alert");
                        alertDialogBuilder
                                .setMessage(response.body().getError_Message())
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        forceLogout();
                                    }
                                });

                        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }


                } else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(TestSubscriptionActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LogOut> call, Throwable t) {
                Toast.makeText(TestSubscriptionActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }


    @Override
    public void onPaymentSuccess(String razorpayPaymentID, PaymentData data) {
        String paymentId = data.getPaymentId();
        String signature = data.getSignature();
        String orderId = data.getOrderId();
            layout_success.setVisibility(View.VISIBLE);
            btn_demo.setClickable(false);
            image_layout.setAlpha((float) 0.2);
//        Toast.makeText(TestSubscriptionActivity.this,"We have received your payment,Please for Confirmation " , Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(TestSubscriptionActivity.this, MainActivity.class);
//        startActivity(intent);
    }
    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        layout_failed.setVisibility(View.VISIBLE);
        footer.setVisibility(View.GONE);
        btn_demo.setClickable(false);
        image_layout.setAlpha((float) 0.2);
       // Toast.makeText(TestSubscriptionActivity.this, "Payment Unsuccessful", Toast.LENGTH_SHORT).show();
    }
    private void forceLogout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }

}