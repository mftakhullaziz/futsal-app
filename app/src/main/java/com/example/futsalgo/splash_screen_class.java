package com.example.futsalgo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Typeface;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;

public class splash_screen_class extends AppCompatActivity {

    TextView teks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

//
//        teks=(TextView)findViewById(R.id.pesanTextView);
//        Typeface customfont=Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
//        teks.setTypeface(customfont);



        LottieAnimationView animationView = findViewById(R.id.progressAnimationView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(splash_screen_class .this, welcome.class));
                finish();
            }
        },3000);

    }
}
