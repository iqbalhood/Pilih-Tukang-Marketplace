package com.zone.caritukang.adapter;





/**
 * Created by Mohammad Iqbal on 9/7/2016.
 * Email : iqbalhood@gmail.com
 * Ini adalah fungsi setting adapter untuk menyiapkan data yang akan ditampilkan di
 * fragment
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.zone.caritukang.R;
import com.zone.caritukang.setget.Jasa;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class JasaAdapter extends ArrayAdapter<Jasa> {
    ArrayList<Jasa> jasaList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    //firebase storage reference
    private StorageReference storageReference;

    public JasaAdapter(Context context, int resource, ArrayList<Jasa> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        jasaList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageview    = (ImageView) v.findViewById(R.id.imgJasa);
            holder.imageProduk    = (ImageView) v.findViewById(R.id.img_jasa);
            holder.tvNama       = (TextView) v.findViewById(R.id.namaJasa);
            holder.tvDetail     = (TextView) v.findViewById(R.id.detailJasa);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference(jasaList.get(position).getFoto());
        StorageReference storageRef2 = storage.getReference(jasaList.get(position).getFproduk());

        //System.out.println(" FOTO GETTTT -- >"+ jasaList.get(position).getFoto());

     //   StorageReference riversRef = storageReference.child(String.valueOf(jasaList.get(position).getFoto()));
        holder.imageview.setImageResource(R.drawable.tukang);

        Glide.with(v.getContext()).using(new FirebaseImageLoader()).load(storageRef).bitmapTransform(new CropCircleTransformation(v.getContext())).into(holder.imageview);
        Glide.with(v.getContext()).using(new FirebaseImageLoader()).load(storageRef2).into(holder.imageProduk);
        holder.tvNama.setText(jasaList.get(position).getNama());
        holder.tvDetail.setText(jasaList.get(position).getDetail());
        return v;

    }


    static class ViewHolder {
        public TextView  id;
        public ImageView imageview;
        public ImageView imageProduk;
        public TextView  tvNama;
        public TextView  tvDetail;
    }












}
