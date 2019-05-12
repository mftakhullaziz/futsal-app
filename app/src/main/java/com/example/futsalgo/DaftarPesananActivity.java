package com.example.futsalgo;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.futsalgo.Model.Pesanan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.example.futsalgo.Adapter.PesananAdapter;

import java.util.ArrayList;
import java.util.List;

public class DaftarPesananActivity extends AppCompatActivity {

    // Creating DatabaseReference.
    DatabaseReference dbRef;

    // Creating RecyclerView.
    RecyclerView recyclerView;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter;

    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating List of ImageUploadInfo class.
    List<Pesanan> listPesanan = new ArrayList<>();

    private FirebaseAuth auth;
    private String IDPengguna;
    private Toolbar toolbar;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pesanan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.montserat);
        getSupportActionBar().setTitle("List Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        context = this;

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        IDPengguna = user.getUid();

        // Assign id to RecyclerView.
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(DaftarPesananActivity.this));

        // Assign activity this to progress dialog.
        progressDialog = new ProgressDialog(DaftarPesananActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Memuat...");

        // Showing progress dialog.
        progressDialog.show();

        getDataPesanan();


    }

    private void getDataPesanan() {
        //database path
        dbRef = FirebaseDatabase.getInstance().getReference("pesanan");

        Query query = dbRef.orderByChild("idPengguna").equalTo(IDPengguna);

        // Adding Add Value Event Listener to databaseReference.
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listPesanan.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Pesanan dataPesanan = postSnapshot.getValue(Pesanan.class);
                    listPesanan.add(dataPesanan);
                }

                adapter = new PesananAdapter(context, listPesanan);

                recyclerView.setAdapter(adapter);

                // Hiding the progress dialog.
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
                progressDialog.dismiss();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
