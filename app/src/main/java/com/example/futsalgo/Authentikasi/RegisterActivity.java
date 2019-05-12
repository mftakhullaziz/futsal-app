package com.example.futsalgo.Authentikasi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.futsalgo.DialogActivity;
import com.example.futsalgo.R;
import com.example.futsalgo.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class RegisterActivity extends AppCompatActivity {

    private EditText inputNama, inputEmail, inputPassword, inputNoTelepon;

    private FirebaseAuth auth;
    private Button btnSignUp;
    private ProgressDialog mProgress;
    private DatabaseReference dbRef;
    private DialogActivity.p_dialog object;

    ImageView imgUserPhoto;
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickimgURI;
    private String nama, email, password, telepon;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Start Inisialisasi Firebase

        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("pengguna");
        dbRef.keepSynced(true);

        mProgress = new ProgressDialog(this);

        inputNama = (EditText) findViewById(R.id.nama_daftar);
        inputEmail = (EditText) findViewById(R.id.email_daftar);
        inputPassword = (EditText) findViewById(R.id.password_daftar);
        inputNoTelepon = (EditText) findViewById(R.id.no_hp);
        btnSignUp = (Button) findViewById(R.id.button_Daftar);

        //End Inisialisasi Firebase

        //Start Button Sign Up

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.setMessage("Mendaftar ...");
                nama = inputNama.getText().toString().trim();
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                String telepon = inputNoTelepon.getText().toString().trim();




                if (password.isEmpty() && nama.isEmpty() && email.isEmpty() && telepon.isEmpty()){
                    inputNama.setError("please input name !");
                    inputEmail.setError("please input email !");
                    inputPassword.setError("please input password !");
                    inputNoTelepon.setError("please input no telephone !");
                    Toast.makeText(getApplicationContext(), "Please input field !", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (email.isEmpty()) {
                        inputEmail.setError("please input email !");
                        Toast.makeText(getApplicationContext(), "please input email !", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (password.isEmpty()) {
                        inputPassword.setError("please input password !");
                        Toast.makeText(getApplicationContext(), "please input password !", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (password.length() < 6) {
                            Toast.makeText(getApplicationContext(), "Password is too short, enter at least 6 characters!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                }




                mProgress.show();

                //create user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            showDialog();
                        } else {
                          /*  startActivity(new Intent(RegisterActivity.this, LoginActivity.class));*/
                            simpanData();
                            UpdateUserInfo(nama, pickimgURI, auth.getCurrentUser());
                            Toast.makeText(RegisterActivity.this, "Register successful, please login !", Toast.LENGTH_SHORT).show();
                            mProgress.dismiss();
                            inputNama.setText("");
                            inputEmail.setText("");
                            inputPassword.setText("");
                            inputNoTelepon.setText("");
                        }
                    }
                });
            }
        });

        //End Button Sign Up


        Intent intent = getIntent();

        imgUserPhoto = (ImageView)findViewById(R.id.userPhoto);
        imgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= 16){
                    checkAndRequestForPermission();
                }else{
                    openGallery();
                }
            }
        });
    }

    private void UpdateUserInfo(final String nama, Uri pickimgURI, final FirebaseUser currentUser) {
        StorageReference mstorage = FirebaseStorage.getInstance().getReference().child("users_photo");
        final StorageReference imgfilePath = mstorage.child(pickimgURI.getLastPathSegment());
        imgfilePath.putFile(pickimgURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgfilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest ProfileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nama)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(ProfileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    showMessage("Daftar Berhasil");
                                }
                            }
                        });
                    }
                });

            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }


    private void simpanData() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("pengguna");
        dbRef.keepSynced(true);
        String idPengguna = user.getUid();

        nama = inputNama.getText().toString();
        email = inputEmail.getText().toString();
        password = inputPassword.getText().toString();
        telepon = inputNoTelepon.getText().toString();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mProgress.show();
            dbRef.child(idPengguna).child("idPengguna").setValue(idPengguna);
            dbRef.child(idPengguna).child("nama").setValue(nama);
            dbRef.child(idPengguna).child("email").setValue(email);
            dbRef.child(idPengguna).child("password").setValue(password);
            dbRef.child(idPengguna).child("telepon").setValue(telepon);

        }
    }

    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Notification")
                .setCancelable(true)
                .setMessage("Register failed")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        alert.show();
    }

    private void checkAndRequestForPermission() {
        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(RegisterActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        }else{
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESCODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){
            pickimgURI = data.getData();
            imgUserPhoto.setImageURI(pickimgURI);
        }
    }

    public void toLogin(View view) {
        /*progress_dialog object=new progress_dialog(this);
        object.show();*/
        object = new DialogActivity.p_dialog(this);
        object.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                object.dismiss();
                finish();

            }
        },3000);

    }

    public void kembali(View view) {
      /*progress_dialog object=new progress_dialog(this);
        object.show();*/
        object = new DialogActivity.p_dialog(this);
        object.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(RegisterActivity.this, WelcomeActivity.class));
                object.dismiss();
                finish();

            }
        },3000);


    }
}
