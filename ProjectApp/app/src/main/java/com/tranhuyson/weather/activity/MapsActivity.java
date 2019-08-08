package com.tranhuyson.weather.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tranhuyson.weather.R;
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

import pub.devrel.easypermissions.EasyPermissions;

//import com.google.android.libraries.places.api.Places;
//import com.google.android.libraries.places.api.model.Place;
//import com.google.android.libraries.places.api.net.PlacesClient;
//import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
//import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private TextView tvNameCity, tvDateTime, tvTemp;
    private ImageView imgAnhMoTa;
    private Button btnChiTiet;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    SearchView searchView;
    double lat;
    double lng;

    ImageView imgGPS;
    JSONObject jsonObject;
    JSONArray jsonArray;
    private List<ListViTri> mListDataViTri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        tvDateTime = findViewById(R.id.tvTimeMaps);
        tvTemp = findViewById(R.id.tvTempMaps);
        imgAnhMoTa = findViewById(R.id.imgAnhMaps);
        tvNameCity = findViewById(R.id.tvNameLocation);
        btnChiTiet = findViewById(R.id.btnChiTiet);
        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("lat", lat + "");
                bundle.putString("lng", lng + "");
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        imgGPS = findViewById(R.id.imgGPS);
        String provider = LocationManager.GPS_PROVIDER;
        checkRequiredPermissions();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        locationManager.requestLocationUpdates(provider, 1000, 20, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });

        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        androidx.appcompat.widget.SearchView.SearchAutoComplete searchAutoComplete=searchView.findViewById()
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    Toast.makeText(MapsActivity.this, "lat" + address.getLatitude() + "Lng" + address.getLongitude(), Toast.LENGTH_SHORT).show();
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    String API = "https://api.openweathermap.org/data/2.5/weather?lat=" + address.getLatitude() + "&lon=" + address.getLongitude() + "&appid=211ff006de9aba9ddd122331f87cdf8b";
                    new DoGetDataMaps().execute(API);
                    btnChiTiet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("lat", address.getLatitude() + "");
                            bundle.putString("lng", address.getLongitude() + "");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }

    private boolean checkRequiredPermissions() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.app_name1)
                    , 20000
                    , perms);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //AddamarkerinSydneyandmovethecamera
        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions()
                .position(sydney).title("I'am here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraPosition cameraPosition = CameraPosition.builder().target(sydney).zoom(14).bearing(0).tilt(45).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        String API = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lng + "&appid=211ff006de9aba9ddd122331f87cdf8b";
        new DoGetDataMaps().execute(API);
        imgGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng sydney = new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions()
                        .position(sydney).title("I'am here"));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                CameraPosition cameraPosition = CameraPosition.builder().target(sydney).zoom(16).bearing(0).tilt(45).build();
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                String API = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lng + "&appid=211ff006de9aba9ddd122331f87cdf8b";
                new DoGetDataMaps().execute(API);
            }
        });


    }


    class DoGetDataMaps extends AsyncTask<String, Void, List<ListViTri>> {
        private String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<ListViTri> doInBackground(String... strings) {
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
        protected void onPostExecute(List<ListViTri> s) {
            super.onPostExecute(s);
            tvDateTime.setText(sDateTime);
            tvTemp.setText(temp + "°");
            tvNameCity.setText(sNameCity + ", " + sQuocGia);

        }
    }

    ListViTri listViTri;
    String sDateTime;
    String sNameCity;
    int temp;
    String sIcon;
    String sQuocGia;

    private List<ListViTri> DataMaps(String json) {
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
            jsonArray = jsonObject.getJSONArray("weather");
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


}
