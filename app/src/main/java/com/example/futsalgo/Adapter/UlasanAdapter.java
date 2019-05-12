package com.example.futsalgo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.futsalgo.Model.Ulasan;
import com.example.futsalgo.R;

import java.util.List;

public class UlasanAdapter extends RecyclerView.Adapter<UlasanAdapter.ViewHolder> {

    Context context;
    private List<Ulasan> ulasanList;
    LayoutInflater mInflater;

    public UlasanAdapter(Context context, List<Ulasan> ulasanList) {
        this.context = context;
        this.ulasanList = ulasanList;
        mInflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView rating;
        TextView namaPemesan;
        TextView ulasan;
        TextView timestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            namaPemesan = itemView.findViewById(R.id.tvNamaPemesan);
            rating = itemView.findViewById(R.id.tvRating);
            ulasan = itemView.findViewById(R.id.tvUlasan);
            timestamp = itemView.findViewById(R.id.tvTimestamp);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ulasan_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String namaPemesan = ulasanList.get(position).getNamaPemesan();
        final String rating = ulasanList.get(position).getRating();
        final String ulasan = ulasanList.get(position).getUlasan();
        final String timestamp = ulasanList.get(position).getTimestamp();

        holder.namaPemesan.setText(namaPemesan);
        holder.rating.setText("Nilai " + rating + "/5");
        holder.ulasan.setText(ulasan);
        holder.timestamp.setText(timestamp);
    }

    @Override
    public int getItemCount() {
        return ulasanList.size();
    }
}
