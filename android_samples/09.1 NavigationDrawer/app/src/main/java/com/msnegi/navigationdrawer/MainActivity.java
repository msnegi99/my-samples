package com.msnegi.navigationdrawer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Toast;
import android.widget.ImageView;

//import com.msnegi.bostonpizzahouseoffline.login.AppConst;
//import com.msnegi.bostonpizzahouse.login.DatabaseHandler;
//import com.msnegi.bostonpizzahouseoffline.login.SessionManager;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity
{
    private TextView username;
    private TextView email_id;
    public ImageView profile_image;

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        /*if (!AppConst.session.isLoggedIn())
        {
            setNavTitle("Logout","Login");
        }*/

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)
            {
                //navigationView.getMenu().findItem(R.id.food_menu_1).setChecked(false);

                //Checking if the item is in checked state or not, if not make it in checked state
/*
                if (menuItem.isChecked())
                    menuItem.setChecked(false);
                else
                    menuItem.setChecked(true);
*/

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                username = (TextView) findViewById(R.id.username);
                email_id = (TextView) findViewById(R.id.email_id);
                profile_image = (ImageView) findViewById(R.id.profile_image);

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId())
                {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.menu:
                        // Toast.makeText(getApplicationContext(), "menu Selected", Toast.LENGTH_SHORT).show();
                        //loadHome();
                        //ContentFragment fragment = new ContentFragment();
                        //android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        //fragmentTransaction.replace(R.id.frame, fragment);
                        //fragmentTransaction.commit();
                        return true;

                    case R.id.food_menu_1:
                        //Fragment fragment1 =  new PizzaFragment();
                        //FragmentManager fragmentManager1 = getSupportFragmentManager();
                        //fragmentManager1.beginTransaction().replace(R.id.frame_container, fragment1).commit();
                        return true;

                    case R.id.food_menu_2:
                        //Fragment fragment2 =  new PastaFragment();
                        //FragmentManager fragmentManager2 = getSupportFragmentManager();
                        //fragmentManager2.beginTransaction().replace(R.id.frame_container, fragment2).commit();
                        return true;

                    case R.id.food_menu_3:
                        //Fragment fragment3 =  new DrinksFragment();
                        //FragmentManager fragmentManager3 = getSupportFragmentManager();
                        //fragmentManager3.beginTransaction().replace(R.id.frame_container, fragment3).commit();
                        return true;

                    case R.id.food_menu_4:
                        //Fragment fragment4 =  new ComboFragment();
                        //FragmentManager fragmentManager4 = getSupportFragmentManager();
                        //fragmentManager4.beginTransaction().replace(R.id.frame_container, fragment4).commit();
                        return true;

                    case R.id.rate_us:

                        //String myUrl ="https://play.google.com/store/apps/details?id=com.msnegi.mahalakshmi";
                        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(myUrl)));
                        return true;

                    case R.id.logout:
                        //Toast.makeText(getApplicationContext(), "TITLE : " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                        if(menuItem.getTitle().equals("Logout"))
                        {
                            logoutUser();
                            Toast.makeText(getApplicationContext(), "logout Selected ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // Toast.makeText(getApplicationContext(), "LOGIN Selected ", Toast.LENGTH_SHORT).show();

                            //-- Load LOGIN Fragment
                            //Fragment fragment =  new LoginActivity();
                            //FragmentManager fragmentManager = getSupportFragmentManager();
                            //fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                        }
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer)
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

        //loadHome();
    }

    public void resetNavigation()
    {
        navigationView.getMenu().findItem(R.id.food_menu_1).setChecked(false);
        navigationView.getMenu().findItem(R.id.food_menu_2).setChecked(false);
        navigationView.getMenu().findItem(R.id.food_menu_3).setChecked(false);
        navigationView.getMenu().findItem(R.id.food_menu_4).setChecked(false);
        navigationView.getMenu().findItem(R.id.rate_us).setChecked(false);
        navigationView.getMenu().findItem(R.id.logout).setChecked(false);

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
        Menu m = navigationView.getMenu();

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
}
