package com.sukriti.sukrititest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import com.sukriti.sukrititest.databinding.FragmentOneBinding
import com.sukriti.sukrititest.databinding.FragmentTwoBinding

class TwoFragment : Fragment() {
    private var _binding: FragmentTwoBinding? = null
    private val binding get() = _binding!!
    var callbackInterface: CallbackInterface? = null
    var n: Int = 0

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        callbackInterface = activity as SecondActivity
        n = requireArguments().getInt("initvalue")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTwoBinding.inflate(inflater, container, false)

        binding.resultvaluetxt!!.setText(n.toString())
        binding.submitbtn!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent()
                intent.putExtra("n", n)
                requireActivity().setResult(1004, intent)
                requireActivity().finish()
            }
        })

        return binding.root
    }

    fun incrementNumber() {
        if (n < 100) {
            n++
            binding.resultvaluetxt!!.setText(n.toString())
        }
    }

    fun decrementNumber() {
        if (n > 0) {
            n--
            binding.resultvaluetxt!!.setText(n.toString())
        }
    }
}


