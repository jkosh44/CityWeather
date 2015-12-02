package hu.ait.android.cityweather.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import hu.ait.android.cityweather.CitiesListActivity;
import hu.ait.android.cityweather.DetailsActivity;
import hu.ait.android.cityweather.R;
import hu.ait.android.cityweather.data.CityItem;
import hu.ait.android.cityweather.data.CityResult;
import hu.ait.android.cityweather.network.HttpAsyncTask;

/**
 * Created by joe on 11/23/15.
 */
public class CityItemRecyclerAdapter extends RecyclerView.Adapter<CityItemRecyclerAdapter.ViewHolder> {


    public static final String CITY_NAME = "CITY_NAME";
    public static final String CITY_RESULT = "CITY_RESULT";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String TEMP = "TEMP";
    public static final String MIN_TEMP = "MIN_TEMP";

    private Intent detailsIntent;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCityName;
        public RelativeLayout layoutItemRow;
        public ImageView iconX;


        public ViewHolder(View itemView) {
            super(itemView);
            tvCityName = (TextView) itemView.findViewById(R.id.tvCityName);
            layoutItemRow = (RelativeLayout) itemView.findViewById(R.id.itemRow);
            iconX = (ImageView) itemView.findViewById(R.id.iconX);
        }

    }

    private List<CityItem> cityItemsList;
    private Context context;
    //private int lastPosition = -1;

    public CityItemRecyclerAdapter(List<CityItem> cityItemsList, Context context) {
        this.cityItemsList = cityItemsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.tvCityName.setText(cityItemsList.get(i).getCityName());
        viewHolder.layoutItemRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsIntent = new Intent(context, DetailsActivity.class);
                detailsIntent.putExtra(CITY_NAME, cityItemsList.get(i).getCityName());
                context.startActivity(detailsIntent);
            }
        });
        viewHolder.iconX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCityItem(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return cityItemsList.size();
    }

    public void addCityItem(CityItem item) {
        item.save();
        cityItemsList.add(item);
        notifyDataSetChanged();
    }

    public void updateItem(int index, CityItem item) {
        cityItemsList.set(index, item);
        item.save();
        notifyItemChanged(index);
    }

    public void removeCityItem(int index) {
        // remove it from the DB
        cityItemsList.get(index).delete();
        // remove it from the list
        cityItemsList.remove(index);
        notifyDataSetChanged();
    }

    public void removeAll() {
        cityItemsList.clear();
        CityItem.deleteAll(CityItem.class);
        notifyDataSetChanged();
    }

    public void swapItems(int oldPosition, int newPosition) {
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(cityItemsList, i, i + 1);
            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(cityItemsList, i, i - 1);
            }
        }
        notifyItemMoved(oldPosition, newPosition);
    }


    public CityItem getItem(int i) {
        return cityItemsList.get(i);
    }

}
