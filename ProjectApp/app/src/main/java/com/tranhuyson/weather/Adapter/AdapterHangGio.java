package com.tranhuyson.weather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tranhuyson.weather.R;
import com.tranhuyson.weather.model.HangGio;

import java.util.ArrayList;

public class AdapterHangGio extends RecyclerView.Adapter<AdapterHangGio.ViewHolder> {
   private Context mContext;

   private ArrayList<HangGio> mListHangGio;


    public AdapterHangGio(Context mContext, ArrayList<HangGio> mListHangGio) {
        this.mContext = mContext;
        this.mListHangGio = mListHangGio;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.hanggio_item,parent,false);

        ViewHolder viewHolder=new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            HangGio hangGio=mListHangGio.get(position);
            long time=hangGio.getTime();
            String sTime=new java.text.SimpleDateFormat("dd/MM\nHH:mm").format(new java.util.Date(time * 1000));
            holder.tvTime.setText(sTime);
//        double temp_int = Double.parseDouble(temp) - 273.15;
//        temp_int = Math.round(temp_int);
//        i = (int) temp_int;
            double dTemp=(double)hangGio.getTemp()-273.15;
            dTemp=Math.round(dTemp);
            int iTemp=(int)dTemp;
      holder.tvTemp.setText(iTemp+"°");
        holder.tvDoAm.setText(hangGio.getDoAm()+"%");
      String icon=hangGio.getIcon();
        switch (icon){
            case "01d":
                holder.imgIconHangGio.setImageResource(R.drawable.d01);
                break;
            case "02d":
                holder.imgIconHangGio.setImageResource(R.drawable.d02);
                break;
            case "03d":
                holder.imgIconHangGio.setImageResource(R.drawable.d03);
                break;
            case "04d":
                holder.imgIconHangGio.setImageResource(R.drawable.d04);
                break;
            case "09d":
                holder.imgIconHangGio.setImageResource(R.drawable.d09);
                break;
            case "10d":
                holder.imgIconHangGio.setImageResource(R.drawable.d10);
                break;
            case "11d":
                holder.imgIconHangGio.setImageResource(R.drawable.d11);
                break;
            case "13d":
                holder.imgIconHangGio.setImageResource(R.drawable.d13);
                break;
            case "50d":
                holder.imgIconHangGio.setImageResource(R.drawable.d50);
                break;
            case "01n":
                holder.imgIconHangGio.setImageResource(R.drawable.n01);
                break;
            case "02n":
                holder.imgIconHangGio.setImageResource(R.drawable.n02);
                break;
            case "03n":
                holder.imgIconHangGio.setImageResource(R.drawable.n03);
                break;
            case "04n":
                holder.imgIconHangGio.setImageResource(R.drawable.n04);
                break;
            case "09n":
                holder.imgIconHangGio.setImageResource(R.drawable.n09);
                break;
            case "10n":
                holder.imgIconHangGio.setImageResource(R.drawable.n10);
                break;
            case "11n":
                holder.imgIconHangGio.setImageResource(R.drawable.n11);
                break;
            case "13n":
                holder.imgIconHangGio.setImageResource(R.drawable.n13);
                break;
            case "50n":
                holder.imgIconHangGio.setImageResource(R.drawable.n50);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mListHangGio.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime,tvDoAm,tvTemp;
        private ImageView imgIconHangGio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime=itemView.findViewById(R.id.tvTimeHangGio);
            tvDoAm=itemView.findViewById(R.id.tvPhanTramLuongMuaHg);
            tvTemp=itemView.findViewById(R.id.tvNhietDoHangGio);
            imgIconHangGio=itemView.findViewById(R.id.imgDuBao);
        }
    }
}
