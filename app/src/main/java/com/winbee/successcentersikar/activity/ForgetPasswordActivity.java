package com.winbee.successcentersikar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.Utils.LocalData;
import com.winbee.successcentersikar.Utils.ProgressBarUtil;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.model.ForgetMobile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText forgetMobile;
    Button forgetButton;
    private ProgressBarUtil progressBarUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        forgetMobile = findViewById(R.id.editTextre_Password);
        progressBarUtil = new ProgressBarUtil(this);
        forgetButton = findViewById(R.id.buttonForget);
        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userValidation();
            }
        });
    }
    private void userValidation() {
        final String mobile = forgetMobile.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            forgetMobile.setError("Please enter your mobile no");
            forgetMobile.requestFocus();
            return;
        }
        ForgetMobile forgetMobile = new ForgetMobile();
        forgetMobile.setUsername(mobile);
        callForgetMobileApi(forgetMobile);
    }
    private void callForgetMobileApi(final ForgetMobile forgetMobile){

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<ForgetMobile> call = mService.getForgetMobile(1,forgetMobile.getUsername());
        Log.i("forgetpassword", "callForgetMobileApi: "+forgetMobile.getUsername());
        call.enqueue(new Callback<ForgetMobile>() {
            @Override
            public void onResponse(Call<ForgetMobile> call, Response<ForgetMobile> response) {
                int statusCode  = response.code();
                if(statusCode==200 && response.body().getSuccess()==true ) {
                    progressBarUtil.hideProgress();
                    Intent intent = new Intent(ForgetPasswordActivity.this, ResetPassWordActivity.class);
                    LocalData.MobileNumber=response.body().getUsername();
                    startActivity(intent);

                }else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(ForgetPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ForgetMobile> call, Throwable t) {
                Toast.makeText(ForgetPasswordActivity.this,"Failed"+t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }


}
