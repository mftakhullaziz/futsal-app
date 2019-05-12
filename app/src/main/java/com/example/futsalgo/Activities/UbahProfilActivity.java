package com.example.futsalgo.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.futsalgo.Fragments_Menu.Profile_Fragment;
import com.example.futsalgo.Model.Pemesan;
import com.example.futsalgo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UbahProfilActivity extends AppCompatActivity {

    ImageView EditUserPhoto;
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickimgURI;

    private EditText ENama, EAlamat, ENoTelepon;
    private String nama, email, password, alamat, telepon, IDPengguna;
    private TextView EEmail;
    private Button btnUpdate;
    private FirebaseAuth auth;
    private ProgressDialog mProgress;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ENama = (EditText) findViewById(R.id.EditNama);
        EEmail = (TextView) findViewById(R.id.EditEmail);
        EAlamat = (EditText) findViewById(R.id.EditAlamat);
        ENoTelepon = (EditText) findViewById(R.id.EditNotelp);
        btnUpdate = (Button) findViewById(R.id.btn_save);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("pengguna");
        dbRef.keepSynced(true);
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        IDPengguna = user.getUid();


        ImageView nav_photo = (ImageView) findViewById(R.id.EditFotoProfil);
        Glide.with(this).load(user.getPhotoUrl()).into(nav_photo);
        getDataPemesan();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanData();
                Toast.makeText(UbahProfilActivity.this, "Profile Berhasil di Perbaharui",
                        Toast.LENGTH_LONG).show();
            }
        });


        Intent intent = getIntent();

        EditUserPhoto = findViewById(R.id.EditFotoProfil);
        EditUserPhoto.setOnClickListener(new View.OnClickListener() {
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
                                    showMessage("Update");
                                }
                            }
                        });
                    }
                });

            }
        });
    }

    private void checkAndRequestForPermission() {
        if(ContextCompat.checkSelfPermission(UbahProfilActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(UbahProfilActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(UbahProfilActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(UbahProfilActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        }else{
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryIntentnew = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntentnew.setType("image/*");
        startActivityForResult(galleryIntentnew, REQUESCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){
            pickimgURI = data.getData();
            EditUserPhoto.setImageURI(pickimgURI);
        }
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void getDataPemesan() {
        dbRef.child(IDPengguna).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Pemesan dataPemesan = dataSnapshot.getValue(Pemesan.class);
                if (dataPemesan != null) {
                    ENama.setText(dataPemesan.getNama());
                    EEmail.setText(dataPemesan.getEmail());
                    EAlamat.setText(dataPemesan.getAlamat());
                    ENoTelepon.setText(dataPemesan.getTelepon());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void simpanData() {
        final String nama = ENama.getText().toString().trim();
        final String alamat = EAlamat.getText().toString().trim();
        final String telepon = ENoTelepon.getText().toString().trim();


        if (!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(alamat) && !TextUtils.isEmpty(telepon)) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Menyimpan Data");
            progressDialog.show();

            dbRef.child(IDPengguna).child("nama").setValue(nama);
            dbRef.child(IDPengguna).child("alamat").setValue(alamat);
            dbRef.child(IDPengguna).child("telepon").setValue(telepon);

            progressDialog.dismiss();

        }

    }
}
