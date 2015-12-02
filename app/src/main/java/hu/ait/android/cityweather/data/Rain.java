package hu.ait.android.cityweather.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joe on 11/29/15.
 */
public class Rain {
    @SerializedName("3h")
    @Expose
    private Double _3h;

    public Double get_3h() {
        return _3h;
    }

    public void set_3h(Double _3h) {
        this._3h = _3h;
    }
}
