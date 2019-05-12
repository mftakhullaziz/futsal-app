package com.example.futsalgo.Authentikasi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.futsalgo.DialogActivity;
import com.example.futsalgo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    private EditText inputEmail;
    private Button btnReset;
    private FirebaseAuth auth;
    private DialogActivity.p_dialog object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputEmail = (EditText) findViewById(R.id.email_forgot);
        btnReset = (Button) findViewById(R.id.button_password_baru);
        auth = FirebaseAuth.getInstance();

        builder = new AlertDialog.Builder(this);

            btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String email = inputEmail.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //Setting message manually and performing action on button click
                    builder.setMessage("Link ganti password akan dikirimkan melalui email anda, silahkan cek email anda ?")
                            .setCancelable(false)
                            .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ForgotPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                                loginKembali(btnReset);
                                                finish();
                                            } else {
                                                Toast.makeText(ForgotPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


//
//                                    Toast.makeText(getApplicationContext(),"Silahkan cek email dan ganti dengan password baru",
//                                            Toast.LENGTH_SHORT).show();
//                                            /*disini harus di isi method untuk mengganti paswword baru, jika password berhasil diganti makan akan diarahkan ke halaman activity_login*/
//                                            loginKembali(btnReset);
                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                    Toast.makeText(getApplicationContext(),"Password anda tidak jadi diperbaharui",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    //Creating DialogActivity box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    //alert.setTitle("AlertDialogExample");
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);
                }
            });
    }

    public void loginKembali(View view) {
        //animate DialogActivity progress
        object = new DialogActivity.p_dialog(this);
        object.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                finish();
                object.dismiss();
            }
        },3000);

    }


}
