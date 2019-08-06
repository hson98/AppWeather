package com.tranhuyson.weather.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.tranhuyson.weather.Adapter.AdapterHangGio;
import com.tranhuyson.weather.Adapter.AdapterHangNgay;
import com.tranhuyson.weather.Fragment.ListVitriFragment;
import com.tranhuyson.weather.R;
import com.tranhuyson.weather.model.GraphDays;
import com.tranhuyson.weather.model.HangGio;
import com.tranhuyson.weather.model.HangNgay;
import com.tranhuyson.weather.model.MainWeather;
import com.tranhuyson.weather.model.NameCity;
import com.tranhuyson.weather.model.SysWeather;
import com.tranhuyson.weather.model.Weather;
import com.tranhuyson.weather.model.Wind;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.tranhuyson.weather.model.Define.API_WEATHER;
import static com.tranhuyson.weather.model.Define.API_WEATHER_HOUR;
import static com.tranhuyson.weather.model.Define.API_WEATHER_KEY;

public class MainActivity extends AppCompatActivity {
    private TextView tvTimeDate, tvDescription, tvTemp, tvTimeSunRise, tvTimeSunSet, tvHumidity, tvMinMax, tvSpeedWind, tvNameCity, tvLuongMua;
    private ImageView imgIcon;
    String nameCity = "London";
    private String lat;
    private String lng;
        String urlAPI = API_WEATHER + "weather?q=" + nameCity + API_WEATHER_KEY;
    String urlAPI_HOUR = API_WEATHER + "forecast?q=" + nameCity + API_WEATHER_KEY;
    String urlAPI_DAY = API_WEATHER + "forecast/daily?q=" + nameCity + API_WEATHER_KEY;

    private ArrayList<HangGio> mListHangGio;
    private RecyclerView mRecyclerView;
    private AdapterHangGio mAdapter;
    private ArrayList<HangNgay> mListHangNgay;
    private AdapterHangNgay mAdapterHangNgay;
    private RecyclerView mRecycleViewHangNgay;
    JSONObject jsonObject;
    private static final String TAG = "WeatherMap";
    private static final String TAG1 = "WeatherMapHour";
    MaterialSearchView mSearchView;
    Toolbar mToolbar;
    private List<String> mListGraphDay;
    GraphDays mygraphDays;

    //String urlAPI_LOCATION = API_WEATHER + "weather?lat=" + lat + "&lon=" + lng + "&appid=" + API_WEATHER_KEY;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mygraphDays = findViewById(R.id.grDay);

        addControl();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            lat = bundle.getString("lat");
            lng = bundle.getString("lng");
            String urlAPI_LOCATION = API_WEATHER + "weather?lat=" + lat + "&lon=" + lng + API_WEATHER_KEY;
            new DoGetData().execute(urlAPI_LOCATION);
            String urlAPI_HOUR = API_WEATHER + "forecast?lat=" + lat + "&lon=" + lng + API_WEATHER_KEY;
            new DoGetDaTaHangGio().execute(urlAPI_HOUR);
            String urlAPI_DAY = API_WEATHER + "forecast/daily?lat=" + lat + "&lon=" + lng+ API_WEATHER_KEY;
            new DoGetDaTaHangNgay().execute(urlAPI_DAY);
            new DogetJsonGraphDay().execute(urlAPI_DAY);
        }

        //lay GPS
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String provider = LocationManager.GPS_PROVIDER;
        checkRequiredPermissions();
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
        locationManager.requestLocationUpdates(provider, 1000, 20, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String lat1 = location.getLatitude() + "";
                String lng1 = location.getLongitude() + "";
                Toast.makeText(MainActivity.this, "lat" + lat1 + "lng" + lng1, Toast.LENGTH_SHORT).show();
                if (lat == null && lng == null) {
                    String urlAPI_LOCATION = API_WEATHER + "weather?lat=" + lat1 + "&lon=" + lng1 + API_WEATHER_KEY;
                    new DoGetData().execute(urlAPI_LOCATION);
                    String urlAPI_HOUR = API_WEATHER + "forecast?lat=" + lat1 + "&lon=" + lng1 + API_WEATHER_KEY;

                    new DoGetDaTaHangGio().execute(urlAPI_HOUR);
                    String urlAPI_DAY = API_WEATHER + "forecast/daily?lat=" + lat1 + "&lon=" + lng1+ API_WEATHER_KEY;
                    new DoGetDaTaHangNgay().execute(urlAPI_DAY);
                    new DogetJsonGraphDay().execute(urlAPI_DAY);
                }

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

        //HangGio
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mListHangGio = new ArrayList<HangGio>();
        mAdapter = new AdapterHangGio(MainActivity.this, mListHangGio);
        mRecyclerView.setAdapter(mAdapter);

        //hang ngay
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
        mRecycleViewHangNgay.setLayoutManager(layoutManager);
        mListHangNgay = new ArrayList<HangNgay>();
        mAdapterHangNgay = new AdapterHangNgay(MainActivity.this, mListHangNgay);
        mRecycleViewHangNgay.setAdapter(mAdapterHangNgay);


        // new DoGetData().execute(urlAPI);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mSearchView = findViewById(R.id.search_view);
        mSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestios));
        mSearchView.setEllipsize(true);
        mSearchView.clearFocus();


        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                urlAPI = API_WEATHER + "weather?q=" + query + API_WEATHER_KEY;
                urlAPI_HOUR = API_WEATHER + "forecast?q=" + query + API_WEATHER_KEY;
                urlAPI_DAY = API_WEATHER + "forecast/daily?q=" + query + API_WEATHER_KEY;
                new DoGetData().execute(urlAPI);
                new DoGetDaTaHangGio().execute(urlAPI_HOUR);
                new DoGetDaTaHangNgay().execute(urlAPI_DAY);
                new DogetJsonGraphDay().execute(urlAPI_DAY);
