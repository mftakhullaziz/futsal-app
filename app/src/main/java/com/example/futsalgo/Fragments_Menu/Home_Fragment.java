package com.example.futsalgo.Fragments_Menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.futsalgo.AboutUsActivity;
import com.example.futsalgo.DaftarPesananActivity;
import com.example.futsalgo.LihatUlasanActivity;
import com.example.futsalgo.Maps.MapsActivity;
import com.example.futsalgo.MenuBeranda.ViewImageActivity;
import com.example.futsalgo.R;

public class Home_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;


    GridView androidGridView;
    Context context;
    public Home_Fragment() {
        // Required empty public constructor
    }

    public static Home_Fragment newInstance(String param1, String param2) {
        Home_Fragment fragment = new Home_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootV = inflater.inflate(R.layout.fragment_home, container, false);


        CardView BookingLapangan=rootV.findViewById(R.id.card1);
        BookingLapangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start1 = new Intent(getActivity(), ViewImageActivity.class);
                startActivity(start1);
            }
        });

        CardView StatusBooking=rootV.findViewById(R.id.card2);
        StatusBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start2 = new Intent(getActivity(), DaftarPesananActivity.class);
                startActivity(start2);
            }
        });

        CardView Location=rootV.findViewById(R.id.card3);
        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start3 = new Intent(getActivity(), MapsActivity.class);
                startActivity(start3);
            }
        });

        CardView Reviews=rootV.findViewById(R.id.card4);
        Reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start4 = new Intent(getActivity(), LihatUlasanActivity.class);
                startActivity(start4);
            }
        });

       /* CardView kalenders=rootV.findViewById(R.id.card5);
        kalenders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*Intent start1 = new Intent(getActivity(), BerandaActivity.class);
                startActivity(start1);*//*
            }
        });*/

        CardView About=rootV.findViewById(R.id.card6);
        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start5 = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(start5);
            }
        });

        return rootV;
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
        void onFragmentInteraction(Uri uri);
    }

}
