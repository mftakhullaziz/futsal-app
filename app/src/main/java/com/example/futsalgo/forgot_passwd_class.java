package com.example.futsalgo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Typeface;

import com.airbnb.lottie.LottieAnimationView;

public class forgot_passwd_class extends AppCompatActivity {
    Button confirm;
    AlertDialog.Builder builder;
    TextView text1, text2;
    EditText text3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        text1=(TextView)findViewById(R.id.ganti_passwd);
        Typeface customfont1=Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        text1.setTypeface(customfont1);

        text2=(TextView)findViewById(R.id.login_kembali);
        Typeface customfont2=Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        text2.setTypeface(customfont2);

        text3=(EditText) findViewById(R.id.email_forgot);
        Typeface customfont3=Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        text3.setTypeface(customfont3);

        confirm = (Button) findViewById(R.id.button_password_baru);
        builder = new AlertDialog.Builder(this);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Setting message manually and performing action on button click
                    builder.setMessage("Link ganti password akan dikirimkan melalui email anda, silahkan cek email anda ?")
                            .setCancelable(false)
                            .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(),"Silahkan cek email dan ganti dengan password baru",
                                            Toast.LENGTH_SHORT).show();
                                            /*disini harus di isi method untuk mengganti paswword baru, jika password berhasil diganti makan akan diarahkan ke halaman login*/
                                            loginKembali(confirm);
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
                    //Creating dialog box
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
        //animate dialog progress
        setContentView(R.layout.dialog_progress);
        LottieAnimationView animationView = findViewById(R.id.progressAnimationView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(forgot_passwd_class.this, login_class.class));
                finish();
            }
        },3000);

    }


}
