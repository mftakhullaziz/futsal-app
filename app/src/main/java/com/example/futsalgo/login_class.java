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
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_class extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private TextView btnSignup, btnReset;
    private Button btnLogin;

    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgress = new ProgressDialog(this);
        setContentView(R.layout.login);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.Masuk);
        btnReset = (TextView) findViewById(R.id.LupaPasswd);
        btnSignup = (TextView) findViewById(R.id.toRegister);

        btnSignup.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(login_class.this, nav_drawer_class.class));
//            finish();
//        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            Login();
        } else if (view == btnSignup) {
            startActivity(new Intent(login_class.this, register_class.class));
        } else if (view == btnReset) {
            startActivity(new Intent(login_class.this, forgot_passwd_class.class));
        }
    }

    private void Login() {
        mProgress.setMessage("Login ..");

        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        mProgress.show();

        //authenticate user
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(login_class.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    // there was an error
                    if (password.length() < 6) {
                        inputPassword.setError(getString(R.string.minimum_password));
                    } else {
                        Toast.makeText(login_class.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                        mProgress.dismiss();
                        finish();
                    }
                } else {
                    Intent intent = new Intent(login_class.this, nav_drawer_class.class);
                    mProgress.dismiss();
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


//    public void toRegister(View view) {
//
//        //animate dialog progress
//        setContentView(R.layout.dialog_progress);
//        LottieAnimationView animationView = findViewById(R.id.progressAnimationView);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                startActivity(new Intent(login_class.this,register_class.class));
//                finish();
//            }
//        },3000);
//
//        //no animate
//        //Intent intent = new Intent(login_class.this, register_class.class);
//        //startActivity(intent);
//    }

//    public void lupaPassword(View view) {
//        //animate dialog progress
//        setContentView(R.layout.dialog_progress);
//        LottieAnimationView animationView = findViewById(R.id.progressAnimationView);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                startActivity(new Intent(login_class.this, forgot_passwd_class.class));
//                finish();
//            }
//        },3000);
//
//    }



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
