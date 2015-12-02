package hu.ait.android.cityweather;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hu.ait.android.cityweather.data.CityItem;

/**
 * Created by joe on 11/23/15.
 */
public class AddCityDialog extends DialogFragment {

    public static final String TAG = "DialogFragment";

    public interface AddCityFragmentInterface {
        public void onAddCityFragmentResult(CityItem city);
    }

    private CityItem newCity;
    private AddCityFragmentInterface addCityFragmentInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addCityFragmentInterface = (AddCityFragmentInterface) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + activity.getString(R.string.add_city_fragment_interface_error));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_city_dialog, container, false);

        final EditText etAddCity = (EditText) v.findViewById(R.id.etAddCity);
        Button btnAddCity = (Button) v.findViewById(R.id.btnAddCity);
        Button btnCancel = (Button) v.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(etAddCity.getText().toString())) {
                    etAddCity.setError(getString(R.string.empty_error));
                } else {
                    newCity = new CityItem(etAddCity.getText().toString());
                    addCityFragmentInterface.onAddCityFragmentResult(newCity);
                    dismiss();
                }

            }
        });


        return v;
    }



}
