package com.msnegi.parampassing.serializable_object;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.msnegi.parampassing.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Student st = (Student) getIntent().getSerializableExtra("Student");

        TextView txt = findViewById(R.id.receivedParamTxt);
        txt.setText(st.getName() + "\n" + st.getAge() + "\n" + st.getResult());


    }
}