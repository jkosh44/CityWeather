package hu.ait.android.cityweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URLEncoder;

import de.greenrobot.event.EventBus;
import hu.ait.android.cityweather.R;
import hu.ait.android.cityweather.data.CityResult;
import hu.ait.android.cityweather.network.HttpAsyncTask;

/**
 * Created by joe on 11/30/15.
 */
public class FragmentMapInfo extends Fragment {
    public static final String TAG = "TAG_MAIN";
    private String cityName;

    private TextView tvCityName;
    private TextView tvTemperature;
    private TextView tvDescription;
    private ImageView ivWeatherIcon;

    public FragmentMapInfo(String cityName) {
        this.cityName = cityName;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map_info, container, false);

        tvCityName = (TextView) rootView.findViewById(R.id.tvInfoCityName);
        tvTemperature = (TextView) rootView.findViewById(R.id.tvTemperature);
        tvDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        ivWeatherIcon = (ImageView) rootView.findViewById(R.id.ivWeatherIcon);

        getWeather(cityName);

        return rootView;
    }

    public void onEventMainThread(CityResult cityResult) {
        if (cityResult.getName() != null) {
            tvCityName.setText("City: " + cityResult.getName());
        }
            tvTemperature.setText("Temperature: " + cityResult.getMain().getTemp() + "° C");
            tvDescription.setText("Description: " + cityResult.getWeather().get(0).getDescription());
            Glide.with(this).load("http://openweathermap.org/img/w/" + cityResult.getWeather().get(0).getIcon() + ".png").into(ivWeatherIcon);

    }

    public void getWeather(String city) {

        try {
            String urlCityName = URLEncoder.encode(city);
            new HttpAsyncTask(getActivity().getApplicationContext()).execute(
                    "http://api.openweathermap.org/data/2.5/weather?q=" + city/*urlCityName*/ + "&units=metric&APPID=6290e650b9ffc24da1e9106fd489b6fd"
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
