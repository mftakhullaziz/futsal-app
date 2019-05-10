package com.example.futsalgo.Authentikasi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.futsalgo.DialogActivity;
import com.example.futsalgo.NavDrawerActivity;
import com.example.futsalgo.R;
import com.example.futsalgo.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.app.ProgressDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private TextView btnSignup, btnReset;
    private Button btnLogin;

    private DialogActivity.p_dialog object;

    private ProgressDialog mProgress, progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgress = new ProgressDialog(this);
        setContentView(R.layout.activity_login);

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
//            startActivity(new Intent(LoginActivity.this, NavDrawerActivity.class));
//            finish();
//        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
//            object = new DialogActivity.p_dialog(this);
//            object.show();
            Login();

        } else if (view == btnSignup) {

            object = new DialogActivity.p_dialog(this);
            object.show();
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

        } else if (view == btnReset) {

            object = new DialogActivity.p_dialog(this);
            object.show();
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));

        }
    }

    private void Login() {
        /*mProgress.setMessage("Login ..");*/
        /*mProgress.setMessage("Login ..");*/
        object = new DialogActivity.p_dialog(this);
//        final progress_dialog object=new progress_dialog(this);

        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            inputEmail.setError("please input email !");
            inputPassword.setError("please input password !");
            Toast.makeText(getApplicationContext(), "please input email address and your password !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "please input email !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "please input password !", Toast.LENGTH_SHORT).show();
            return;
        }



        object.show();
//        mProgress.show();

        //authenticate user
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.


                if (!task.isSuccessful()) {
                    // there was an error
                    if (password.length() < 6) {
                        inputPassword.setError(getString(R.string.minimum_password));
//                        object.dismiss();
                        return;
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
//                        object.dismiss();
                        return;
//                        finish();
                    }
                } else {
                    Intent intent = new Intent(LoginActivity.this, NavDrawerActivity.class);
//                    object.dismiss();
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void kembali(View view) {

//        progress_dialog object=new progress_dialog(this);
        object = new DialogActivity.p_dialog(this);
        object.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                finish();
            }
        },3000);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
