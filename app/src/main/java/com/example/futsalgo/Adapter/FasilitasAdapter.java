package com.example.futsalgo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.futsalgo.Model.Fasilitas;
import com.example.futsalgo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FasilitasAdapter extends RecyclerView.Adapter<FasilitasAdapter.ViewHolder> implements View.OnClickListener {

    Context context;
    LayoutInflater mInflater;
    private List<Fasilitas> fasilitasList;
    private DatabaseReference dbRef;

    public FasilitasAdapter(Context context, List<Fasilitas> fasilitasList) {
        this.context = context;
        this.fasilitasList = fasilitasList;

        mInflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvFasilitas;

        public ViewHolder(View itemView) {
            super(itemView);

            tvFasilitas = (TextView) itemView.findViewById(R.id.tvFasilitas);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fasilitas_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        dbRef = FirebaseDatabase.getInstance().getReference("fasilitas");

        final String namaFasilitas = fasilitasList.get(position).getFasilitas();

        holder.tvFasilitas.setText(namaFasilitas);
    }

    @Override
    public int getItemCount() {
        return fasilitasList.size();
    }

    @Override
    public void onClick(View v) {

    }
}
