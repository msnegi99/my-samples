package com.msnegi.callbackexamples

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class CallbackFragment : Fragment() {
    var callBackInterface: CallBackInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            callBackInterface = context as MainActivity
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnListItemSelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_callback, container, false)

        val btnCallback = view.findViewById<View?>(R.id.btnCallback) as Button
        btnCallback.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                callBackInterface!!.callbackfunction("This is a callback from Fragment !!!")
            }
        })

        return view
    }
}
