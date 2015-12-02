package hu.ait.android.cityweather.data;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by joe on 11/23/15.
 */
public class CityItem extends SugarRecord<CityItem> implements Serializable {

    private String cityName;

    public CityItem() {
    }

    public CityItem(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
