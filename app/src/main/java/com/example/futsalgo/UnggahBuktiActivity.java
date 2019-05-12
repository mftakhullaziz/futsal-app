package com.example.futsalgo;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.futsalgo.Model.Pesanan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UnggahBuktiActivity extends AppCompatActivity {

    Button pilihGambar, btnUnggah;
    EditText namaRekening, nomorRekening;
    // Creating URI.
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;
    private Toolbar toolbar;
    private RadioGroup radioPembayaran;
    private RadioButton rbLunas, rbUangMuka;
    private TextView nominal;
    String jenisPembayaran;
    String emailTempatFutsal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unggah_bukti);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.montserat);
        getSupportActionBar().setTitle("Upload Proof of Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        namaRekening = findViewById(R.id.etNamaRekening);
        nomorRekening = findViewById(R.id.etNomorRekening);
        radioPembayaran = findViewById(R.id.rgPembayaran);
        rbLunas = findViewById(R.id.rbLunas);
        rbUangMuka = findViewById(R.id.rbUangMuka);
        nominal = findViewById(R.id.tvNominal);
        pilihGambar = findViewById(R.id.pilihGambar);
        btnUnggah = findViewById(R.id.unggah);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(UnggahBuktiActivity.this);

        pilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), Image_Request_Code);
            }
        });

        btnUnggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calling method to upload selected image on Firebase storage.
                UnggahBuktiPembayaran();
            }
        });

        radioPembayaran.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbLunas.isChecked()) {
                    hitungLunas();
                } else if (rbUangMuka.isChecked()) {
                    hitungUangMuka();
                }
            }
        });
    }


    private void UnggahBuktiPembayaran() {
        final String idPesanan = getIntent().getStringExtra("idPesanan");
        final String namaRekPemesan = namaRekening.getText().toString().trim();
        final String nomorRekPemesan = nomorRekening.getText().toString().trim();
        final String nominalTransfer = nominal.getText().toString().trim();


        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {
            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...");
            // Showing progressDialog.
            progressDialog.show();
            // Creating second StorageReference.
            final StorageReference storageReference2nd = storageReference.child("albums/" + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();
                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            databaseReference.child("pesanan").child(idPesanan).child("pembayaran").child("namaRekPemesan").setValue(namaRekPemesan);
                            databaseReference.child("pesanan").child(idPesanan).child("pembayaran").child("idPesanan").setValue(idPesanan);
                            databaseReference.child("pesanan").child(idPesanan).child("pembayaran").child("nomorRekPemesan").setValue(nomorRekPemesan);
                            databaseReference.child("pesanan").child(idPesanan).child("pembayaran").child("jenisPembayaran").setValue(jenisPembayaran);
                            databaseReference.child("pesanan").child(idPesanan).child("pembayaran").child("nominalTransfer").setValue(nominalTransfer);
                            databaseReference.child("pesanan").child(idPesanan).child("pembayaran").child("buktiPembayaran").setValue(taskSnapshot.toString());
                            /*buatPemberitahuan();*/
                            ubahStatusPesanan();
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Hiding the progressDialog.
                            progressDialog.dismiss();
                            // Showing exception erro message.
                            Toast.makeText(UnggahBuktiActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            // Setting progressDialog Title.
                            progressDialog.setTitle("Sedang mengunggah...");
                        }
                    });
        } else {
            Toast.makeText(UnggahBuktiActivity.this, "Lengkapi semua data", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            try {
                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                // After selecting image change choose button above text.
                pilihGambar.setText("Image Selected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void ubahStatusPesanan() {
        String idPesanan = getIntent().getStringExtra("idPesanan");
        databaseReference.child("pesanan").child(idPesanan).child("statusPesanan").setValue("Booking Lapangan Berhasil, Silahkan tunggu SMS konfirmasi admin");
    }

    private void hitungLunas() {
        final String idPesanan = getIntent().getStringExtra("idPesanan");
        databaseReference.child("pesanan").child(idPesanan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pesanan dataPesanan = dataSnapshot.getValue(Pesanan.class);
                if (dataPesanan != null) {
                    double total = dataPesanan.getTotalPembayaran();
                    nominal.setText("Rp. " + String.valueOf(total));
                    jenisPembayaran = String.valueOf(rbLunas.getText());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void hitungUangMuka() {
        final String idPesanan = getIntent().getStringExtra("idPesanan");
        databaseReference.child("pesanan").child(idPesanan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pesanan dataPesanan = dataSnapshot.getValue(Pesanan.class);
                if (dataPesanan != null) {
                    double total = (dataPesanan.getTotalPembayaran() / 2);
                    nominal.setText("Rp. " + String.valueOf(total));
                    jenisPembayaran = String.valueOf(rbUangMuka.getText());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

/*    private String getEmail() {
        String idPesanan = getIntent().getStringExtra("idPesanan");
        databaseReference.child("pesanan").child(idPesanan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pesanan dataEmail = dataSnapshot.getValue(Pesanan.class);
                String email = dataEmail.getEmailTempatFutsal();
                emailTempatFutsal = email;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return emailTempatFutsal;
    }*/


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
