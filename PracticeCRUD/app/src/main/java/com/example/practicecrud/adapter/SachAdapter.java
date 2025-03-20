package com.example.practicecrud.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practicecrud.R;
import com.example.practicecrud.SachActivity;
import com.example.practicecrud.model.Sach;

import java.util.List;

public class SachAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Sach> sachList;

    public SachAdapter(Context context, int layout, List<Sach> sachList) {
        this.context = context;
        this.layout = layout;
        this.sachList = sachList;
    }

    @Override
    public int getCount() {
        return sachList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtTen, txtNgayXB, txtTheloai, txtTacgiaSach;
        ImageView imgDelete, imgUpdate;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtTen = (TextView) convertView.findViewById(R.id.textViewTenCV);
            holder.txtNgayXB = (TextView) convertView.findViewById(R.id.textViewNgayXBSach);
            holder.txtTheloai = (TextView) convertView.findViewById(R.id.textViewTheLoaiSach);
            holder.txtTacgiaSach = (TextView) convertView.findViewById(R.id.textViewTacgiaSach);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            holder.imgUpdate = (ImageView) convertView.findViewById(R.id.imageViewEdit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Sach sach = sachList.get(position);
        holder.txtTen.setText(sach.getTensach());
        holder.txtNgayXB.setText(sach.getNgayXB());
        holder.txtTheloai.setText(sach.getTheloai());
        holder.txtTacgiaSach.setText(sach.getIdTacgia() + "");

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SachActivity) context).DialogSuaSach(sach.getIDsach(), sach.getTensach(), sach.getNgayXB(), sach.getTheloai(), sach.getIdTacgia());
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SachActivity) context).DialogXoaSach(sach.getTensach(), sach.getIDsach());
            }
        });

        return convertView;
    }
}
