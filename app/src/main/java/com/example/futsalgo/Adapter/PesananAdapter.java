package com.example.futsalgo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.futsalgo.DetailPesananActivity;
import com.example.futsalgo.HistoriPesananActivity;
import com.example.futsalgo.Model.Pesanan;
import com.example.futsalgo.R;

import java.util.List;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.ViewHolder> {

    Context context;
    private List<Pesanan> listPesanan;
    LayoutInflater mInflater;

    public PesananAdapter(Context context, List<Pesanan> listPesanan) {
        this.context = context;
        this.listPesanan = listPesanan;
        mInflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView invoice;
        /*TextView status;*/

        public ViewHolder(View itemView) {
            super(itemView);
            invoice = itemView.findViewById(R.id.tvInvoice);
            /*status = itemView.findViewById(R.id.tvStatus);*/
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pesanan_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Pesanan dataPesanan = listPesanan.get(position);
        final String idPesanan = dataPesanan.getIdPesanan();
        /*final String idLapangan = dataPesanan.getIdLapangan();
        final String idPetugas = dataPesanan.getIdPetugas();*/
        final String status = dataPesanan.getStatusPesanan();
        final String invoice = dataPesanan.getInvoice();

        /*holder.status.setText(status);*/
        holder.invoice.setText(invoice);

       /* holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPesananActivity.class);
                intent.putExtra("idPesanan", idPesanan);
                intent.putExtra("idLapangan", idLapangan);
                intent.putExtra("idPetugas", idPetugas);
                context.startActivity(intent);
            }
        });
*/
        holder.invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HistoriPesananActivity.class);
                intent.putExtra("idPesanan", idPesanan);
   /*             intent.putExtra("idLapangan", idLapangan);
                intent.putExtra("idPetugas", idPetugas);*/
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPesanan.size();
    }
}
