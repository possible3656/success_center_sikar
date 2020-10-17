package com.winbee.successcentersikar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.winbee.successcentersikar.NewModels.Detail;
import com.winbee.successcentersikar.NewModels.LogOut;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.WebApi.ClientApi;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;

public class TestSeriesDetailsActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener, PaymentResultWithDataListener
    {
        private TextView titleHome,course_name,course_price,txt_course_discription,txt_total_videos,txt_total_pdf,
                buy_course,txt_course_videos,txt_course_pdf,txt_amount,txt_txn_id,txt_discription_click,txt_discription,txt_course;
        private ImageView img_share,WebsiteHome,payment_image,image_expand_more,image_expand_less,
                image_expand_more_video,image_expand_less_video,image_expand_more_pdf,image_expand_less_pdf;
        private ArrayList<Detail> list;
        private ProgressBarUtil progressBarUtil;
        private Button btn_buy_course,btn_demo,buy_now,go_back,btn_course,go_back_failed;
        private RelativeLayout layout_discription_details,layout_video_details,description_layout,layout_pdf_details,
                layout_success,layout_failed,main_layout;
        String android_id,Username,UserMobile,UserPassword;
        String TAG="payment activity";



        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_series_details);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBarUtil = new ProgressBarUtil(this);
        android_id = Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        Username=SharedPrefManager.getInstance(this).refCode().getName();
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();
        WebsiteHome=findViewById(R.id.WebsiteHome);
        layout_success=findViewById(R.id.layout_success);
        layout_failed=findViewById(R.id.layout_failed);
        main_layout=findViewById(R.id.main_layout);
        txt_txn_id=findViewById(R.id.txt_txn_id);
        go_back_failed=findViewById(R.id.go_back_failed);
        go_back_failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(TestSeriesDetailsActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        go_back=findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(TestSeriesDetailsActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_course=findViewById(R.id.btn_course);
        btn_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(TestSeriesDetailsActivity.this,MyPurchasedCourseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_buy_course=findViewById(R.id.btn_buy_course);
        btn_buy_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(TestSeriesDetailsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_payment_alert);
                TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                TextView txt_course=dialog.findViewById(R.id.txt_course);
                TextView txt_discount=dialog.findViewById(R.id.txt_discount);
                TextView txt_actual_price=dialog.findViewById(R.id.txt_actual_price);
                TextView txt_discount_price=dialog.findViewById(R.id.txt_discount_price);
               // txt_discount_price.setText(String.valueOf(LocalData.Total//));
                txt_actual_price.setText(String.valueOf(LocalData.TestDisplayPrice));
                txt_discount.setPaintFlags(txt_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                txt_discount.setText(String.valueOf(LocalData.TestDiscountPrice));

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
                        //userValidation();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.setCancelable(false);
            }
        });
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
            View hView =  navigationView.getHeaderView(0);
            TextView nav_user = (TextView)hView.findViewById(R.id.txt_user);
            TextView nav_user_nuber = (TextView)hView.findViewById(R.id.txt_user_number);
            nav_user.setText(SharedPrefManager.getInstance(this).refCode().getName());
            nav_user_nuber.setText(SharedPrefManager.getInstance(this).refCode().getUsername());

    }




        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Intent home=new Intent(TestSeriesDetailsActivity.this,MainActivity.class);
                startActivity(home);
            } else if (id == R.id.nav_profile) {
                Intent profile = new Intent(TestSeriesDetailsActivity.this,DashboardCourseActivity.class);
                startActivity(profile);

            } else if (id == R.id.nav_course) {
                Intent course = new Intent(TestSeriesDetailsActivity.this,MyPurchasedCourseActivity.class);
                startActivity(course);
            } else if (id == R.id.nav_test_series) {
                Intent test = new Intent(TestSeriesDetailsActivity.this,AllPurchasedTestActivity.class);
                startActivity(test);
            } else if (id == R.id.nav_txn) {
            Intent txn = new Intent(TestSeriesDetailsActivity.this,MyTransactionActivity.class);
            startActivity(txn);
            } else if (id == R.id.nav_live_class) {
                Intent live = new Intent(TestSeriesDetailsActivity.this,YouTubeVideoList.class);
                startActivity(live);
            } else if (id == R.id.nav_pdf) {
                Intent pdf = new Intent(TestSeriesDetailsActivity.this,AllPurchasedPdfActivity.class);
                startActivity(pdf);
            } else if (id == R.id.nav_contact) {
                Intent intent = new Intent(TestSeriesDetailsActivity.this,ContactUsActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_about) {
                Intent intent = new Intent(TestSeriesDetailsActivity.this,AboutUsActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_rate) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +getPackageName())));


            } else if (id == R.id.nav_share) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Success Center Sikar");
                    String shareMessage= "\nSuccess Center Sikar download the application.\n ";
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) { }

            } else if (id == R.id.nav_logout) {
                logout();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
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
                        SharedPrefManager.getInstance(TestSeriesDetailsActivity.this).logout();
                        startActivity(new Intent(TestSeriesDetailsActivity.this, LoginActivity.class));
                        finish();
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                TestSeriesDetailsActivity.this);
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
                    Toast.makeText(TestSeriesDetailsActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LogOut> call, Throwable t) {
                Toast.makeText(TestSeriesDetailsActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

//        private void userValidation() {
//        final String Course_id = LocalData.TestBuckedId;
//        final String User_id = SharedPrefManager.getInstance(this).refCode().getUserId();
//        final String Amount_org_id =String.valueOf(LocalData.TestDisplayPrice);
//        //final String Amount_org_id ="100";
//        final String Org_id = "WB_007";
//
//
//
//        PaymentModel paymentModel = new PaymentModel();
//        paymentModel.setCourse_id(Course_id);
//        paymentModel.setUser_id(User_id);
//        paymentModel.setAmount_org_id(Amount_org_id);
//        paymentModel.setOrg_id(Org_id);
//
//
//
//
//        callPayment(paymentModel);
//
//    }
//        private void callPayment(final PaymentModel paymentModel){
//        progressBarUtil.showProgress();
//        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
//        Call<PaymentModel> call =apiCall.fetchPaymentData(paymentModel.getCourse_id(),paymentModel.getUser_id(),paymentModel.getAmount_org_id(),paymentModel.getOrg_id());
//        Log.i(TAG, "callPayment: "+paymentModel.getCourse_id()+" "+paymentModel.getUser_id()+" "+paymentModel.getAmount_org_id());
//        call.enqueue(new Callback<PaymentModel>() {
//            @Override
//            public void onResponse(Call<PaymentModel> call, Response<PaymentModel> response) {
//                int statusCode = response.code();
//                if(statusCode==200 && response.body()!=null){
//                    LocalData.Org_id=response.body().getOrgOrderId();
//                    LocalData.RazorpayOrderId=response.body().getRazorpayOrderId();
//                    txt_txn_id.setText("Reference No :- "+LocalData.Org_id);
//                    Toast.makeText(getApplicationContext(),"Payment started" , Toast.LENGTH_SHORT).show();
//                    startPayment();
//                    progressBarUtil.hideProgress();
//                }
//                else{
//                    System.out.println("Sur: response code"+response.message());
//                    Toast.makeText(getApplicationContext(),"NetWork Issue,Please Check Network Connection" , Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<PaymentModel> call, Throwable t) {
//                System.out.println("Suree: "+t.getMessage());
//
//                Toast.makeText(getApplicationContext(),"Failed"+t.getMessage() , Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

//        public void startPayment() {
//        Checkout checkout = new Checkout();
//
//        String str = String.valueOf(LocalData.CourseDiscountPrice);
//        Double inum = Double.parseDouble(str);
//        Double sum = inum*100;
//        String str1 = Double.toString(sum);
//        final Activity activity = this;
//
//        try {
//            JSONObject options = new JSONObject();
//
//            options.put("name", UserName);
//
//
//
//
//            options.put("description", "Purchase Course");
//            options.put("order_id",LocalData.RazorpayOrderId);
//            // options.put("image", "http://edu.rbclasses.com/api/images/RBClasses-logo.jpeg");
//            options.put("currency", "INR");
//            //  options.put("amount",str1);
//            options.put("amount",str1);
//
//            checkout.open(activity, options);
//        } catch(Exception e) {
//            Log.e(TAG, "Error in starting Razorpay Checkout", e);
//        }
//    }

        @Override
        public void onPaymentSuccess(String razorpayPaymentID, PaymentData data) {
        String paymentId = data.getPaymentId();
        String signature = data.getSignature();
        String orderId = data.getOrderId();
//        layout_success.setVisibility(View.VISIBLE);
//        btn_buy_course.setVisibility(View.GONE);
//        buy_now.setClickable(false);
//        btn_demo.setClickable(false);
//        main_layout.setAlpha((float) 0.2);
        Toast.makeText(getApplicationContext(),"We have received your payment,Please for Confirmation " , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TestSeriesDetailsActivity.this,MainActivity.class);
        startActivity(intent);
    }
        @Override
        public void onPaymentError(int i, String s, PaymentData paymentData) {
        //todo layout visible
        layout_failed.setVisibility(View.VISIBLE);
        btn_buy_course.setVisibility(View.GONE);
        buy_now.setClickable(false);
        btn_demo.setClickable(false);
        main_layout.setAlpha((float) 0.2);
    }

        private void forceLogout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }
    }