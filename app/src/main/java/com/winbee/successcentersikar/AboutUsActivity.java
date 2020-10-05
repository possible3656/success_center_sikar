package com.winbee.successcentersikar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class AboutUsActivity extends AppCompatActivity {
    ImageView WebsiteHome,img_share,companylogo;
    TextView aboutus_textview1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        WebsiteHome=findViewById(R.id.WebsiteHome);
        aboutus_textview1=findViewById(R.id.aboutus_textview1);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutUsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        img_share=findViewById(R.id.img_share);
        companylogo=findViewById(R.id.companylogo);
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

       // Picasso.get().load("https://edu.shekhawatiacademy.com/assets/images/Sekhawati-Defence-Academy.jpg").fit().into(companylogo);


    }
}
