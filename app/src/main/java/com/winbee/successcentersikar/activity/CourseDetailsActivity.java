package com.winbee.successcentersikar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.squareup.picasso.Picasso;
import com.winbee.successcentersikar.NewModels.ContentSyllabu;
import com.winbee.successcentersikar.NewModels.Detail;
import com.winbee.successcentersikar.NewModels.LogOut;
import com.winbee.successcentersikar.NewModels.PaymentModel;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.Utils.LocalData;
import com.winbee.successcentersikar.Utils.ProgressBarUtil;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.CourseContentAdapter;
import com.winbee.successcentersikar.model.RazorPayModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;
public class CourseDetailsActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener, PaymentResultWithDataListener
{
    private TextView titleHome,course_name,course_price,txt_course_discription,buy_course,txt_amount,txt_txn_id,
            txt_discription_click,txt_discription,txt_course,btn_demo_test,btn_demo,total_video,total_notes,total_test,
            txt_actual_price,txt_actual_price_bottom;
    private ImageView img_share,WebsiteHome,payment_image,image_expand_more,image_expand_less,img_noti;
    private ArrayList<Detail> list;
    private ProgressBarUtil progressBarUtil;
    private View view_failed,view_success;
    private RelativeLayout layout_discription_details,description_layout,
            layout_success,layout_failed,main_layout,content_layout,go_back,btn_course,go_back_failed,buy_now,btn_buy_course;
    private LinearLayout layout_test;
    private RecyclerView content_recycleview;
    String android_id,Username,UserMobile,UserPassword;
    String TAG="payment activity";
    String Referal_code;
    private ArrayList<ContentSyllabu> contentSyllabus;
    private CourseContentAdapter courseContentAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Referal_code=SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();
        progressBarUtil = new ProgressBarUtil(this);

        callApiService();
        android_id = Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        Username= SharedPrefManager.getInstance(this).refCode().getName();
        UserMobile=SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();
        callRazorPayService();
        WebsiteHome=findViewById(R.id.WebsiteHome);
        view_failed=findViewById(R.id.view_failed);
        view_success=findViewById(R.id.view_success);
        content_recycleview=findViewById(R.id.content_recycle);
        layout_discription_details=findViewById(R.id.layout_discription_details);
        description_layout=findViewById(R.id.description_layout);
        layout_success=findViewById(R.id.layout_success);
        layout_failed=findViewById(R.id.layout_failed);
        layout_test=findViewById(R.id.layout_test);
        main_layout=findViewById(R.id.main_layout);
        content_layout=findViewById(R.id.content_layout);
        txt_txn_id=findViewById(R.id.txt_txn_id);
        total_video=findViewById(R.id.total_video);
        total_video.setText(Integer.toString(LocalData.CourseTotalVideos));
        total_notes=findViewById(R.id.total_notes);
        total_notes.setText(Integer.toString(LocalData.CourseTotalPdf));
        total_test=findViewById(R.id.total_test);
        total_test.setText(LocalData.CourseTotalTest);
        btn_demo_test=findViewById(R.id.btn_demo_test);
        txt_discription_click=findViewById(R.id.txt_discription_click);
        txt_discription=findViewById(R.id.txt_discription);
        go_back_failed=findViewById(R.id.go_back_failed);
        txt_actual_price=findViewById(R.id.txt_actual_price);
        txt_actual_price.setText(String.valueOf(LocalData.CoursePrice));
        txt_actual_price_bottom=findViewById(R.id.txt_actual_price_bottom);
        txt_actual_price_bottom.setText(String.valueOf(LocalData.CoursePrice));


