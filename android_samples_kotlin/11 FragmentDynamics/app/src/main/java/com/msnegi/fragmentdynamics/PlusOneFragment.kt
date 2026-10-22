package com.msnegi.fragmentdynamics

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class PlusOneFragment : Fragment() {
    var id: EditText? = null
    var type: Spinner? = null
    var remove: android.widget.Button? = null
    var hide: android.widget.Button? = null

    var bundle: android.os.Bundle? = null
    var n: kotlin.Int = 0

    var arr: kotlin.Array<kotlin.String?> =
        kotlin.arrayOf<kotlin.String?>("Type A", "Type B", "Type C")

    var rec: MyBroadcastRec? = null
    var pc: printComma? = null

    interface printComma {
        fun printcomma()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        pc = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: android.view.View = inflater.inflate(R.layout.fragment_plus_one, container, false)

        bundle = getArguments()
        n = bundle!!.getInt("n")

        id = view.findViewById<android.view.View?>(R.id.id) as EditText
        type = view.findViewById<android.view.View?>(R.id.type) as Spinner
        remove = view.findViewById<android.view.View?>(R.id.remove) as android.widget.Button
        hide = view.findViewById<android.view.View?>(R.id.hide) as android.widget.Button

        remove!!.setOnClickListener(object : android.view.View.OnClickListener
        {
            override fun onClick(v: android.view.View?)
            {
                val frg: Fragment? = getFragmentManager()?.findFragmentByTag("a" + n) as Fragment?

                val transaction: FragmentTransaction = getFragmentManager()?.beginTransaction() as FragmentTransaction
                transaction.setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_right
                ) //does not work here
                transaction.remove(frg!!).commit()
            }
        })

        hide!!.setOnClickListener(object : android.view.View.OnClickListener
        {
            override fun onClick(v: android.view.View?)
            {
                val frg: Fragment? = getFragmentManager()?.findFragmentByTag("a" + n) as Fragment?

                val transaction: FragmentTransaction = getFragmentManager()?.beginTransaction() as FragmentTransaction
                transaction.setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_right
                ) //does not work with last fragment ???
                transaction.hide(frg!!).commit()
            }
        })

        val adapter: ArrayAdapter<String?> = ArrayAdapter<kotlin.String?>(requireActivity(), android.R.layout.simple_spinner_item, arr)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        type?.setAdapter(adapter)

        rec = MyBroadcastRec()
        ContextCompat.registerReceiver(
            requireActivity(),
            rec,
            IntentFilter("printlog"),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        return view
    }

    inner class MyBroadcastRec : BroadcastReceiver() {
        override fun onReceive(context: android.content.Context?, intent: android.content.Intent?) {
            kotlin.io.println("{")
            kotlin.io.println("\"id\":" + "\"" + id?.getText().toString() + "\",")
            kotlin.io.println("\"type\":" + "\"" + type?.getSelectedItem().toString() + "\"")
            kotlin.io.println("}")

            pc!!.printcomma()
        }
    }

    public override fun onDestroy() {
        super.onDestroy()

        getActivity()?.unregisterReceiver(rec)
    }
}
