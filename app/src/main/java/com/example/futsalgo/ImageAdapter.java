package com.example.futsalgo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.futsalgo.MenuTempatFutsal.DetailTempatFutsalActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;

    public ImageAdapter(Context context, List<Upload> uploads)
    {
        mContext=context;
        mUploads=uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(mContext).inflate(R.layout.futsal_row, viewGroup,false);
        return  new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, final int i) {
        Upload uploadCur=mUploads.get(i);
        final String idAdmin = uploadCur.getIdAdministrator();

        imageViewHolder.img_description.setText(uploadCur.getImgName());
        Picasso.with(mContext)
                .load(uploadCur.getImgUrl())
                .fit()
                .centerCrop()
                .into(imageViewHolder.image_view);

        imageViewHolder.image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailTempatFutsalActivity.class);
                intent.putExtra("imgName", mUploads.get(i).getImgName());
                intent.putExtra("imgUrl",mUploads.get(i).getImgUrl());
                intent.putExtra("deskripsi_lapangan",mUploads.get(i).getDeskripsi_lapangan());
                intent.putExtra("nomor_tlp_lapangan",mUploads.get(i).getNomor_tlp_lapangan());
                intent.putExtra("fasilitas_tersedia",mUploads.get(i).getFasilitas_tersedia());
                intent.putExtra("alamat_lapangan",mUploads.get(i).getAlamat_lapangan());

                intent.putExtra("lapangan_1",mUploads.get(i).getLapangan_1());
                intent.putExtra("lapangan_2",mUploads.get(i).getLapangan_2());
                intent.putExtra("lapangan_3",mUploads.get(i).getLapangan_3());

                intent.putExtra("harga_lapangan_1",mUploads.get(i).getHarga_lapangan_1());
                intent.putExtra("harga_lapangan_2",mUploads.get(i).getHarga_lapangan_2());
                intent.putExtra("harga_lapangan_3",mUploads.get(i).getHarga_lapangan_3());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView img_description;
        public ImageView image_view;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            img_description=itemView.findViewById(R.id.namaTempatFutsal);
            image_view=itemView.findViewById(R.id.fotoTempatFutsal);
        }
    }
}
