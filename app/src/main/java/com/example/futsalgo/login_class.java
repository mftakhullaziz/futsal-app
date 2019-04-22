package com.example.futsalgo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.graphics.Typeface;

import com.airbnb.lottie.LottieAnimationView;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_class extends AppCompatActivity {
    TextView text1, text2, text3, text4;
    EditText eMail, passWord;

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(login_class.this, nav_drawer_class.class));
            finish();
        }

        setContentView(R.layout.login);







        text1=(TextView)findViewById(R.id.SilahkanMasuk);
        Typeface customfont1=Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        text1.setTypeface(customfont1);

        eMail=(EditText) findViewById(R.id.email);
        Typeface customfont2=Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        eMail.setTypeface(customfont2);

        passWord=(EditText) findViewById(R.id.password);
        Typeface customfont3=Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        passWord.setTypeface(customfont3);

//        text2=(TextView)findViewById(R.id.ch_rememberme);
//        Typeface customfont4=Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
//        text2.setTypeface(customfont4);

//        mAsuk=(Button) findViewById(R.id.Masuk);
//        Typeface customfont5=Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Bold.ttf");
//        mAsuk.setTypeface(customfont5);

        text3=(TextView)findViewById(R.id.LupaPasswd);
        Typeface customfont6=Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        text3.setTypeface(customfont6);

        text4=(TextView)findViewById(R.id.toRegister);
        Typeface customfont7=Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        text4.setTypeface(customfont7);
    }

    public void toRegister(View view) {

        //animate dialog progress
        setContentView(R.layout.dialog_progress);
        LottieAnimationView animationView = findViewById(R.id.progressAnimationView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(login_class.this,register_class.class));
                finish();
            }
        },3000);

        //no animate
        //Intent intent = new Intent(login_class.this, register_class.class);
        //startActivity(intent);
    }

    public void lupaPassword(View view) {
        //animate dialog progress
        setContentView(R.layout.dialog_progress);
        LottieAnimationView animationView = findViewById(R.id.progressAnimationView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(login_class.this, forgot_passwd_class.class));
                finish();
            }
        },3000);

    }

    public void masuk(View view) {
        //animate dialog progress
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.Masuk);

        final String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        setContentView(R.layout.dialog_progress);
        LottieAnimationView animationView = findViewById(R.id.progressAnimationView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login_class.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(login_class.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(login_class.this, nav_drawer_class.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

//                startActivity(new Intent(login_class.this, nav_drawer_class.class));
//                finish();
            }
        },3000);




    }


    public void kembali(View view) {
        setContentView(R.layout.dialog_progress);
        LottieAnimationView animationView = findViewById(R.id.progressAnimationView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(login_class.this, welcome.class));
                finish();
            }
        },3000);
    }
}
