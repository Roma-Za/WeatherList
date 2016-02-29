package com.example.roman.weatherlist;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.roman.weatherlist.fragments.CardsListFragment;
import com.example.roman.weatherlist.fragments.InfoFragment;
import com.example.roman.weatherlist.fragments.ManageFragment;
import com.example.roman.weatherlist.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                   CardsListFragment.OnFragmentInteractionListener {

    public RequestQueue queue;

    private Cities cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        queue = Volley.newRequestQueue(this);

        cities = new Cities(this);
        setCardsListFragment(false);

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.your_city) {
            setCardsListFragment(true);
        } else if (id == R.id.settings) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new SettingsFragment())
                    .commit();
        } else if (id == R.id.manage_cities) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new ManageFragment())
                    .commit();
        } else if (id == R.id.info) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new InfoFragment())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setCardsListFragment( boolean isMyCity) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(CardsListFragment.IS_MY_CITY, isMyCity);
        CardsListFragment cardsListFragment = new CardsListFragment();
        cardsListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, cardsListFragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("Fragment", "uri: " + uri.toString());
    }
}
