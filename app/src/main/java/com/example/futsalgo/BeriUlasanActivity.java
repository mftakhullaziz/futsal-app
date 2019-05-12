package com.example.futsalgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.futsalgo.Model.Pemesan;
import com.example.futsalgo.Model.Pesanan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BeriUlasanActivity extends AppCompatActivity {

    private TextView tvTempatFut;
    private TextView tvNamaPem;
    private TextView tvNomorTel;
    private RatingBar ratingBar;
    private EditText getUlasan;
    private Button btnSimpan;
    private DatabaseReference dbRef;
    private FirebaseAuth auth;
    private String IDPengguna;
    private String idUlasan;
    private Calendar calander;
    private SimpleDateFormat simpledateformat;
    private String Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beri_ulasan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.montserat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Give a Review");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tvTempatFut = findViewById(R.id.namaTempatFutsalx);
        tvNamaPem = findViewById(R.id.namaPemesanx);
        tvNomorTel = findViewById(R.id.nomorTeleponx);
        ratingBar = findViewById(R.id.ratingBar);
        getUlasan = findViewById(R.id.etUlasan);
        btnSimpan = findViewById(R.id.btnSimpan);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("pengguna");
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        IDPengguna = user.getUid();
        idUlasan = dbRef.push().getKey();

        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date = simpledateformat.format(calander.getTime());

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });

        getDataPemesan1();
        getDataPesananAsli();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanUlasan();
            }
        });
    }

    public void getDataPemesan1() {
        dbRef.child(IDPengguna).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Pemesan dataPemesan = dataSnapshot.getValue(Pemesan.class);
                if (dataPemesan != null) {
                    tvNamaPem.setText(dataPemesan.getNama());
                    tvNomorTel.setText(dataPemesan.getTelepon());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void getDataPesananAsli() {
        /*String idPesanan = getIntent().getStringExtra("idPesanan");*/

        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("pesanan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {
                    Pesanan dataPesanan = chidSnap.getValue(Pesanan.class);
                    tvTempatFut.setText(dataPesanan.getNameTempatFuts());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void simpanUlasan() {

        String idPesanan = getIntent().getStringExtra("idPesanan");
        String pemesan = tvNamaPem.getText().toString();
        String telepon = tvNomorTel.getText().toString();
        String rating = String.valueOf(ratingBar.getRating());
        String ulasan = getUlasan.getText().toString();

        if (TextUtils.isEmpty(ulasan)) {
            Toast.makeText(this, "Silahkan beri ulasan anda", Toast.LENGTH_LONG).show();
        } else {
            dbRef.child("ulasan").child(idUlasan).child("idUlasan").setValue(idUlasan);
            dbRef.child("ulasan").child(idUlasan).child("idPesanan").setValue(idPesanan);
            dbRef.child("ulasan").child(idUlasan).child("idPemesan").setValue(IDPengguna);
            dbRef.child("ulasan").child(idUlasan).child("namaPemesan").setValue(pemesan);
            dbRef.child("ulasan").child(idUlasan).child("nomorTelepon").setValue(telepon);
            dbRef.child("ulasan").child(idUlasan).child("rating").setValue(rating);
            dbRef.child("ulasan").child(idUlasan).child("ulasan").setValue(ulasan);
            dbRef.child("ulasan").child(idUlasan).child("timestamp").setValue(Date);
            Toast.makeText(this, "Ulasan berhasil disimpan", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
