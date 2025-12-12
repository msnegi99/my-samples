package com.example.animationexamples;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.animationexamples.fragment.blinkfragment;
import com.example.animationexamples.fragment.bouncefragment;
import com.example.animationexamples.fragment.drawableanimationfragment;
import com.example.animationexamples.fragment.fadefragment;
import com.example.animationexamples.fragment.flipfragment;
import com.example.animationexamples.fragment.movefragment;
import com.example.animationexamples.fragment.rotatefragment;
import com.example.animationexamples.fragment.sequentialanimationfragment;
import com.example.animationexamples.fragment.slidedownfragment;
import com.example.animationexamples.fragment.slideupfragment;
import com.example.animationexamples.fragment.swapanimationfragment;
import com.example.animationexamples.fragment.togetheranimationfragment;
import com.example.animationexamples.fragment.zoominfragment;
import com.example.animationexamples.fragment.zoomoutfragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.action_settings);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {

            case R.id.nav_bounce:
                fragment = new bouncefragment();
                break;
            case R.id.nav_Blink:
                fragment = new blinkfragment();
                break;
            case R.id.nav_zoomin:
                fragment = new zoominfragment();
                break;
            case R.id.nav_zoomout:
                fragment = new zoomoutfragment();
                break;
            case R.id.nav_rotate:
                fragment = new rotatefragment();
                break;
            case R.id.nav_move:
                fragment = new movefragment();
                break;
            case R.id.nav_slideup:
                fragment = new slideupfragment();
                break;
            case R.id.nav_slidedown:
                fragment = new slidedownfragment();
                break;
            case R.id.nav_sequentialanimation:
                fragment = new sequentialanimationfragment();
                break;
            case R.id.nav_togetheranimation:
                fragment = new togetheranimationfragment();
                break;
            case R.id.nav_flip:
                fragment = new flipfragment();
                break;
            case R.id.nav_fade:
                fragment= new fadefragment();
                break;
            case  R.id.nav_drawable:
                fragment = new drawableanimationfragment();
                break;
            case R.id.nav_swap:
                fragment = new swapanimationfragment();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;

    }


}
