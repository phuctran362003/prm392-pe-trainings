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
import com.example.practicecrud.TacgiaActivity;
import com.example.practicecrud.model.Sach;
import com.example.practicecrud.model.Tacgia;

import java.util.List;

public class TacgiaAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Tacgia> tacgiaList;

    public TacgiaAdapter(Context context, int layout, List<Tacgia> tacgiaList) {
        this.context = context;
        this.layout = layout;
        this.tacgiaList = tacgiaList;
    }


    @Override
    public int getCount() {
        return tacgiaList.size();
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
        TextView txtTen, txtEmail, txtDiachi, txtDienthoai;
        ImageView imgDelete, imgUpdate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtTen = (TextView) convertView.findViewById(R.id.textViewTenTG);
            holder.txtEmail = (TextView) convertView.findViewById(R.id.textViewEmail);
            holder.txtDiachi = (TextView) convertView.findViewById(R.id.textViewDiaChi);
            holder.txtDienthoai = (TextView) convertView.findViewById(R.id.textViewSoDienThoai);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imageViewDeleteTG);
            holder.imgUpdate = (ImageView) convertView.findViewById(R.id.imageViewEditTG);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Tacgia tacgia = tacgiaList.get(position);
        holder.txtTen.setText(tacgia.getTenTacgia());
        holder.txtEmail.setText(tacgia.getEmail());
        holder.txtDiachi.setText(tacgia.getDiachi());
        holder.txtDienthoai.setText(tacgia.getDienThoai());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TacgiaActivity) context).DialogXoaTacgia(tacgia.getTenTacgia(), tacgia.getIDTacgia());
            }
        });

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TacgiaActivity) context).DialogSuaTacgia(tacgia.getIDTacgia(), tacgia.getTenTacgia(), tacgia.getEmail(), tacgia.getDiachi(), tacgia.getDienThoai());
            }
        });

        return convertView;
    }
}
