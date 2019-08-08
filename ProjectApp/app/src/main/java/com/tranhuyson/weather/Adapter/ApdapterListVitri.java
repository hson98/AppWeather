package com.tranhuyson.weather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.tranhuyson.weather.R;
import com.tranhuyson.weather.model.ListViTri;

import java.util.ArrayList;

public class ApdapterListVitri extends RecyclerView.Adapter<ApdapterListVitri.ViewHolderListViTri> {
    private Context mContext;
    private ArrayList<ListViTri> mListVitri;

    public ApdapterListVitri(Context mContext, ArrayList<ListViTri> mListVitri) {
        this.mContext = mContext;
        this.mListVitri = mListVitri;
    }

    @NonNull
    @Override
    public ViewHolderListViTri onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_list, parent, false);
        ViewHolderListViTri viewHolderListViTri = new ViewHolderListViTri(view);

        return viewHolderListViTri;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderListViTri holder, int position) {
                ListViTri listViTri=mListVitri.get(position);
                holder.tvNameCity.setText(listViTri.getNameCity()+", "+listViTri.getQuocGia());
                holder.tvNhietDo.setText(listViTri.getNhietDo()+"°");
                holder.tvTime.setText(listViTri.getNgayThang());

    }

    @Override
    public int getItemCount() {
        return mListVitri.size();
    }

    public class ViewHolderListViTri extends RecyclerView.ViewHolder {
        private TextView tvNameCity, tvTime,tvNhietDo;
        private ImageView imgAnhMoTa;
        public ViewHolderListViTri(@NonNull View itemView) {
            super(itemView);
            tvNameCity=itemView.findViewById(R.id.tvCustomNameLocation);
            tvTime=itemView.findViewById(R.id.tvCustomTimeMaps);
            tvNhietDo=itemView.findViewById(R.id.tvCustomTempMaps);
            imgAnhMoTa=itemView.findViewById(R.id.imgCustomAnhMaps);
        }
    }
}
