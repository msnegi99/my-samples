package com.example.rightnavigationexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import androidx.core.view.GravityCompat;

public class MainActivity extends AppCompatActivity {

    private TextView username;
    private TextView email_id;
    public ImageView profile_image;

    private Toolbar toolbar;
    private NavigationView navView;
    private NavigationView navView1;
    private DrawerLayout drawerLayout;

    private ListView drawerList;
    private ArrayList<DrawerItem> drawerListItmes;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(new DrawerArrowDrawable(toolbar.getContext()));
//        ab.setDisplayHomeAsUpEnabled(false);

        //Initializing navView
        navView = (NavigationView) findViewById(R.id.nav_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)
            {
                //Closing drawer on item click
                drawerLayout.closeDrawers();

                username = (TextView) findViewById(R.id.username);
                email_id = (TextView) findViewById(R.id.email_id);
                profile_image = (ImageView) findViewById(R.id.profile_image);

                if(menuItem.getItemId() == R.id.menu){
                    Toast.makeText(MainActivity.this,"menu clicked", Toast.LENGTH_SHORT).show();
                }else if(menuItem.getItemId() == R.id.family_item){
                    Toast.makeText(MainActivity.this,"family_item clicked",Toast.LENGTH_SHORT).show();
                }else if(menuItem.getItemId() == R.id.friends_item){
                    Toast.makeText(MainActivity.this,"friends_item clicked",Toast.LENGTH_SHORT).show();
                }else if(menuItem.getItemId() == R.id.colleagues_item){
                    Toast.makeText(MainActivity.this,"colleagues_item clicked",Toast.LENGTH_SHORT).show();
                }else if(menuItem.getItemId() == R.id.messages){
                    Toast.makeText(MainActivity.this,"messages clicked",Toast.LENGTH_SHORT).show();
                }else if(menuItem.getItemId() == R.id.location_sharing){
                    Toast.makeText(MainActivity.this,"location_sharing clicked",Toast.LENGTH_SHORT).show();
                }else if(menuItem.getItemId() == R.id.rate_us){
                    Toast.makeText(MainActivity.this,"rate_us clicked",Toast.LENGTH_SHORT).show();
                }else if(menuItem.getItemId() == R.id.logout){
                    if(menuItem.getTitle().equals("Logout"))
                    {
                        Toast.makeText(MainActivity.this,"logout clicked",Toast.LENGTH_SHORT).show();
                        openDrawer();
                    }else{
                        Toast.makeText(MainActivity.this,"login clicked",Toast.LENGTH_SHORT).show();
                        closeDrawer();

                    }
                }



                return true;
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer)
        {
            @Override
            public void onDrawerClosed(View drawerView)
            {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        //drawerLayout.setViewScale(Gravity.START, 1f);
        //drawerLayout.setRadius(Gravity.START, 20f);
        //drawerLayout.setViewElevation(Gravity.START, 10f);
        //drawerLayout.setViewRotation(Gravity.START, 10f);

        drawerListItmes = new ArrayList<DrawerItem>();
        adapter = new DrawerListAdapter(this, drawerListItmes);

        drawerList = findViewById(R.id.drawerList);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DrawerItemClick(position);
            }
        });
        loadDrawerItems();

        //-- second navigation bar
        //Initializing navView
        navView1 = (NavigationView) findViewById(R.id.nav_view1);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, navView1);

        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDrawer();
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                closeDrawer();
            }
        });

    }

    private void DrawerItemClick(int position){
        drawerLayout.closeDrawer(GravityCompat.START);

        DrawerItem drawerItem = drawerListItmes.get(position);
        switch (drawerItem.getId()){

        }
    }

    private void openDrawer(){
        //drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
        //actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu_camera);
        actionBarDrawerToggle.syncState();*/

        drawerLayout.openDrawer(navView1);
        //actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu_camera);
    }

    public void closeDrawer(){
        if(drawerLayout.isDrawerOpen(navView1))
            drawerLayout.closeDrawer(navView1);
        /*drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu_camera);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();*/
    }

    private void loadDrawerItems() {
        drawerListItmes.clear();
        drawerListItmes.add(new DrawerItem().setId(1).setName(getString(R.string.menu_option_1)).setImageRes(R.drawable.ic_menu_gallery));
        drawerListItmes.add(new DrawerItem().setId(2).setName(getString(R.string.menu_option_2)).setImageRes(R.drawable.ic_menu_gallery));
        drawerListItmes.add(new DrawerItem().setId(3).setName(getString(R.string.menu_option_3)).setImageRes(R.drawable.ic_menu_gallery));
        drawerListItmes.add(new DrawerItem().setId(4).setName(getString(R.string.menu_option_4)).setImageRes(R.drawable.ic_menu_gallery));
        drawerListItmes.add(new DrawerItem().setId(5).setName(getString(R.string.menu_option_5)).setImageRes(R.drawable.ic_menu_gallery));
        drawerListItmes.add(new DrawerItem().setId(6).setName(getString(R.string.menu_option_6)).setImageRes(R.drawable.ic_menu_gallery));
        drawerListItmes.add(new DrawerItem().setId(7).setName(getString(R.string.menu_option_7)).setImageRes(R.drawable.ic_menu_gallery));
        drawerListItmes.add(new DrawerItem().setId(8).setName(getString(R.string.menu_option_8)).setImageRes(R.drawable.ic_menu_gallery));
        drawerListItmes.add(new DrawerItem().setId(9).setName(getString(R.string.menu_option_9)).setImageRes(R.drawable.ic_menu_gallery));
        drawerListItmes.add(new DrawerItem().setId(10).setName(getString(R.string.menu_option_10)).setImageRes(R.drawable.ic_menu_gallery));

        adapter.notifyDataSetChanged();

    }


    class DrawerListAdapter extends BaseAdapter {
        private Context context; //context
        private ArrayList<DrawerItem> drawerListItmes; //data source of the list adapter

        //public constructor
        public DrawerListAdapter(Context context, ArrayList<DrawerItem> drawerListItmes) {
            this.context = context;
            this.drawerListItmes = drawerListItmes;
        }

        @Override
        public int getCount() {
            return drawerListItmes.size(); //returns total of items in the list
        }

        @Override
        public Object getItem(int position) {
            return drawerListItmes.get(position); //returns list item at the specified position
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // inflate the layout for each list row
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.view_drawer_listitem, parent, false);
            }

            DrawerItem drawerItem = drawerListItmes.get(position);

            // get the TextView for item name and item description
            ImageView drawer_icon = (ImageView) convertView.findViewById(R.id.drawer_icon);
            TextView drawer_text = (TextView)  convertView.findViewById(R.id.drawer_text);

            drawer_icon.setImageResource(drawerItem.getImageRes());
            drawer_text.setText(drawerItem.getName());

            // returns the view for the current row
            return convertView;
        }
    }

    public void resetNavigation()
    {
        navView.getMenu().findItem(R.id.family_item).setChecked(false);
        navView.getMenu().findItem(R.id.friends_item).setChecked(false);
        navView.getMenu().findItem(R.id.colleagues_item).setChecked(false);
        navView.getMenu().findItem(R.id.messages).setChecked(false);
        navView.getMenu().findItem(R.id.location_sharing).setChecked(false);
        navView.getMenu().findItem(R.id.rate_us).setChecked(false);
        navView.getMenu().findItem(R.id.logout).setChecked(false);
    }


    public void onLoggedin()
    {
        setNavTitle("Login", "Logout");
        //updateCart();
        //loadHome();
    }

    public void setPhotoProfile(String uname, String email, String personPhotoUrl)
    {
        username.setText(uname);
        email_id.setText(email);

        new LoadProfileImage(profile_image).execute(personPhotoUrl);
    }

    private void logoutUser()
    {
        Toast.makeText(this,"User Logged out...",Toast.LENGTH_LONG).show();

        setNavTitle("Logout","Login");
        setProfile("Welcome Guest", "");
    }

    public void setProfile(String uname,String email)
    {
        username.setText(uname);
        email_id.setText(email);
    }

    boolean menuLoaded = false;

    public void setNavTitle(String mItem,String mNewItem)
    {
        Menu m = navView.getMenu();

        for(int i=0; i<m.size(); i++)
        {
            MenuItem mi = m.getItem(i);
            if(mi.getTitle().equals(mItem))
            {
                mi.setTitle(mNewItem);
            }
        }
    }

    /**
     * Background Async task to load user profile picture from url
     * */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap>
    {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage)
        {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls)
        {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;

            try
            {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            }
            catch (Exception e)
            {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result)
        {
            bmImage.setImageBitmap(result);
        }
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


}