        go_back_failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(CourseDetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        go_back=findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(CourseDetailsActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_course=findViewById(R.id.btn_course);
        btn_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(CourseDetailsActivity.this, MyPurchasedCourseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        img_noti=findViewById(R.id.img_noti);
        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(CourseDetailsActivity.this, NotificationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if (LocalData.TestBuckedId.equalsIgnoreCase("")){
            layout_test.setVisibility(View.GONE);
            btn_demo_test.setVisibility(View.GONE);
        }else {
            layout_test.setVisibility(View.VISIBLE);
            btn_demo_test.setVisibility(View.VISIBLE);
        }
        btn_demo_test=findViewById(R.id.btn_demo_test);
        btn_demo_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  =new Intent(CourseDetailsActivity.this,DemoOnlineTestActivity.class);
                startActivity(intent);
            }
        });
        buy_now=findViewById(R.id.buy_now);
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(CourseDetailsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_payment_alert);
                CheckBox checkBox = dialog.findViewById(R.id.checkBox);
                TextView txt_pervious_attempt=dialog.findViewById(R.id.txt_pervious_attempt);
                TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                TextView txt_course=dialog.findViewById(R.id.txt_course);
                TextView txt_discount=dialog.findViewById(R.id.txt_discount);
                TextView txt_actual_price=dialog.findViewById(R.id.txt_actual_price);
                TextView txt_discount_price=dialog.findViewById(R.id.txt_discount_price);
                TextView txt_point_price=dialog.findViewById(R.id.txt_point_price);
                txt_actual_price.setText(String.valueOf(LocalData.CoursePrice));
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((CheckBox)view).isChecked()){
                            txt_actual_price.setText(String.valueOf(LocalData.DP_AF_RC));

                            txt_pervious_attempt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    userValidation();
                                    dialog.dismiss();
                                }
                            });
                        }else{
                            txt_actual_price.setText(String.valueOf(LocalData.CoursePrice));

                            txt_pervious_attempt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    userValidation1();
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                });
                txt_discount_price.setText(String.valueOf(LocalData.TotalDiscount));
                txt_point_price.setText(String.valueOf(LocalData.TotalPoints));
               // txt_actual_price.setText(String.valueOf(LocalData.DP_AF_RC));
                txt_discount.setPaintFlags(txt_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                txt_discount.setText(String.valueOf(LocalData.CourseDiscountPrice));

                txt_course.setText(LocalData.CourseName);

                txt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                txt_pervious_attempt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userValidation1();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.setCancelable(false);

            }
        });
        btn_buy_course=findViewById(R.id.btn_buy_course);
        btn_buy_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(CourseDetailsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_payment_alert);
                CheckBox checkBox = dialog.findViewById(R.id.checkBox);
                TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                TextView txt_pervious_attempt=dialog.findViewById(R.id.txt_pervious_attempt);
                TextView txt_course=dialog.findViewById(R.id.txt_course);
                TextView txt_discount=dialog.findViewById(R.id.txt_discount);
                TextView txt_actual_price=dialog.findViewById(R.id.txt_actual_price);
                TextView txt_discount_price=dialog.findViewById(R.id.txt_discount_price);
                TextView txt_point_price=dialog.findViewById(R.id.txt_point_price);
                txt_actual_price.setText(String.valueOf(LocalData.CoursePrice));
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((CheckBox)view).isChecked()){
                            txt_actual_price.setText(String.valueOf(LocalData.DP_AF_RC));
                            TextView txt_pervious_attempt=dialog.findViewById(R.id.txt_pervious_attempt);
                            txt_pervious_attempt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    userValidation();
                                    dialog.dismiss();
                                }
                            });
                        }else{
                            txt_actual_price.setText(String.valueOf(LocalData.CoursePrice));

                            txt_pervious_attempt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    userValidation1();
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                });

                txt_point_price.setText(String.valueOf(LocalData.TotalPoints));
                txt_discount_price.setText(String.valueOf(LocalData.TotalDiscount));
               // txt_actual_price.setText(String.valueOf(LocalData.DP_AF_RC));
                txt_discount.setPaintFlags(txt_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                txt_discount.setText(String.valueOf(LocalData.CourseDiscountPrice));

                txt_course.setText(LocalData.CourseName);

                txt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                txt_pervious_attempt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userValidation1();
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
        btn_demo=findViewById(R.id.btn_demo);
        btn_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetailsActivity.this, MyCourseSubjectActivity.class);
                startActivity(intent);
                finish();
            }
        });
        image_expand_more=findViewById(R.id.image_expand_more);
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
        titleHome=findViewById(R.id.titleHome);
        titleHome.setText(LocalData.CourseName);
        payment_image=findViewById(R.id.payment_image);
        Picasso.get().load(LocalData.CourseImage).placeholder(R.drawable.dummyimage).fit().into(payment_image);
        course_name=findViewById(R.id.course_name);
        course_name.setText(LocalData.CourseName);
        course_price=findViewById(R.id.course_price);
        course_price.setText(String.valueOf(LocalData.CourseDiscountPrice));//LocalData.CoursePrice
        buy_course=findViewById(R.id.buy_course);
        buy_course.setText(String.valueOf(LocalData.CoursePrice));
        txt_course=findViewById(R.id.txt_course);
        txt_course.setText("Item :- "+LocalData.CourseName);
        txt_amount=findViewById(R.id.txt_amount);
        txt_amount.setText(String.valueOf(LocalData.CoursePrice));
        txt_course_discription=findViewById(R.id.txt_course_discription);
        txt_course_discription.setText(LocalData.CourseDiscription);
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

      //  callApiService();
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
            Intent home=new Intent(CourseDetailsActivity.this,MainActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_profile) {
            Intent profile = new Intent(CourseDetailsActivity.this,DashboardCourseActivity.class);
            startActivity(profile);

        } else if (id == R.id.nav_course) {
            Intent course = new Intent(CourseDetailsActivity.this,MyPurchasedCourseActivity.class);
            startActivity(course);
        } else if (id == R.id.nav_test_series) {
            Intent test = new Intent(CourseDetailsActivity.this,AllPurchasedTestActivity.class);
            startActivity(test);
        } else if (id == R.id.nav_pdf) {
            Intent pdf = new Intent(CourseDetailsActivity.this,AllPurchasedPdfActivity.class);
            startActivity(pdf);
        } else if (id == R.id.nav_txn) {
           Intent txn = new Intent(CourseDetailsActivity.this, MyTransactionActivity.class);
            startActivity(txn);
        } else if (id == R.id.nav_live_class) {
            Intent live = new Intent(CourseDetailsActivity.this, YouTubeVideoList.class);
            startActivity(live);
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(CourseDetailsActivity.this,ContactUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(CourseDetailsActivity.this,AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_rate) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +getPackageName())));
        } else if (id == R.id.nav_referal) {
            Intent intent = new Intent(CourseDetailsActivity.this, ReferalActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Success Center Sikar");
                String shareMessage= "\nSuccess Center Sikar download the application.\n ";
                String referalMessage= "\nMy Referal Code is .\n "+Referal_code;
                shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage+referalMessage);
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
                        SharedPrefManager.getInstance(CourseDetailsActivity.this).logout();
                        startActivity(new Intent(CourseDetailsActivity.this, LoginActivity.class));
                        finish();
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                CourseDetailsActivity.this);
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
                    Toast.makeText(CourseDetailsActivity.this, response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LogOut> call, Throwable t) {
                Toast.makeText(CourseDetailsActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    private void userValidation() {
        final String Course_id = LocalData.CourseId;
        final String User_id = SharedPrefManager.getInstance(this).refCode().getUserId();
        final String Amount_org_id =String.valueOf(LocalData.DP_AF_RC);
        //final String Amount_org_id ="100";
        final String Org_id = "WB_010";



        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setCourse_id(Course_id);
        paymentModel.setUser_id(User_id);
        paymentModel.setAmount_org_id(Amount_org_id);
        paymentModel.setOrg_id(Org_id);




        callPayment(paymentModel);

    }
    private void callPayment(final PaymentModel paymentModel){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<PaymentModel> call =apiCall.fetchPaymentData(paymentModel.getCourse_id(),paymentModel.getUser_id(),paymentModel.getAmount_org_id(),paymentModel.getOrg_id(),
                LocalData.TotalPoints);
        Log.i(TAG, "callPayment: "+paymentModel.getCourse_id()+" "+paymentModel.getUser_id()+" "+paymentModel.getAmount_org_id());
        call.enqueue(new Callback<PaymentModel>() {
            @Override
            public void onResponse(Call<PaymentModel> call, Response<PaymentModel> response) {
                int statusCode = response.code();
                if(statusCode==200 && response.body()!=null){
                    LocalData.Org_id=response.body().getOrgOrderId();
                    LocalData.RazorpayOrderId=response.body().getRazorpayOrderId();
                    txt_txn_id.setText("Reference No :- "+LocalData.Org_id);
                    Toast.makeText(getApplicationContext(),"Payment page loading..." , Toast.LENGTH_SHORT).show();
                    startPayment();
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Sur: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"NetWork Issue,Please Check Network Connection" , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PaymentModel> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());

                Toast.makeText(getApplicationContext(),"Failed"+t.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void userValidation1() {
        final String Course_id = LocalData.CourseId;
        final String User_id = SharedPrefManager.getInstance(this).refCode().getUserId();
        final String Amount_org_id =String.valueOf(LocalData.CoursePrice);
        //final String Amount_org_id ="100";
        final String Org_id = "WB_010";



        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setCourse_id(Course_id);
        paymentModel.setUser_id(User_id);
        paymentModel.setAmount_org_id(Amount_org_id);
        paymentModel.setOrg_id(Org_id);




        callPayment1(paymentModel);

    }
    private void callPayment1(final PaymentModel paymentModel){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<PaymentModel> call =apiCall.fetchPaymentData(paymentModel.getCourse_id(),paymentModel.getUser_id(),
                paymentModel.getAmount_org_id(),paymentModel.getOrg_id(),0);
        Log.i(TAG, "callPayment: "+paymentModel.getCourse_id()+" "+paymentModel.getUser_id()+" "+paymentModel.getAmount_org_id());
        call.enqueue(new Callback<PaymentModel>() {
            @Override
            public void onResponse(Call<PaymentModel> call, Response<PaymentModel> response) {
                int statusCode = response.code();
                if(statusCode==200 && response.body()!=null){
                    LocalData.Org_id=response.body().getOrgOrderId();
                    LocalData.RazorpayOrderId=response.body().getRazorpayOrderId();
                    txt_txn_id.setText("Reference No :- "+LocalData.Org_id);
                    Toast.makeText(getApplicationContext(),"Payment page loading..." , Toast.LENGTH_SHORT).show();
                    startPayment();
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Sur: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"NetWork Issue,Please Check Network Connection" , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PaymentModel> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());

                Toast.makeText(getApplicationContext(),"Failed"+t.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID(LocalData.razorPayKey);


        String str = String.valueOf(LocalData.CourseDiscountPrice);
        Double inum = Double.parseDouble(str);
        Double sum = inum*100;
        String str1 = Double.toString(sum);
        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", Username);




            options.put("description", "Purchase Course");
            options.put("order_id",LocalData.RazorpayOrderId);
            // options.put("image", "http://edu.rbclasses.com/api/images/RBClasses-logo.jpeg");
            options.put("currency", "INR");
          //  options.put("amount",str1);
            options.put("amount",str1);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID, PaymentData data) {
        String paymentId = data.getPaymentId();
        String signature = data.getSignature();
        String orderId = data.getOrderId();
        layout_success.setVisibility(View.VISIBLE);
        btn_buy_course.setVisibility(View.GONE);
        view_success.setVisibility(View.VISIBLE);
        buy_now.setClickable(false);
        btn_demo.setClickable(false);
        main_layout.setAlpha((float) 0.2);
//        Toast.makeText(getApplicationContext(),"We have received your payment,Please for Confirmation " , Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(CourseDetailsActivity.this,MainActivity.class);
//        startActivity(intent);
    }
    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        //todo layout visible
        layout_failed.setVisibility(View.VISIBLE);
        view_failed.setVisibility(View.VISIBLE);
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


    private void callApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<ContentSyllabu>> call = apiCAll.getCourseContent("WB_010",LocalData.CourseId);
        call.enqueue(new Callback<ArrayList<ContentSyllabu>>() {
            @Override
            public void onResponse(Call<ArrayList<ContentSyllabu>> call, Response<ArrayList<ContentSyllabu>> response) {
                //ContentSyllabu courseContent=response.body();
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (response.body().size()!=0) {
                        contentSyllabus = response.body();
                        System.out.println("Suree body: " + response.body());
                        courseContentAdapter = new CourseContentAdapter(CourseDetailsActivity.this, contentSyllabus);
                        content_recycleview.setAdapter(courseContentAdapter);
                        progressBarUtil.hideProgress();
                    }else{
                        content_layout.setVisibility(View.GONE);
                        progressBarUtil.hideProgress();
                    }
                }
                else{
                    progressBarUtil.hideProgress();
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"NetWork Issue,Please Check Network Connection" ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ContentSyllabu>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();
                progressBarUtil.hideProgress();
                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }
    private void callRazorPayService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<RazorPayModel> call = apiCAll.getRazorPay();
        call.enqueue(new Callback<RazorPayModel>() {
            @Override
            public void onResponse(Call<RazorPayModel> call, Response<RazorPayModel> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body()!=null ) {
                    progressBarUtil.hideProgress();
                    LocalData.razorPayKey=response.body().getAPI_Key();
//
                } else {
                    progressBarUtil.hideProgress();
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RazorPayModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                Intent intent= new Intent(MainActivity.this,ServerDownMessage.class);
//                startActivity(intent);
//                finish();
                progressBarUtil.hideProgress();
                //System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }

}