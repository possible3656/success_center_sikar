package com.winbee.successcentersikar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.winbee.successcentersikar.NewModels.LogOut;
import com.winbee.successcentersikar.NewModels.TestSeriesPayment;
import com.winbee.successcentersikar.NewModels.TestSubscription;
import com.winbee.successcentersikar.NewModels.TestSubscriptionArray;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.successcentersikar.Utils.LocalData;
import com.winbee.successcentersikar.Utils.OnlineTestData;
import com.winbee.successcentersikar.Utils.ProgressBarUtil;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.DemoTestListAdapter;
import com.winbee.successcentersikar.adapter.SubscriptionTestListAdapter;
import com.winbee.successcentersikar.adapter.TestSubscriptionAdapter;
import com.winbee.successcentersikar.model.RazorPayModel;
import com.winbee.successcentersikar.model.SIACDetailsDataModel;
import com.winbee.successcentersikar.model.SIACDetailsMainModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;
import static com.winbee.successcentersikar.Utils.LocalData.UserName;

public class TestSubscriptionActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    private ArrayList<TestSubscriptionArray> list;
    private RecyclerView gec_test_recycle,recycle_test;
    private ShimmerLayout shimmerLayout;
    private TestSubscriptionAdapter adapter;
    RelativeLayout description_layout,layout_discription_details,image_layout,layout_success,layout_failed;
    private ImageView WebsiteHome,img_share,image_expand_more,image_expand_less,img_noti;
    String UserId,android_id,UserMobile,UserPassword;
    TextView txt_no_subject,course_name,txt_discription_click,txt_discription,txt_course_discription,txt_total_test,
            txt_amount,txt_course,txt_txn_id;
    private ProgressBarUtil progressBarUtil;
    String Referal_code;
    Button btn_demo,go_back,btn_course,go_back_failed,btn_payment;
    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification,footer;
    private Toast toast_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_subscription);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Referal_code=SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();
        gec_test_recycle = findViewById(R.id.gec_test_recycle);
        txt_no_subject = findViewById(R.id.txt_no_subject);
        course_name = findViewById(R.id.course_name);
        course_name.setText(LocalData.TestName);
        shimmerLayout=findViewById(R.id.shimmerLayout);
        recycle_test=findViewById(R.id.recycle_test);
        progressBarUtil   =  new ProgressBarUtil(this);
        UserId= SharedPrefManager.getInstance(this).refCode().getUserId();
        android_id = Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();
        callRazorPayService();
        getTestList();
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
                Intent intent  =new Intent(TestSubscriptionActivity.this, DemoOnlineTestActivity.class);
                startActivity(intent);
            }
        });
        img_noti=findViewById(R.id.img_noti);
        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(TestSubscriptionActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        go_back=findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(TestSubscriptionActivity.this, MainActivity.class);
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
                    String referalMessage= "\nMy Referal Code is .\n "+Referal_code;
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage+referalMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                }
            }
        });
        layout_user=findViewById(R.id.layout_user);
        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(TestSubscriptionActivity.this, DashboardCourseActivity.class);
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
                Intent profile = new Intent(TestSubscriptionActivity.this, DiscussionActivity.class);
                startActivity(profile);
            }
        });
        layout_notification=findViewById(R.id.layout_notification);
        layout_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(TestSubscriptionActivity.this, ReferalActivity.class);
                startActivity(profile);
            }
        });
        btn_payment=findViewById(R.id.btn_payment);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(TestSubscriptionActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_payment_alert_test);
                TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                TextView txt_course=dialog.findViewById(R.id.txt_course);
                TextView txt_discount=dialog.findViewById(R.id.txt_discount);
                TextView txt_actual_price=dialog.findViewById(R.id.txt_actual_price);
                TextView txt_discount_price=dialog.findViewById(R.id.txt_discount_price);
                txt_discount_price.setText(LocalData.TestDiscountPrice);
                txt_actual_price.setText(LocalData.TestDiscountPrice);

                txt_discount.setPaintFlags(txt_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                txt_discount.setText(LocalData.TestDisplayPrice);

                txt_course.setText(LocalData.TestName);
                TextView txt_pervious_attempt=dialog.findViewById(R.id.txt_pervious_attempt);
                txt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                txt_pervious_attempt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userValidation();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.setCancelable(false);
            }
        });

        callCourseSubjectApiService();

    }
    private void userValidation() {
        final String Course_id = LocalData.TestBuckedId;
        final String User_id = SharedPrefManager.getInstance(this).refCode().getUserId();
        final String Amount_org_id =LocalData.TestDiscountPrice;
        //final String Amount_org_id ="100";
        final String Org_id = "WB_010";
        final String subscription_id=LocalData.TestBuckedId;



        TestSeriesPayment testSeriesPayment = new TestSeriesPayment();
        testSeriesPayment.setCourse_id(Course_id);
        testSeriesPayment.setUser_id(User_id);
        testSeriesPayment.setAmount_org_id(Amount_org_id);
        testSeriesPayment.setOrg_id(Org_id);
        testSeriesPayment.setSubscriptionId(subscription_id);




        callPayment(testSeriesPayment);

    }
    private void callPayment(final TestSeriesPayment testSeriesPayment){
        progressBarUtil.showProgress();
        ClientApi apiCall = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<TestSeriesPayment> call =apiCall.fetchTestPaymentData(testSeriesPayment.getCourse_id(),testSeriesPayment.getUser_id(),testSeriesPayment.getAmount_org_id(),testSeriesPayment.getOrg_id(),testSeriesPayment.getSubscriptionId());
        Log.i("tag", "callPayment: "+testSeriesPayment.getCourse_id()+" "+testSeriesPayment.getUser_id()+" "+testSeriesPayment.getAmount_org_id());
        call.enqueue(new Callback<TestSeriesPayment>() {
            @Override
            public void onResponse(Call<TestSeriesPayment> call, Response<TestSeriesPayment> response) {
                int statusCode = response.code();
                if(statusCode==200 && response.body()!=null){
                    LocalData.Org_id=response.body().getOrgOrderId();
                    LocalData.RazorpayOrderId=response.body().getRazorpayOrderId();
                    Log.i("tag", "onResponse: "+response.body().getRazorpayOrderId());
                    Log.i("tag", "test api key: "+LocalData.razorPayKeyTest);
                    Toast.makeText(TestSubscriptionActivity.this,"Payment Page Loading..." , Toast.LENGTH_SHORT).show();
                    startPayment();
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Sur: response code"+response.message());
                    Toast.makeText(TestSubscriptionActivity.this,"NetWork Issue,Please Check Network Connection" , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TestSeriesPayment> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());

                Toast.makeText(TestSubscriptionActivity.this,"Failed"+t.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void startPayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID(LocalData.razorPayKeyTest);

        String str = LocalData.TestDiscountPrice;
        Double inum = Double.parseDouble(str);
        Double sum = inum*100;
        String str1 = Double.toString(sum);
        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", UserName);




            options.put("description", "Purchase Test series");
            options.put("order_id",LocalData.RazorpayOrderId);
            // options.put("image", "http://edu.rbclasses.com/api/images/RBClasses-logo.jpeg");
            options.put("currency", "INR");
            //  options.put("amount",str1);
            options.put("amount",str1);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("tag", "Error in starting Razorpay Checkout", e);
        }
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
            footer.setVisibility(View.GONE);
            btn_payment.setVisibility(View.GONE);
            image_layout.setAlpha((float) 0.2);
//        Toast.makeText(TestSubscriptionActivity.this,"We have received your payment,Please for Confirmation " , Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(TestSubscriptionActivity.this, MainActivity.class);
//        startActivity(intent);
    }
    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        layout_failed.setVisibility(View.VISIBLE);
        footer.setVisibility(View.GONE);
        btn_payment.setVisibility(View.GONE);
        btn_demo.setClickable(false);
        image_layout.setAlpha((float) 0.2);
       // Toast.makeText(TestSubscriptionActivity.this, "Payment Unsuccessful", Toast.LENGTH_SHORT).show();
    }
    private void forceLogout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }
    private void callRazorPayService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<RazorPayModel> call = apiCAll.getRazorPay();
        call.enqueue(new Callback<RazorPayModel>() {
            @Override
            public void onResponse(Call<RazorPayModel> call, Response<RazorPayModel> response) {
                int statusCode = response.code();

                if (statusCode == 200 && response.body()!=null ) {
                    progressBarUtil.hideProgress();
                    LocalData.razorPayKeyTest=response.body().getAPI_Key();
                    System.out.println("==================================" + response.body());
                } else {
                    progressBarUtil.hideProgress();
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RazorPayModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBarUtil.hideProgress();
            }
        });
    }

    //test list
    private void getTestList() {
        apiCall();
        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SIACDetailsMainModel> call=apiClient.fetchSIACDetails(OnlineTestData.org_code,OnlineTestData.auth_code, LocalData.TestBuckedId,UserId);
        call.enqueue(new Callback<SIACDetailsMainModel>() {
            @Override
            public void onResponse(Call<SIACDetailsMainModel> call, Response<SIACDetailsMainModel> response) {
                apiCalled();
                SIACDetailsMainModel siacDetailsMainModel=response.body();
                if(siacDetailsMainModel!=null){
                    if (siacDetailsMainModel.getMessage().equalsIgnoreCase("true")){
                        List<SIACDetailsDataModel> siacDetailsDataModelList=new ArrayList<>(Arrays.asList(siacDetailsMainModel.getData()));
                        SubscriptionTestListAdapter testListAdapter=new SubscriptionTestListAdapter(TestSubscriptionActivity.this,siacDetailsDataModelList);
                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(TestSubscriptionActivity.this, LinearLayoutManager.VERTICAL, false);
                        recycle_test.setLayoutManager(layoutManager);
                        recycle_test.setItemAnimator(new DefaultItemAnimator());
                        recycle_test.setAdapter(testListAdapter);
                    }
                    else
                        doToast(siacDetailsMainModel.getMessage());
                }
                else
                    doToast("data null");
            }
            @Override
            public void onFailure(Call<SIACDetailsMainModel> call, Throwable t) {
                doToast(getString(R.string.went_wrong));
                System.out.println("call fail "+t);
                apiCalled();
            }
        });
    }
    private void apiCall() {
        shimmerLayout.setVisibility(View.VISIBLE);
        shimmerLayout.startShimmerAnimation();
    }
    private void apiCalled() {
        shimmerLayout.setVisibility(View.GONE);
        shimmerLayout.stopShimmerAnimation();
    }
    private void doToast(String msg){
        if(toast_msg !=null){
            toast_msg.cancel();
        }
        toast_msg = Toast.makeText(TestSubscriptionActivity.this, msg, Toast.LENGTH_SHORT);
        toast_msg.show();
    }
}