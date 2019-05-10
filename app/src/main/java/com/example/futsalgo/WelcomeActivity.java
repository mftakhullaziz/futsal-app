package com.example.futsalgo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.futsalgo.Authentikasi.LoginActivity;
import com.example.futsalgo.Authentikasi.RegisterActivity;

public class WelcomeActivity extends AppCompatActivity {

    private DialogActivity.p_dialog object;
    private Button btnMasuk, btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnMasuk = (Button) findViewById(R.id.Masuk_login);
        btnDaftar = (Button) findViewById(R.id.Masuk_daftar);


        object = new DialogActivity.p_dialog(this);

        btnMasuk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                object.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                        finish();
                    }
                },3000);


            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
                        finish();
                    }
                },3000);

            }
        });
    }

}
