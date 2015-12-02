package hu.ait.android.cityweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import hu.ait.android.cityweather.adapter.CityItemRecyclerAdapter;
import hu.ait.android.cityweather.data.CityItem;
import hu.ait.android.cityweather.data.CityResult;
import hu.ait.android.cityweather.touch.CityItemListTouchHelper;


public class CitiesListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddCityDialog.AddCityFragmentInterface {

    private CityItemRecyclerAdapter cityAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<CityItem> cityItemList = CityItem.listAll(CityItem.class);

        cityAdapter = new CityItemRecyclerAdapter(cityItemList, this);
        RecyclerView recyclerViewCityItems = (RecyclerView) findViewById(
                R.id.recycler_view);
        recyclerViewCityItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCityItems.setAdapter(cityAdapter);

        CityItemListTouchHelper touchHelperCallback = new CityItemListTouchHelper(
                cityAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerViewCityItems);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCity();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cities_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            aboutApp();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            aboutApp();
        } else if (id == R.id.nav_add_city) {
            addCity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void aboutApp() {
        Toast.makeText(CitiesListActivity.this, R.string.about_message, Toast.LENGTH_SHORT).show();
    }

    public void addCity() {
        final AddCityDialog dialog = new AddCityDialog();

        dialog.setCancelable(false);

        dialog.show(getSupportFragmentManager(), AddCityDialog.TAG);
    }


    @Override
    public void onAddCityFragmentResult(CityItem city) {
        cityAdapter.addCityItem(city);
    }



}
