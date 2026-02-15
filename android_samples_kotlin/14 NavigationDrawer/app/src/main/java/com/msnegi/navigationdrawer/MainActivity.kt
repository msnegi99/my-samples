package com.msnegi.navigationdrawer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var username: TextView? = null
    private var email_id: TextView? = null
    var profile_image: ImageView? = null

    //Defining Variables
    private var toolbar: Toolbar? = null
    private var navigationView: NavigationView? = null
    private var drawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<View?>(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        navigationView = findViewById<View?>(R.id.navigation_view) as NavigationView
        navigationView!!.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                drawerLayout!!.closeDrawers()

                username = findViewById<View?>(R.id.username) as TextView
                email_id = findViewById<View?>(R.id.email_id) as TextView
                profile_image = findViewById<View?>(R.id.profile_image) as ImageView

                val itemId = menuItem.getItemId()
                if (itemId == R.id.menu) {
                    Toast.makeText(getApplicationContext(), "menu", Toast.LENGTH_SHORT).show()
                    return true
                } else if (itemId == R.id.food_menu_1) {
                    Toast.makeText(getApplicationContext(), "food_menu_1", Toast.LENGTH_SHORT)
                        .show()
                    return true
                } else if (itemId == R.id.food_menu_2) {
                    Toast.makeText(getApplicationContext(), "food_menu_2", Toast.LENGTH_SHORT)
                        .show()
                    return true
                } else if (itemId == R.id.food_menu_3) {
                    Toast.makeText(getApplicationContext(), "food_menu_3", Toast.LENGTH_SHORT)
                        .show()
                    return true
                } else if (itemId == R.id.food_menu_4) {
                    Toast.makeText(getApplicationContext(), "food_menu_4", Toast.LENGTH_SHORT)
                        .show()
                    return true
                } else if (itemId == R.id.rate_us) {
                    Toast.makeText(getApplicationContext(), "rate_us", Toast.LENGTH_SHORT).show()
                    return true
                } else if (itemId == R.id.logout) {
                    Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_SHORT).show()
                    return true
                } else {
                    Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT)
                        .show()
                    return true
                }
            }
        })

        drawerLayout = findViewById<View?>(R.id.drawer) as DrawerLayout
        val actionBarDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.openDrawer,
            R.string.closeDrawer
        ) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView!!)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView!!)
            }
        }

        drawerLayout!!.setDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        //loadHome();
    }

    fun resetNavigation() {
        navigationView!!.getMenu().findItem(R.id.food_menu_1).setChecked(false)
        navigationView!!.getMenu().findItem(R.id.food_menu_2).setChecked(false)
        navigationView!!.getMenu().findItem(R.id.food_menu_3).setChecked(false)
        navigationView!!.getMenu().findItem(R.id.food_menu_4).setChecked(false)
        navigationView!!.getMenu().findItem(R.id.rate_us).setChecked(false)
        navigationView!!.getMenu().findItem(R.id.logout).setChecked(false)
    }

    fun onLoggedin() {
        setNavTitle("Login", "Logout")
    }

    fun setPhotoProfile(uname: String?, email: String?, personPhotoUrl: String?) {
        username!!.setText(uname)
        email_id!!.setText(email)

        LoadProfileImage(profile_image!!).execute(personPhotoUrl)
    }

    private fun logoutUser() {
        Toast.makeText(this, "User Logged out...", Toast.LENGTH_LONG).show()

        setNavTitle("Logout", "Login")
        setProfile("Welcome Guest", "")
    }

    fun setProfile(uname: String?, email: String?) {
        username!!.setText(uname)
        email_id!!.setText(email)
    }

    var menuLoaded: Boolean = false

    fun setNavTitle(mItem: String?, mNewItem: String?) {
        val m = navigationView!!.getMenu()

        for (i in 0..<m.size()) {
            val mi = m.getItem(i)
            if (mi.getTitle() == mItem) {
                mi.setTitle(mNewItem)
            }
        }
    }

    private inner class LoadProfileImage(var bmImage: ImageView) :
        AsyncTask<String?, Void?, Bitmap?>() {
        override fun doInBackground(vararg urls: String?): Bitmap? {
            val urldisplay: String? = urls[0]
            var mIcon11: Bitmap? = null

            try {
                val `in` = URL(urldisplay).openStream()
                mIcon11 = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error", e.message!!)
                e.printStackTrace()
            }
            return mIcon11
        }

        override fun onPostExecute(result: Bitmap?) {
            bmImage.setImageBitmap(result)
        }
    }
}
