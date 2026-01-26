package com.msnegi.callbackexamples;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CallBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_back);

        TextView txtHello = (TextView) findViewById(R.id.txtHello);
        txtHello.setText("An activity can't make any callback to calling activity. " +
                "An activity return control to calling activity through onActivityResult() function only.");
    }
}