//                tvNameCity.setText(query);
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                return true;
            case R.id.menu_setting:
                Toast.makeText(getApplication(), "Setting", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_Maps:
                Intent intent = new Intent(getApplication(), MapsActivity.class);
                startActivity(intent);
                Toast.makeText(getApplication(), "Maps open", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_ListVitri:
                Toast.makeText(this, "Vi tri", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.contener, new ListVitriFragment());
                fragmentTransaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    private ArrayList<HangGio> listDataHangGio(String json) {
        mListHangGio = new ArrayList<>();
        String icon = "";

        try {

            JSONObject jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray("list");

            int lenght = jsonArray.length();
            for (int i = 0; i <= lenght; i++) {
                JSONObject obJsonHangGio = jsonArray.getJSONObject(i);
                long time = obJsonHangGio.getLong("dt");
                jsonObject = obJsonHangGio.getJSONObject("main");
                int temp = jsonObject.getInt("temp");
                String humidity = jsonObject.getString("humidity");
                int length1 = obJsonHangGio.getJSONArray("weather").length();
                try {
                    for (int j = 0; j <= length1; j++) {
                        JSONObject obHangGio1 = obJsonHangGio.getJSONArray("weather").getJSONObject(j);
                        icon = obHangGio1.getString("icon");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mListHangGio.add(new HangGio(time, icon, temp, humidity));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mListHangGio;
    }


    private ArrayList<HangNgay> listDataHangNgay(String json) {
        mListHangNgay = new ArrayList<>();
        String icon = "";
        String moTa = "";
        try {

            jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray("list");
            int leght1 = jsonArray.length();
            for (int i = 0; i <= leght1; i++) {

                JSONObject job = jsonArray.getJSONObject(i);
                long time = job.getLong("dt");
                jsonObject = job.getJSONObject("temp");
                double tempDay = jsonObject.getDouble("day");
                double dTempDay = tempDay - 273.15;
                dTempDay = Math.round(dTempDay);
                int iTempDay = (int) dTempDay;
                //Lay minmax
                double tempDayMin = jsonObject.getDouble("min");
                double dTempDayMin = tempDayMin - 273.15;
                dTempDayMin = Math.round(dTempDayMin);
                int iTempDayMin1 = (int) dTempDayMin;
                String iTempDayMin = iTempDayMin1 + "";

                double tempDayMax = jsonObject.getDouble("max");
                double dTempDayMax = tempDayMax - 273.15;
                dTempDayMax = Math.round(dTempDayMax);
                int iTempDayMax1 = (int) dTempDayMax;
                String iTempDayMax = iTempDayMax1 + "";


                String doAm = job.getString("humidity");
                int leght = job.getJSONArray("weather").length();
                try {
                    for (int j = 0; j <= leght; j++) {
                        JSONObject job1 = job.getJSONArray("weather").getJSONObject(j);
                        icon = job1.getString("icon");
                        moTa = job1.getString("description");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mListHangNgay.add(new HangNgay(time, doAm, iTempDay, icon, iTempDayMin, iTempDayMax, moTa));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mListHangNgay;
    }


    private void addControl() {
        tvLuongMua = findViewById(R.id.tvLuongMua);
        tvNameCity = findViewById(R.id.tvNameLocal);
        tvTimeDate = findViewById(R.id.tvTimeDate);
        tvDescription = findViewById(R.id.tvDuBao);
        tvTemp = findViewById(R.id.tvNhietDo);
        tvTimeSunRise = findViewById(R.id.tvTimeBm);
        tvTimeSunSet = findViewById(R.id.tvTimeHh);
        tvHumidity = findViewById(R.id.tvPhanTramDoAm);
        imgIcon = findViewById(R.id.imgIcon);
        mRecyclerView = findViewById(R.id.rcHangGio);
        mRecycleViewHangNgay = findViewById(R.id.rcHangNgay);
        tvMinMax = findViewById(R.id.tvMinMax);
        tvSpeedWind = findViewById(R.id.tvSpeedWind);
    }

    List<Weather> listWeather;
    JSONArray jsonArray;
    String sIcon = "";
    String sDescription;

    private void GetJsonArrayWeather(String json) {
        if (listWeather == null) {
            listWeather = new ArrayList<>();

        }
        try {
            jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray("weather");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsob = jsonArray.getJSONObject(i);
                int iId = jsob.getInt("id");
                String sMain = jsob.getString("main");
                sDescription = jsob.getString("description");
                sIcon = jsob.getString("icon");
                listWeather.add(new Weather(iId, sMain, sDescription, sIcon));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String nameCity1;

    private void GetJsonNameCity(String json) {
        try {
            jsonObject = new JSONObject(json);
            nameCity1 = jsonObject.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String sDateTime;

    private void GetJsonDateTime(String json) {
        try {
            jsonObject = new JSONObject(json);
            long lDateTime = jsonObject.getLong("dt");
            sDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(lDateTime * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    SysWeather sysWeather;
    String timeSunrise;
    String timeSunset;

    private void GetJsonSys(String json) {
        try {
            jsonObject = new JSONObject(json);
            jsonObject = jsonObject.getJSONObject("sys");
            long lSunrise = jsonObject.getLong("sunrise");
            long lSunset = jsonObject.getLong("sunset");
            sysWeather = new SysWeather(jsonObject.getString("type"), jsonObject.getString("id"), jsonObject.getString("message"), jsonObject.getString("country"), lSunrise, lSunset);
            timeSunrise = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(lSunrise * 1000));
            timeSunset = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(lSunset * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Wind wind;

    private void GetJsonWind(String json) {
        try {
            jsonObject = new JSONObject(json);
            jsonObject = jsonObject.getJSONObject("wind");
            String speed = jsonObject.getString("speed");
            wind = new Wind(speed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    MainWeather mainWeather;
    int i;
    int Itemp_min;
    int Itemp_max;

    private void GetJsonMainWeather(String json) {
        try {
            jsonObject = new JSONObject(json);
            jsonObject = jsonObject.getJSONObject("main");
            String temp = (jsonObject.getString("temp"));
            String pressure = jsonObject.getString("pressure");
            String humidity = jsonObject.getString("humidity");
            String temp_min = jsonObject.getString("temp_min");
            String temp_max = jsonObject.getString("temp_max");
            mainWeather = new MainWeather(temp, pressure, humidity, temp_min, temp_max);
            //Doi do temp
            double temp_int = Double.parseDouble(temp) - 273.15;
            temp_int = Math.round(temp_int);
            i = (int) temp_int;
            //Doi do temp_min
            double dTemp_min = Double.parseDouble(temp_min) - 273.15;
            dTemp_min = Math.round(dTemp_min);
            Itemp_min = (int) dTemp_min;
            //Doi do temp_max
            double dTemp_max = Double.parseDouble(temp_max) - 273.15;
            dTemp_max = Math.round(dTemp_max);
            Itemp_max = (int) dTemp_max;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class DoGetDaTaHangNgay extends AsyncTask<String, Void, ArrayList<HangNgay>> {
        private String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<HangNgay> doInBackground(String... params) {
            try {
                URL urlDay = new URL(params[0]);
                URLConnection urlConnectionHour = urlDay.openConnection();
                InputStream isDay = urlConnectionHour.getInputStream();
                int byteCharactorHour;
                while ((byteCharactorHour = isDay.read()) != -1) {
                    result += (char) byteCharactorHour;
                }
                Log.d(TAG1, "doInBackground: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return listDataHangNgay(result);
        }

        @Override
        protected void onPostExecute(ArrayList<HangNgay> aVoid) {
            super.onPostExecute(aVoid);
            mAdapterHangNgay = new AdapterHangNgay(getBaseContext(), aVoid);
            mRecycleViewHangNgay.setAdapter(mAdapterHangNgay);
            mAdapterHangNgay.notifyDataSetChanged();
        }
    }

    class DoGetDaTaHangGio extends AsyncTask<String, Void, ArrayList<HangGio>> {
        private String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<HangGio> doInBackground(String... params) {
            try {
                URL urlHour = new URL(params[0]);
                URLConnection urlConnectionHour = urlHour.openConnection();
                InputStream isHour = urlConnectionHour.getInputStream();
                int byteCharactorHour;
                while ((byteCharactorHour = isHour.read()) != -1) {
                    result += (char) byteCharactorHour;
                }
                Log.d(TAG1, "doInBackground: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return listDataHangGio(result);
        }

        @Override
        protected void onPostExecute(ArrayList<HangGio> aVoid) {
            super.onPostExecute(aVoid);
            mAdapter = new AdapterHangGio(getBaseContext(), aVoid);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    class DoGetData extends AsyncTask<String, Void, Void> {
        private String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                URLConnection urlConnection = url.openConnection();
                InputStream is = urlConnection.getInputStream();
                int byteCharactor;
                while ((byteCharactor = is.read()) != -1) {
                    result += (char) byteCharactor;
                }
                Log.d(TAG, "doInBackground: " + result);
                GetJsonMainWeather(result);
                GetJsonWind(result);
                GetJsonSys(result);
                GetJsonDateTime(result);
                GetJsonNameCity(result);
                GetJsonArrayWeather(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //mainWeather
            tvHumidity.setText(mainWeather.getHumidity() + "%");
            tvTemp.setText(i + "°");
            tvMinMax.setText(Itemp_max + "°C" + " / " + Itemp_min + "°C");
            tvSpeedWind.setText(wind.getSpeed() + " m/s");
            //sysWeather
            tvTimeSunRise.setText(timeSunrise);
            tvTimeSunSet.setText(timeSunset);
            //DateTime
            tvTimeDate.setText(sDateTime);
            //NameCity
            tvNameCity.setText(nameCity1);
            //Weather
            switch (sIcon) {
                case "01d":
                    imgIcon.setImageResource(R.drawable.d01);
                    break;
                case "02d":
                    imgIcon.setImageResource(R.drawable.d02);
                    break;
                case "03d":
                    imgIcon.setImageResource(R.drawable.d03);
                    break;
                case "04d":
                    imgIcon.setImageResource(R.drawable.d04);
                    break;
                case "09d":
                    imgIcon.setImageResource(R.drawable.d09);
                    break;
                case "10d":
                    imgIcon.setImageResource(R.drawable.d10);
                    break;
                case "11d":
                    imgIcon.setImageResource(R.drawable.d11);
                    break;
                case "13d":
                    imgIcon.setImageResource(R.drawable.d13);
                    break;
                case "50d":
                    imgIcon.setImageResource(R.drawable.d50);
                    break;
                case "01n":
                    imgIcon.setImageResource(R.drawable.n01);
                    break;
                case "02n":
                    imgIcon.setImageResource(R.drawable.n02);
                    break;
                case "03n":
                    imgIcon.setImageResource(R.drawable.n03);
                    break;
                case "04n":
                    imgIcon.setImageResource(R.drawable.n04);
                    break;
                case "09n":
                    imgIcon.setImageResource(R.drawable.n09);
                    break;
                case "10n":
                    imgIcon.setImageResource(R.drawable.n10);
                    break;
                case "11n":
                    imgIcon.setImageResource(R.drawable.n11);
                    break;
                case "13n":
                    imgIcon.setImageResource(R.drawable.n13);
                    break;
                case "50n":
                    imgIcon.setImageResource(R.drawable.n50);
                    break;


            }
            tvDescription.setText(sDescription);
        }
    }

    //Graph
    class DogetJsonGraphDay extends AsyncTask<String, Void, List<String>> {
        private String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(String... strings) {
            try {
                URL urlHour = new URL(strings[0]);
                URLConnection urlConnectionHour = urlHour.openConnection();
                InputStream isHour = urlConnectionHour.getInputStream();
                int byteCharactorHour;
                while ((byteCharactorHour = isHour.read()) != -1) {
                    result += (char) byteCharactorHour;
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            return ListDataGraphDay(result);
        }

        @Override
        protected void onPostExecute(List<String> graphDays) {
            super.onPostExecute(graphDays);
            mygraphDays.getData(graphDays);
        }
    }


    private List<String> ListDataGraphDay(String json) {
        mListGraphDay = new ArrayList<>();
        try {
            jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray("list");
            int leght1 = jsonArray.length();
            for (int i = 0; i <= leght1; i++) {
                JSONObject job = jsonArray.getJSONObject(i);
                long time = job.getLong("dt");
                String sTime = new java.text.SimpleDateFormat("dd").format(new java.util.Date(time * 1000));
                jsonObject = job.getJSONObject("temp");
                double tempDay = jsonObject.getDouble("day");
                double tempDay1 = (double) tempDay - 273.15;
                tempDay1 = Math.round(tempDay1);
                int tempDay2 = (int) tempDay1;
                mListGraphDay.add(sTime);
                mListGraphDay.add(String.valueOf(tempDay2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mListGraphDay;
    }
}


