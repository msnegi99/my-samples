package com.sukriti.sukrititest

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.sukriti.sukrititest.databinding.FragmentOneBinding
import com.sukriti.sukrititest.databinding.FragmentThreeBinding

class OneFragment : Fragment() {
    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!
    var callbackInterface: CallbackInterface? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        callbackInterface = activity as SecondActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneBinding.inflate(inflater, container, false)

        binding.incrementImgbtn!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                callbackInterface!!.increment()
            }
        })

        return binding.root
    }
}