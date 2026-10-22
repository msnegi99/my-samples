package com.msnegi.fragmentdynamics

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), PlusOneFragment.printComma {
    var n: kotlin.Int = 0

    protected override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val add = findViewById(R.id.add) as android.widget.Button
        add.setOnClickListener(object : android.view.View.OnClickListener {
            override fun onClick(v: android.view.View?)
            {
                n++

                val bundle = android.os.Bundle()
                bundle.putInt("n", n)

                val frg = PlusOneFragment()
                frg.setArguments(bundle)

                val transaction: FragmentTransaction = getSupportFragmentManager().beginTransaction()
                transaction.setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_right
                ) //works with add and replace and backstack
                //transaction.replace(R.id.container, frg, "a"+n);
                transaction.add(R.id.container, frg, "a" + n)
                transaction.commit()
            }
        })

        val submit = findViewById(R.id.submit) as android.widget.Button
        submit.setOnClickListener(object : android.view.View.OnClickListener {
            override fun onClick(v: android.view.View?)
            {
                val intent = android.content.Intent("printlog")
                getApplicationContext().sendBroadcast(intent)
            }
        })

        val show = findViewById(R.id.show) as android.widget.Button
        show.setOnClickListener(object : android.view.View.OnClickListener {
            override fun onClick(v: android.view.View?)
            {
                val fragmentManager: FragmentManager = getSupportFragmentManager()
                val fragments: kotlin.collections.MutableList<Fragment?>? = fragmentManager.getFragments()
                if (fragments != null)
                {
                    for (fragment in fragments)
                    {
                        if (fragment != null && !fragment.isVisible())
                        {
                            val transaction: FragmentTransaction = getSupportFragmentManager().beginTransaction()
                            transaction.setCustomAnimations(
                                R.anim.slide_in_right,
                                R.anim.slide_out_right
                            ) //works
                            transaction.show(fragment).commit()
                        }
                    }
                }
            }
        })
    }

    override fun printcomma() {
        kotlin.io.print(",")
    }
}
