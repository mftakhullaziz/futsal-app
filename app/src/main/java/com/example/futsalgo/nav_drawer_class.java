package com.example.futsalgo;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class nav_drawer_class extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference dbRef;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private String idPengguna, name, email;
    String PersonName , PersonEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.container, new Home_Fragment());
        tx.commit();


        LinearLayout headerImageView= (LinearLayout) findViewById(R.id.headerView);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.montserat);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        assert user != null;

        idPengguna = user.getUid();
        PersonName = user.getDisplayName();
        PersonEmail = user.getEmail();

//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.getname);
        TextView nav_email = (TextView)hView.findViewById(R.id.getemail);
        ImageView nav_photo = (ImageView) hView.findViewById(R.id.getphoto);
        nav_user.setText(PersonName);
        nav_email.setText(PersonEmail);
        Glide.with(this).load(user.getPhotoUrl()).into(nav_photo);

        navigationView.setNavigationItemSelectedListener(this);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);



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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer_class, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
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
            startActivity(new Intent(nav_drawer_class.this, welcome.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
