package hu.ait.android.cityweather.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import hu.ait.android.cityweather.adapter.CityItemRecyclerAdapter;

/**
 * Created by joe on 11/23/15.
 */
public class CityItemListTouchHelper extends ItemTouchHelper.Callback {

    private CityItemRecyclerAdapter adapter;

    public CityItemListTouchHelper(CityItemRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        //messes up editing
        //adapter.swapItems(viewHolder.getAdapterPosition(), target.getAdapterPosition());

        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.removeCityItem(viewHolder.getAdapterPosition());
    }


}
