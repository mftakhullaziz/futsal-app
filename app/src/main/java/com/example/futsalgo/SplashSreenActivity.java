package com.example.futsalgo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;

public class SplashSreenActivity extends AppCompatActivity {

    TextView teks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//
//        teks=(TextView)findViewById(R.id.pesanTextView);
//        Typeface customfont=Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
//        teks.setTypeface(customfont);



        LottieAnimationView animationView = findViewById(R.id.progressAnimationView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashSreenActivity.this, WelcomeActivity.class));
                finish();
            }
        },2500);

    }
}
