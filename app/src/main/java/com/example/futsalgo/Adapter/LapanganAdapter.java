package com.example.futsalgo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.futsalgo.Model.Lapangan;
import com.example.futsalgo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LapanganAdapter extends RecyclerView.Adapter<LapanganAdapter.ViewHolder> implements View.OnClickListener {

    Context context;
    LayoutInflater mInflater;
    private List<Lapangan> lapanganList;
    private DatabaseReference dbRef;

    public LapanganAdapter(Context context, List<Lapangan> lapanganList) {
        this.context = context;
        this.lapanganList = lapanganList;

        mInflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNamaLapanganF;
        public TextView tvHargaF;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNamaLapanganF = (TextView) itemView.findViewById(R.id.tvNamaLapanganPilih);
            tvHargaF = (TextView) itemView.findViewById(R.id.tvHargaLPG);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lapangan_pilih_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        dbRef = FirebaseDatabase.getInstance().getReference();

        final String idPetugas = lapanganList.get(position).getIdAdministrator();
        final String idLapangan = lapanganList.get(position).getIdLapangan();
        final String namaLapangan = lapanganList.get(position).getNamaLapangan();
        final double hargaSewa = lapanganList.get(position).getHargaSewa();

        holder.tvNamaLapanganF.setText(namaLapangan);
        holder.tvHargaF.setText("Rp. " + String.valueOf(hargaSewa));

     /*   holder.tvNamaLapangan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.opsi_dialog);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

                Button editButton = (Button) dialog.findViewById(R.id.ubah);
                Button delButton = (Button) dialog.findViewById(R.id.hapus);

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, UbahLapanganActivity.class);
                        intent.putExtra("idLapangan", lapanganList.get(position).getIdLapangan());
                        context.startActivity(intent);
                    }
                });

                delButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        dbRef.child("lapangan").child(idPetugas).child(idLapangan).removeValue();
                        dbRef.child("tempatFutsal").child(idPetugas).child("lapangan").child(idLapangan).removeValue();
                        Toast.makeText(context, "Data Berhasil Dihapus.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, RekeningActivity.class);
                        context.startActivity(intent);
                    }
                });
                return true;
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return lapanganList.size();
    }

    @Override
    public void onClick(View v) {

    }
}
