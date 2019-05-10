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
    private TextView l1,l2,l3,h1,h2,h3;
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

        l1 = findViewById(R.id.tvLPG1);
     /*   l2 = findViewById(R.id.tvLPG2);
        l3 = findViewById(R.id.tvLPG3);*/

        h1 = findViewById(R.id.tvHarga1);
        /*h2 = findViewById(R.id.tvHarga2);
        h3 = findViewById(R.id.tvHarga3);*/



        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaStadion = getIntent().getStringExtra("imgName");
                String namaTempat = getIntent().getStringExtra("lapangan_1");
                String hargaLapangan = getIntent().getStringExtra("harga_lapangan_1");
                /*startActivity(new Intent(DetailTempatFutsalActivity.this, BuatPesananActivity.class));*/

                Intent intent = new Intent(DetailTempatFutsalActivity.this, BuatPesananActivity.class);
                intent.putExtra("imgName", namaStadion);
                intent.putExtra("lapangan_1", namaTempat);
                intent.putExtra("harga_lapangan_1", hargaLapangan);
                startActivity(intent);
            }
        });

/*
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaStadion2 = getIntent().getStringExtra("imgName");
                String namaTempat2 = getIntent().getStringExtra("lapangan_2");
                String hargaLapangan2 = getIntent().getStringExtra("harga_lapangan_2");
                */
/*startActivity(new Intent(DetailTempatFutsalActivity.this, BuatPesananActivity.class));*//*


                Intent intent = new Intent(DetailTempatFutsalActivity.this, BuatPesananActivity.class);
                intent.putExtra("imgName", namaStadion2);
                intent.putExtra("lapangan_2", namaTempat2);
                intent.putExtra("harga_lapangan_2", hargaLapangan2);
                startActivity(intent);
            }
        });

        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaStadion3 = getIntent().getStringExtra("imgName");
                String namaTempat3 = getIntent().getStringExtra("lapangan_3");
                String hargaLapangan3 = getIntent().getStringExtra("harga_lapangan_3");
                */
/*startActivity(new Intent(DetailTempatFutsalActivity.this, BuatPesananActivity.class));*//*


                Intent intent = new Intent(DetailTempatFutsalActivity.this, BuatPesananActivity.class);
                intent.putExtra("imgName", namaStadion3);
                intent.putExtra("lapangan_3", namaTempat3);
                intent.putExtra("harga_lapangan_3", hargaLapangan3);
                startActivity(intent);
            }
        });
*/





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

        /*String lp2 = getIntent().getExtras().getString("lapangan_2");
        l2.setText(lp2);

        String lp3 = getIntent().getExtras().getString("lapangan_3");
        l3.setText(lp3);*/

        String Hr1 = getIntent().getExtras().getString("harga_lapangan_1");
        h1.setText("Rp. "+Hr1);

        /*String Hr2 = getIntent().getExtras().getString("harga_lapangan_2");
        h2.setText(Hr2);

        String Hr3 = getIntent().getExtras().getString("harga_lapangan_3");
        h3.setText(Hr3);*/

    }


    /*Implementasi API Whatsapp*/
    private void goToWhatsApp() {
        String postTelep = getIntent().getExtras().getString("nomor_tlp_lapangan");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" +postTelep));
        startActivity(intent);
    }

    private void getDataFasilitas() {

        String id = getIntent().getStringExtra("IdAdmin");
        dbRef = FirebaseDatabase.getInstance().getReference("fasilitas");
        // Adding Add Value Event Listener to databaseReference.
        dbRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                fasilitasList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

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
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
