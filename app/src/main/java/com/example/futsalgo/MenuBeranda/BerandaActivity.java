package com.example.futsalgo.MenuBeranda;
/*

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.futsalgo.Adapter.TempatFutsalAdapter;
import com.example.futsalgo.Model.TempatFutsal;
import com.example.futsalgo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BerandaActivity extends AppCompatActivity {
    DatabaseReference dbRef;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;

    List<TempatFutsal> tempatFutsalList = new ArrayList<>();

    private FirebaseAuth auth;
    private String idPengguna;
    private Toolbar toolbar;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daftar Lapangan Futsal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        context = this;

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        idPengguna = user.getUid();

        // Assign id to RecyclerView.
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(BerandaActivity.this));

        // Assign activity this to progress dialog.
        progressDialog = new ProgressDialog(BerandaActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Memuat...");

        // Showing progress dialog.
        progressDialog.show();

        getDataTempatFutsal();
    }

    private void getDataTempatFutsal() {
        //database path
        dbRef = FirebaseDatabase.getInstance().getReference("tempatFutsal");

        // Adding Add Value Event Listener to databaseReference.
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                tempatFutsalList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    TempatFutsal dataTempatFutsal = postSnapshot.getValue(TempatFutsal.class);
                    tempatFutsalList.add(dataTempatFutsal);
                }

                adapter = new TempatFutsalAdapter(context, tempatFutsalList);

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
*/
