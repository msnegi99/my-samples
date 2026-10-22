package com.msnegi.parampassing.serializable_object;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.msnegi.parampassing.R;

public class FirstActivity extends AppCompatActivity {

    Button callBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Student st = new Student();

        st.setName("Kartik");
        st.setAge(12);
        st.setResult(true);

        findViewById(R.id.callBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra("Student",st);
                startActivity(intent);
            }
        });

    }
}