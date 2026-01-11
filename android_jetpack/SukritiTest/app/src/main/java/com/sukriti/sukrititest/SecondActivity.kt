package com.sukriti.sukrititest

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sukriti.sukrititest.databinding.ActivityFirstBinding
import com.sukriti.sukrititest.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity(), CallbackInterface {
    var oneFragment: OneFragment? = null
    var twoFragment: TwoFragment? = null
    var threeFragment: ThreeFragment? = null

    lateinit var binding: ActivitySecondBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = Bundle()
        bundle.putInt("initvalue", getIntent().getStringExtra("initvalue")!!.toInt())

        oneFragment = OneFragment()
        twoFragment = TwoFragment()
        threeFragment = ThreeFragment()

        replaceFragment(oneFragment!!, binding.firstcontainer!!, null, "OneFragment")
        replaceFragment(twoFragment!!, binding.secondcontainer!!, bundle, "TwoFragment")
        replaceFragment(threeFragment!!, binding.thirdcontainer!!, null, "ThreeFragment")
    }

    fun replaceFragment(fragment: Fragment, ll: LinearLayout, bundle: Bundle?, tag: String?) {
        if (bundle != null) {
            fragment.setArguments(bundle)
        }

        val fm = getSupportFragmentManager()
        fm.beginTransaction().add(ll.getId(), fragment, tag).commit()
    }

    override fun increment() {
        twoFragment!!.incrementNumber()
    }

    override fun decrement() {
        twoFragment!!.decrementNumber()
    }
}