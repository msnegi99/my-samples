package com.msnegi.parampassing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.msnegi.parampassing.parcelable_array.ParceFirstActivity;
import com.msnegi.parampassing.serializable_object.FirstActivity;

public class HomeFragment extends Fragment {

    Button serializableBtn, paracelableBtn;
    CallBackInterface callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (HomeActivity) context;
        Bundle bundle = getArguments();
    }

    @Override
    public void onResume() {
        super.onResume();
        callback.setTitle("Parameter Passing Example");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        serializableBtn = view.findViewById(R.id.serializableBtn);
        serializableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FirstActivity.class);
                startActivity(intent);
            }
        });

        paracelableBtn = view.findViewById(R.id.paracelableBtn);
        paracelableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ParceFirstActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
