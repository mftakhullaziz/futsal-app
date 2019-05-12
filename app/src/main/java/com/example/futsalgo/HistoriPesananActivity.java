package com.example.futsalgo;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.futsalgo.Model.Pemesan;
import com.example.futsalgo.Model.Pesanan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HistoriPesananActivity extends AppCompatActivity {

    private TextView invoice, statusPesanan,
            namaPemesan, nomorTelepon,
            namaTempatFutsal, namaLapangan,
            tanggalPesan, durasiSewa,
            totalPembayaran, tvNamaBank,
            tvNomorRekening, tvNamaRekening,
            countdownTimerText;
    private Button btnUnggah, btnUlasan;
    private DatabaseReference dbRef;

    private static final long START_TIME_IN_MILLIS = 1800000;
    private static CountDownTimer countDownTimer;

    private boolean mTimerRunning;

    private String IDPengguna;

    private TextView ENam, ENoPhon;
    private TextView NamaBankP, NBankP, NamaPemilikP;

    private TextView TGL, JAM1, JAM2;


    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_pesanan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.montserat);
        getSupportActionBar().setTitle("History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("pengguna");
        dbRef.keepSynced(true);
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        IDPengguna = user.getUid();


        invoice = findViewById(R.id.tvinvoice);
        ENam = findViewById(R.id.tvNamaPemesan1);
        ENoPhon = findViewById(R.id.tvNomorPemesan1);
        namaTempatFutsal = findViewById(R.id.tvNamaTempatFutsal1);
        namaLapangan = findViewById(R.id.tvNamaLapangan1);
        totalPembayaran = findViewById(R.id.tvTotal);
        statusPesanan = findViewById(R.id.tvStatus);

        NamaBankP = findViewById(R.id.tvNamaBank);
        NBankP = findViewById(R.id.tvNomorRekeningP);
        NamaPemilikP = findViewById(R.id.tvNamaRekeningP);

        TGL = findViewById(R.id.tvTanggalPesan);
        JAM1 = findViewById(R.id.tvDurasi1);

        getDataPemesan();
        getDataPesananAsli();

        btnUlasan = findViewById(R.id.btnBeriUlasan);
        btnUlasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUlasan();
            }
        });

        btnUnggah = findViewById(R.id.btnUnggahBukti);
        btnUnggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUnggah();
            }
        });
    }

    public void getDataPemesan() {
        dbRef.child(IDPengguna).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Pemesan dataPemesan = dataSnapshot.getValue(Pemesan.class);
                if (dataPemesan != null) {
                    ENam.setText(dataPemesan.getNama());
                    ENoPhon.setText(dataPemesan.getTelepon());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getDataPesananAsli() {
        /*String idPesanan = dbRef.getKey();*/
        /*dbRef = FirebaseDatabase.getInstance().getReference();*/

        Bundle bundle = getIntent().getExtras();
        final String idPesanan = bundle.getString("idPesanan");

        dbRef = FirebaseDatabase.getInstance().getReference("pesanan").child(idPesanan);


        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                invoice.setText(dataSnapshot.child("invoice").getValue().toString());
                TGL.setText(dataSnapshot.child("tanggalPesan").getValue().toString());
                JAM1.setText("Jam " + (dataSnapshot.child("jamMulai").getValue().toString() + " - " + (dataSnapshot.child("jamSelesai").getValue().toString() + " WIB")));
                totalPembayaran.setText(String.valueOf("Rp. "+dataSnapshot.child("totalPembayaran").getValue().toString()));
                namaTempatFutsal.setText(dataSnapshot.child("nameTempatFuts").getValue().toString());
                namaLapangan.setText(dataSnapshot.child("nameLapanganFuts").getValue().toString());
                NamaBankP.setText(dataSnapshot.child("transfer_ke_bank").getValue().toString());
                NBankP.setText(dataSnapshot.child("transfer_ke_no_rekening").getValue().toString());
                NamaPemilikP.setText(dataSnapshot.child("transfer_ke_nama_pemilik").getValue().toString());
                statusPesanan.setText(dataSnapshot.child("statusPesanan").getValue().toString());



/*

                for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {
                    Pesanan dataPesanan = chidSnap.getValue(Pesanan.class);
                    invoice.setText(dataPesanan.getInvoice());
                    TGL.setText(dataPesanan.getTanggalPesan());
                    JAM1.setText("Jam " + (dataPesanan.getJamMulai() + " - " + (dataPesanan.getJamSelesai() + " WIB")));
                    totalPembayaran.setText(String.valueOf(dataPesanan.getTotalPembayaran()));
                    namaTempatFutsal.setText(dataPesanan.getNameTempatFuts());
                    namaLapangan.setText(dataPesanan.getNameLapanganFuts());
                    NamaBankP.setText(dataPesanan.getTransfer_ke_bank());
                    NBankP.setText(dataPesanan.getTransfer_ke_no_rekening());
                    NamaPemilikP.setText(dataPesanan.getTransfer_ke_nama_pemilik());
                    Log.v("tmz",""+ chidSnap.getKey()); //displays the key for the node
                    Log.v("tmz",""+ chidSnap.child("market_name").getValue());   //gives the value for given keyname
                }

*/

/*
                Pesanan dataPesanan = dataSnapshot.getValue(Pesanan.class);
                if (dataPesanan != null) {
                    invoice.setText(dataPesanan.getInvoice());
                    *//*statusPesanan.setText(dataPesanan.getStatusPesanan());
                    namaPemesan.setText(dataPesanan.getNamaPemesan());
                    nomorTelepon.setText(dataPesanan.getNoTelepon());
                    namaTempatFutsal.setText(dataPesanan.getNamaTempatFutsal());
                    namaLapangan.setText(dataPesanan.getNamaLapangan());
                    tanggalPesan.setText(dataPesanan.getTanggalPesan());
                    durasiSewa.setText("Jam " + (dataPesanan.getJamMulai() + " - " + (dataPesanan.getJamSelesai() + " WIB")));
                    totalPembayaran.setText("Rp. " + String.valueOf(dataPesanan.getTotalPembayaran()));*//*
                }*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void gotoUlasan() {
        String idPesanan = getIntent().getStringExtra("idPesanan");
        String tempatFutsal = namaTempatFutsal.getText().toString();
        String pemesan = ENam.getText().toString();
        String telepon = ENoPhon.getText().toString();

        Intent intent = new Intent(HistoriPesananActivity.this, BeriUlasanActivity.class);
        intent.putExtra("idPesanan", idPesanan);
        intent.putExtra("namaTempatFutsal", tempatFutsal);
        intent.putExtra("namaPemesan", pemesan);
        intent.putExtra("nomorTelepon", telepon);
        startActivity(intent);
    }

    private void gotoUnggah() {
        String idPesanan = getIntent().getStringExtra("idPesanan");
        String pemesan = ENam.getText().toString();

        Intent intent = new Intent(HistoriPesananActivity.this, UnggahBuktiActivity.class);
        intent.putExtra("idPesanan", idPesanan);
        intent.putExtra("namaPemesan", pemesan);
        startActivity(intent);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
