package com.tranhuyson.weather.Fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.tranhuyson.weather.Adapter.ApdapterListVitri;
import com.tranhuyson.weather.R;
import com.tranhuyson.weather.activity.MapsActivity;
import com.tranhuyson.weather.model.ListViTri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListVitriFragment extends Fragment {
    private SearchView searchViewList;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private RecyclerView mRecycleView;
    private ApdapterListVitri mAdapter;
    private ArrayList<ListViTri> mListDataViTri;
    private Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listvitri, container, false);
        mRecycleView = view.findViewById(R.id.rcListVitri);
        searchViewList = view.findViewById(R.id.sv_list_vitri);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mRecycleView.setLayoutManager(linearLayoutManager);
//        mAdapter=new ApdapterListVitri(mContext,mListDataViTri);
//        mRecycleView.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();

        searchViewList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchViewList.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final Address address = addressList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    Toast.makeText(mContext, "lat" + address.getLatitude() + "Lng" + address.getLongitude(), Toast.LENGTH_SHORT).show();
                    String API = "https://api.openweathermap.org/data/2.5/weather?lat=" + address.getLatitude() + "&lon=" + address.getLongitude() + "&appid=211ff006de9aba9ddd122331f87cdf8b";
                    new DoGetDataList().execute(API);


                }

//                mAdapter=new ApdapterListVitri(mContext,mListDataViTri);
////                mRecycleView.setAdapter(mAdapter);
//                mAdapter.notifyDataSetChanged();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return view;
    }


    class DoGetDataList extends AsyncTask<String, Void, ArrayList<ListViTri>> {
        private String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ListViTri> doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                int byteCharactor;
                while ((byteCharactor = inputStream.read()) != -1) {
                    result += (char) byteCharactor;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return DataMaps(result);
        }


        @Override
        protected void onPostExecute(ArrayList<ListViTri> s) {
            super.onPostExecute(s);
            Toast.makeText(mContext, "date" + sDateTime + "temp" + temp, Toast.LENGTH_SHORT).show();
            mListDataViTri = new ArrayList<>();
            mListDataViTri.addAll(s);
            mAdapter = new ApdapterListVitri(mContext, s);
            mRecycleView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        }
    }

    ListViTri listViTri;
    String sDateTime;
    String sNameCity;
    int temp;
    String sIcon;
    String sQuocGia;

    private ArrayList<ListViTri> DataMaps(String json) {
        mListDataViTri = new ArrayList<>();
        try {
            jsonObject = new JSONObject(json);
            sNameCity = jsonObject.getString("name");
            long lDateTime = jsonObject.getLong("dt");
            jsonObject = jsonObject.getJSONObject("sys");
            sQuocGia = jsonObject.getString("country");
            sDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(lDateTime * 1000));
            JSONObject jsonObject1 = new JSONObject(json);
            jsonObject1 = jsonObject1.getJSONObject("main");
            int a = jsonObject1.getInt("temp");
            double temp_int = Double.parseDouble(a + "") - 273.15;
            temp_int = Math.round(temp_int);
            temp = (int) temp_int;
            JSONObject jsonObject2 = new JSONObject(json);
            jsonArray = jsonObject2.getJSONArray("weather");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsob = jsonArray.getJSONObject(i);
                sIcon = jsob.getString("icon");

            }


            mListDataViTri.add(new ListViTri(sNameCity, sIcon, sDateTime, temp, sQuocGia));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mListDataViTri;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_vitri,menu);
        menu.findItem(R.id.menu_Maps).setVisible(false);
        menu.findItem(R.id.menu_ListVitri).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.menu_setting).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_AddVitri:
                Toast.makeText(getActivity(), "list vi tri", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

