package hu.ait.android.cityweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.URLEncoder;

import de.greenrobot.event.EventBus;
import hu.ait.android.cityweather.R;
import hu.ait.android.cityweather.data.CityResult;
import hu.ait.android.cityweather.network.HttpAsyncTask;

/**
 * Created by joe on 11/30/15.
 */
public class FragmentMapDetails extends Fragment {

    public static final String TAG = "TAG_DETAILS";
    private String cityName;

    private TextView tvCityName;
    private TextView tvMinTemp;
    private TextView tvMaxTemp;
    private TextView tvHumid;
    private TextView tvWindSpeed;

    public FragmentMapDetails(String cityName) {
        this.cityName = cityName;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map_details, container, false);

        tvCityName = (TextView) rootView.findViewById(R.id.tvDetailsCityName);
        tvMinTemp = (TextView) rootView.findViewById(R.id.tvMinTemp);
        tvMaxTemp = (TextView) rootView.findViewById(R.id.tvMaxTemp);
        tvHumid = (TextView) rootView.findViewById(R.id.tvHumid);
        tvWindSpeed = (TextView) rootView.findViewById(R.id.tvWind);

        getWeather(cityName);

        return rootView;
    }

    public void onEventMainThread(CityResult cityResult) {
        if (cityResult.getName() != null) {
            tvCityName.setText("City: " + cityResult.getName());
        }
        tvMinTemp.setText("Minimum Temperature: " + cityResult.getMain().getTempMin().toString() + " \u00b0 C");
        tvMaxTemp.setText("Maximum Temperature: " + cityResult.getMain().getTempMax().toString() + " ° C");
        tvHumid.setText("Humidity: " + cityResult.getMain().getHumidity().toString() + "%");
        tvWindSpeed.setText("Wind Speed: " + cityResult.getWind().getSpeed().toString() + " km/h");

    }

    public void getWeather(String city) {

        try {
            String urlCityName = URLEncoder.encode(city);
            new HttpAsyncTask(getActivity().getApplicationContext()).execute(
                    "http://api.openweathermap.org/data/2.5/weather?q=" + urlCityName + "&units=metric&APPID=6290e650b9ffc24da1e9106fd489b6fd"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


}
