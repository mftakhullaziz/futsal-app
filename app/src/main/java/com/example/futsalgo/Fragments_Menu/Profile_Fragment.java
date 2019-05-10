package com.example.futsalgo.Fragments_Menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.futsalgo.R;
import com.example.futsalgo.Activities.UbahProfilActivity;
import com.example.futsalgo.Model.Pemesan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile_Fragment extends Fragment {

    private DatabaseReference dbRef;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private String IDPengguna, name, email, noTelepon;
    String PersonName , PersonEmail, PersonAlamat, PersonTelepon;

    private String idPemesan;

    private TextView textViewNama, textViewEmail, textViewNoTelepon, textViewAlamat;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    Context context;

    private OnFragmentInteractionListener mListener;

    public Profile_Fragment() {
        // Required empty public constructor
    }


    public static Profile_Fragment newInstance(String param1, String param2) {
        Profile_Fragment fragment = new Profile_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        context = rootView.getContext();

        textViewNama = (TextView) rootView.findViewById(R.id.tvNama);
        textViewEmail = (TextView) rootView.findViewById(R.id.tvEmail);
        textViewAlamat = (TextView) rootView.findViewById(R.id.tvAlamat);
        textViewNoTelepon = (TextView) rootView.findViewById(R.id.tvNotelp);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("pengguna");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        IDPengguna = user.getUid();

        /*auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        assert user != null;

        idPengguna = user.getUid();
        PersonName = user.getDisplayName();
        PersonEmail = user.getEmail();
        PersonTelepon = user.getPhoneNumber();

        TextView nav_user = (TextView)rootView.findViewById(R.id.tvNama);
        TextView nav_email = (TextView)rootView.findViewById(R.id.tvEmail);
        TextView nav_telepon = (TextView)rootView.findViewById(R.id.tvNotelp);
        ImageView nav_photo = (ImageView) rootView.findViewById(R.id.fotoProfil_user);
        nav_user.setText(PersonName);
        nav_email.setText(PersonEmail);
        nav_telepon.setText(PersonTelepon);
        Glide.with(this).load(user.getPhotoUrl()).into(nav_photo);*/

        ImageView nav_photo = (ImageView) rootView.findViewById(R.id.fotoProfil_user);
        Glide.with(this).load(user.getPhotoUrl()).into(nav_photo);

        getDataPemesan();

        Button UbahProfil = (Button) rootView.findViewById(R.id.btn_ubah);
            UbahProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UbahProfilActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    public void getDataPemesan() {
        dbRef.child(IDPengguna).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pemesan dataPemesan = dataSnapshot.getValue(Pemesan.class);
                if (dataPemesan != null) {
                    textViewNama.setText(dataPemesan.getNama());
                    textViewEmail.setText(dataPemesan.getEmail());
                    textViewAlamat.setText(dataPemesan.getAlamat());
                    textViewNoTelepon.setText(dataPemesan.getTelepon());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
