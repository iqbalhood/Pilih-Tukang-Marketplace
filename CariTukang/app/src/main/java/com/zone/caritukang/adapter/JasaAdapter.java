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
import com.zone.caritukang.R;
import com.zone.caritukang.setget.Jasa;

import java.util.ArrayList;


public class JasaAdapter extends ArrayAdapter<Jasa> {
    ArrayList<Jasa> jasaList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

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
            holder.tvNama       = (TextView) v.findViewById(R.id.namaJasa);
            holder.tvDetail     = (TextView) v.findViewById(R.id.detailJasa);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.imageview.setImageResource(R.drawable.tukang);
        Glide.with(v.getContext()).load(jasaList.get(position).getFoto()).into(holder.imageview);
        holder.tvNama.setText(jasaList.get(position).getNama());
        holder.tvDetail.setText(jasaList.get(position).getDetail());
        return v;

    }


    static class ViewHolder {
        public TextView  id;
        public ImageView imageview;
        public TextView  tvNama;
        public TextView  tvDetail;
    }












}
