package com.msnegi.toolbarmenuexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.min


class MainActivity : AppCompatActivity() {
    companion object {
        var menu: Menu? = null
    }
    var flag: Boolean = false
    var textCartItemCount: TextView? = null
    var mCartItemCount: Int = 0
    var homeButton: ImageButton? = null
    var syncButton: ImageButton? = null
    private var toolbar: Toolbar? = null
    private var tv_title: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initializing Toolbar and setting it as the actionbar
        toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar);

        tv_title = findViewById(R.id.headerBarText)
        tv_title!!.text = "Home Activity"

        var ab: ActionBar? = getSupportActionBar()
        ab?.setHomeButtonEnabled(true);
        ab?.setDisplayHomeAsUpEnabled(false);
        //ab.setHomeAsUpIndicator(R.drawable.right_arrow);

        var homeButton = findViewById<ImageButton?>(R.id.homeButton)
        homeButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "home button", Toast.LENGTH_SHORT).show()
            }
        })

        var syncButton = findViewById<ImageButton?>(R.id.syncButton)
        syncButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "sync button", Toast.LENGTH_SHORT).show()
            }
        })

        val btn = findViewById<View?>(R.id.btnAdd) as Button
        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mCartItemCount++
                setupBadge()
            }
        })
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        Companion.menu = menu
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        val versionItem = menu!!.findItem(R.id.version)
        //versionItem.title = getVersion()

        val menuItem = menu.findItem(R.id.action_cart)

        val actionView = menuItem.actionView
        if (actionView != null) {
            textCartItemCount = actionView.findViewById<View?>(R.id.cart_badge) as TextView?
            setupBadge()

            actionView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    onOptionsItemSelected(menuItem)
                }
            })
        }

        return true
    }

    private fun setupBadge() {
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount!!.visibility != View.GONE) {
                    textCartItemCount!!.visibility = View.GONE
                }
            } else {
                textCartItemCount!!.text = min(mCartItemCount, 99).toString()
                if (textCartItemCount!!.visibility != View.VISIBLE) {
                    textCartItemCount!!.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.home) {
            Toast.makeText(this, "home button", Toast.LENGTH_SHORT).show()
            if (flag) {
                flag = false
                item.setIcon(R.drawable.ic_home)
            } else {
                flag = true
                item.setIcon(R.drawable.ic_cart)
            }
            return true
        } else if (itemId == R.id.action_cart) {
            Toast.makeText(this, "cart button", Toast.LENGTH_SHORT).show()
            return true
        }

        if (item.itemId == R.id.version) {
            Toast.makeText(this, "version button", Toast.LENGTH_SHORT).show()
            return true
        } else if (item.itemId == R.id.assistance) {
            //replaceFragment(AssistanceFragment(), Bundle(), "AssistanceFragment")
            Toast.makeText(this, "assistance button", Toast.LENGTH_SHORT).show()
            return true
        } else if (item.itemId == R.id.sync) {
            Toast.makeText(this, "sync button", Toast.LENGTH_SHORT).show()
            return true
        } else if (item.itemId == R.id.logout) {
            Toast.makeText(this, "logout button", Toast.LENGTH_SHORT).show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}
