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
import com.tranhuyson.weather.model.HangNgay;

import java.util.ArrayList;

public class AdapterHangNgay extends RecyclerView.Adapter<AdapterHangNgay.ViewHolderHangNgay> {
    private Context mContext;
    private ArrayList<HangNgay> mListHangNgay;

    public AdapterHangNgay(Context mContext, ArrayList<HangNgay> mListHangNgay) {
        this.mContext = mContext;
        this.mListHangNgay = mListHangNgay;
    }

    @NonNull
    @Override
    public ViewHolderHangNgay onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.hangngay_item,parent,false);
        ViewHolderHangNgay viewHolderHangNgay=new ViewHolderHangNgay(view);

        return viewHolderHangNgay;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHangNgay holder, int position) {
        HangNgay hangNgay=mListHangNgay.get(position);
        long time=hangNgay.getTimeNgay();
        String sTime=new java.text.SimpleDateFormat("dd/MM").format(new java.util.Date(time * 1000));
        holder.tvTime.setText(sTime);
        double dTemp=(double)hangNgay.getTemp()-273.15;
        dTemp=Math.round(dTemp);
        int iTemp=(int)dTemp;
        holder.tvTemp.setText(iTemp+"°");
        holder.tvDoAm.setText(hangNgay.getDoAm()+"%");
        String icon=hangNgay.getIcon();
        switch (icon){
            case "01d":
                holder.imgIconHangNgay.setImageResource(R.drawable.d01);
                break;
            case "02d":
                holder.imgIconHangNgay.setImageResource(R.drawable.d02);
                break;
            case "03d":
                holder.imgIconHangNgay.setImageResource(R.drawable.d03);
                break;
            case "04d":
                holder.imgIconHangNgay.setImageResource(R.drawable.d04);
                break;
            case "09d":
                holder.imgIconHangNgay.setImageResource(R.drawable.d09);
                break;
            case "10d":
                holder.imgIconHangNgay.setImageResource(R.drawable.d10);
                break;
            case "11d":
                holder.imgIconHangNgay.setImageResource(R.drawable.d11);
                break;
            case "13d":
                holder.imgIconHangNgay.setImageResource(R.drawable.d13);
                break;
            case "50d":
                holder.imgIconHangNgay.setImageResource(R.drawable.d50);
                break;
            case "01n":
                holder.imgIconHangNgay.setImageResource(R.drawable.n01);
                break;
            case "02n":
                holder.imgIconHangNgay.setImageResource(R.drawable.n02);
                break;
            case "03n":
                holder.imgIconHangNgay.setImageResource(R.drawable.n03);
                break;
            case "04n":
                holder.imgIconHangNgay.setImageResource(R.drawable.n04);
                break;
            case "09n":
                holder.imgIconHangNgay.setImageResource(R.drawable.n09);
                break;
            case "10n":
                holder.imgIconHangNgay.setImageResource(R.drawable.n10);
                break;
            case "11n":
                holder.imgIconHangNgay.setImageResource(R.drawable.n11);
                break;
            case "13n":
                holder.imgIconHangNgay.setImageResource(R.drawable.n13);
                break;
            case "50n":
                holder.imgIconHangNgay.setImageResource(R.drawable.n50);
                break;



        }


    }

    @Override
    public int getItemCount() {
        return mListHangNgay.size();
    }

    public class ViewHolderHangNgay extends RecyclerView.ViewHolder {
        private TextView tvTime, tvDoAm,tvTemp;
        private ImageView imgIconHangNgay;
        public ViewHolderHangNgay(@NonNull View itemView) {
            super(itemView);
            tvTime=itemView.findViewById(R.id.tvThuHangNgay);
            tvDoAm=itemView.findViewById(R.id.tvPhanTramLuongMuaHn);
            tvTemp=itemView.findViewById(R.id.tvNhietDoHangNgaySang);
            imgIconHangNgay=itemView.findViewById(R.id.imgSangHangNgay);

        }
    }
}
