package com.example.futsalgo.MenuTempatFutsal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.futsalgo.Adapter.FasilitasAdapter;
import com.example.futsalgo.BuatPesananActivity;
import com.example.futsalgo.Maps.MapsActivity;
import com.example.futsalgo.Model.Admin;
import com.example.futsalgo.Model.Deskripsi;
import com.example.futsalgo.Model.Fasilitas;
import com.example.futsalgo.Model.Pemesan;
import com.example.futsalgo.PilihTempatFutsalActivity;
import com.example.futsalgo.R;
import com.example.futsalgo.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailTempatFutsalActivity extends AppCompatActivity {

    private ImageView img_View;
    private TextView post_Name, post_Des_Lapangan, post_Tel_Lapangan, FasPost, faAlamat;
    private TextView l1,namaBank,nomorBank,h1,namaAuthor,h3;
    private DatabaseReference dbRef;
    private Button booking;


    private Context context;

    RecyclerView recyclerViewFasilitas, recyclerViewLapangan;

    RecyclerView.Adapter adapter;

    List<Fasilitas> fasilitasList = new ArrayList<>();
   /* List<Lapangan> lapanganList = new ArrayList<>();*/

    /*private List<Upload> mUploads;*/

    private String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tempat_futsal);

        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.montserat);
        getSupportActionBar().setTitle("Details Indoor Stadium");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        img_View = findViewById(R.id.imageVIEW);
        post_Name = findViewById(R.id.tvNamaLapangan);
        post_Des_Lapangan = findViewById(R.id.tvDeskripsi);
        post_Tel_Lapangan= findViewById(R.id.tvKontak);
        FasPost = findViewById(R.id.tvFas);
        faAlamat = findViewById(R.id.tvAlamatLPG);
        booking = findViewById(R.id.bookingLPG);
        namaBank = findViewById(R.id.tvNBank);
        nomorBank = findViewById(R.id.tvNoBank);
        namaAuthor = findViewById(R.id.tvNOwner);
        l1 = findViewById(R.id.tvLPG1);
        h1 = findViewById(R.id.tvHarga1);


        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaStadion = getIntent().getStringExtra("imgName");
                String namaTempat = getIntent().getStringExtra("lapangan_1");
                String hargaLapangan = getIntent().getStringExtra("harga_lapangan_1");
                String name_b = getIntent().getStringExtra("nama_bank");
                String nomor_b = getIntent().getStringExtra("nomor_rekening");
                String name_bo = getIntent().getStringExtra("nama_pemilik");
                /*startActivity(new Intent(DetailTempatFutsalActivity.this, BuatPesananActivity.class));*/

                Intent intent = new Intent(DetailTempatFutsalActivity.this, BuatPesananActivity.class);
                intent.putExtra("imgName", namaStadion);
                intent.putExtra("lapangan_1", namaTempat);
                intent.putExtra("harga_lapangan_1", hargaLapangan);
                intent.putExtra("nama_bank", name_b);
                intent.putExtra("nomor_rekening", nomor_b);
                intent.putExtra("nama_pemilik", name_bo);
                startActivity(intent);
            }
        });

        getDataFutsal();
        /*getDataFasilitas();*/


        post_Tel_Lapangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWhatsApp();
            }
        });

  /*      booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailTempatFutsalActivity.this, PilihTempatFutsalActivity.class));
            }
        });*/


        faAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailTempatFutsalActivity.this, MapsActivity.class));
            }
        });

    }

    /*Ambil Data Dari Child uploads*/
    public void getDataFutsal() {
        String post = getIntent().getExtras().getString("imgUrl");
        Picasso.with(context)
                .load(post)
                .fit()
                .centerCrop()
                .into(img_View);
        String postName = getIntent().getExtras().getString("imgName");
        post_Name.setText(postName);

        String postDeskripsi = getIntent().getExtras().getString("deskripsi_lapangan");
        post_Des_Lapangan.setText(postDeskripsi);

        String postTelep = getIntent().getExtras().getString("nomor_tlp_lapangan");
        post_Tel_Lapangan.setText(postTelep);

        String postFast = getIntent().getExtras().getString("fasilitas_tersedia");
        FasPost.setText(postFast);

        String postAlamat = getIntent().getExtras().getString("alamat_lapangan");
        faAlamat.setText(postAlamat);

        String lp1 = getIntent().getExtras().getString("lapangan_1");
        l1.setText(lp1);

        String Hr1 = getIntent().getExtras().getString("harga_lapangan_1");
        h1.setText("Rp. "+Hr1);

        String NB = getIntent().getExtras().getString("nama_bank");
        namaBank.setText(NB);

        String NOOB = getIntent().getExtras().getString("nomor_rekening");
        nomorBank.setText(NOOB);

        String thor = getIntent().getExtras().getString("nama_pemilik");
        namaAuthor.setText(thor);

    }


    /*Implementasi API Whatsapp*/
    private void goToWhatsApp() {
        String postTelep = getIntent().getExtras().getString("nomor_tlp_lapangan");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" +postTelep));
        startActivity(intent);
    }





    /*private void getDataFasilitas() {

        Bundle bundle = getIntent().getExtras();
        final String idAdministrator = bundle.getString("idAdministrator");

        dbRef = FirebaseDatabase.getInstance().getReference("fasilitas").child(idAdministrator);


        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fasilitasList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Fasilitas fasilitas = postSnapshot.getValue(Fasilitas.class);

                    fasilitasList.add(fasilitas);
                }

                adapter = new FasilitasAdapter(context, fasilitasList);

                recyclerViewFasilitas.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
