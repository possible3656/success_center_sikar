package com.winbee.successcentersikar.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.Utils.LocalData;
import com.winbee.successcentersikar.Utils.SharedPrefManager;

public class PdfWebActivity extends AppCompatActivity {
    ImageView WebsiteHome,img_share,img_noti;
    String Referal_code;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_web);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Referal_code= SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();
        WebsiteHome=findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PdfWebActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        img_noti=findViewById(R.id.img_noti);
        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PdfWebActivity.this,NotificationActivity.class);
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
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        WebView web_view = findViewById(R.id.myWebView);
        web_view.requestFocus();
        web_view.getSettings().setJavaScriptEnabled(true);
        String url = "https://docs.google.com/viewer?url=";
        web_view.loadUrl(LocalData.PdfUrl);
        Log.i("tag", "onCreate: "+url+LocalData.PdfUrl);
        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;

            }
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                web_view.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                web_view.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
            }
        });
        web_view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });
    }

}