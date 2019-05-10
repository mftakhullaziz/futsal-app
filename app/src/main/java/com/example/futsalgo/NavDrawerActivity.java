package com.example.futsalgo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;


import com.example.futsalgo.Fragments_Menu.*;
import com.example.futsalgo.Model.Pemesan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NavDrawerActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference dbRef;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private String IDPengguna, name, email;
    String PersonName , PersonEmail;
    private TextView textViewNama, textViewEmail;

    private Toolbar mToolbar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer_main);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.container, new Home_Fragment());
        tx.commit();
        LinearLayout headerImageView= (LinearLayout) findViewById(R.id.headerView);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextAppearance(this, R.style.montserat);
        getSupportActionBar().setElevation(0);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("pengguna");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        assert user != null;
        IDPengguna = user.getUid();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        textViewNama = (TextView)hView.findViewById(R.id.getname);
        textViewEmail = (TextView)hView.findViewById(R.id.getemail);
        ImageView nav_photo = (ImageView) hView.findViewById(R.id.getphoto);
        getDataPemesan();
        Glide.with(this).load(user.getPhotoUrl()).into(nav_photo);

        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

    }

    public void getDataPemesan() {
        dbRef.child(IDPengguna).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pemesan dataPemesan = dataSnapshot.getValue(Pemesan.class);
                if (dataPemesan != null) {
                    textViewNama.setText(dataPemesan.getNama());
                    textViewEmail.setText(dataPemesan.getEmail());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_drawer_class, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home){
            getSupportActionBar().setTitle(R.string.toolbar_title_home);
            item.setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Home_Fragment()).commit();

        } else if (id == R.id.nav_profil) {

            getSupportActionBar().setTitle(R.string.toolbar_title_profil);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Profile_Fragment()).commit();

        } else if (id == R.id.nav_help) {

            getSupportActionBar().setTitle(R.string.toolbar_title_help_and_feedback);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Help_Fragment()).commit();

        } else if (id == R.id.nav_signout) {
            auth.signOut();
            startActivity(new Intent(NavDrawerActivity.this, WelcomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
