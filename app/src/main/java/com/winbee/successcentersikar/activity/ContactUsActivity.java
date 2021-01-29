package com.winbee.successcentersikar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.Utils.SharedPrefManager;

public class ContactUsActivity extends AppCompatActivity {
    ImageView WebsiteHome,img_share,img_noti;
    String Referal_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Referal_code= SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();
        WebsiteHome=findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactUsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        img_noti=findViewById(R.id.img_noti);
        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactUsActivity.this, NotificationActivity.class);
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
//        gec_enquiry_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Uri u = Uri.parse("tel:" + "9795299311");
//                Intent i = new Intent(Intent.ACTION_DIAL, u);
//
//                try
//                {
//                    startActivity(i);
//                }
//                catch (SecurityException s)
//                {
//                    Toast.makeText(ContactUsActivity.this, "Permisiion is necessary", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}