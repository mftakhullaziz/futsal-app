package com.example.futsalgo;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.futsalgo.Adapter.LapanganAdapter;
import com.example.futsalgo.Model.Lapangan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PilihTempatFutsalActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnTambah;
    private FirebaseAuth auth;
    private String idAdministrator;
    private DatabaseReference dbRef;
    private ProgressDialog mProgress;

    // Creating RecyclerView.
    RecyclerView recyclerView;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter;

    // Creating List of ImageUploadInfo class.
    List<Lapangan> lapanganList = new ArrayList<>();

    Context context;

    private FloatingActionButton fab;
    private EditText Harga, NamaLapangan;
    private Spinner spinnerKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_tempat_futsal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.montserat);
        getSupportActionBar().setTitle("Select Futsal Court");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        context = this;

        // Assign id to RecyclerView.
        recyclerView = (RecyclerView) findViewById(R.id.recycler_viewLPG);

        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(PilihTempatFutsalActivity.this));

        mProgress = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        idAdministrator = user.getUid();

        getDataLapangan();
    }

   /* private void getDataLapangan() {
        dbRef = FirebaseDatabase.getInstance().getReference("lapangan");
        // Adding Add Value Event Listener to databaseReference.
        *//*dbRef.child(idAdministrator).getKey().toString();*//*
        dbRef.child(idAdministrator).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                lapanganList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Lapangan lapangan = postSnapshot.getValue(Lapangan.class);

                    lapanganList.add(lapangan);
                }

                adapter = new LapanganAdapter(context, lapanganList);

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/


    private void getDataLapangan() {
        dbRef = FirebaseDatabase.getInstance().getReference("lapangan");
        // Adding Add Value Event Listener to databaseReference.
        /*dbRef.child(idAdministrator).getKey().toString();*/
        dbRef.child(idAdministrator).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                lapanganList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Lapangan lapangan = postSnapshot.getValue(Lapangan.class);

                    lapanganList.add(lapangan);
                }

                adapter = new LapanganAdapter(context, lapanganList);

                recyclerView.setAdapter(adapter);
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
