package com.negi.fragmentdynamics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PlusOneFragment.printComma
{
    int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                n++;

                Bundle bundle = new Bundle();
                bundle.putInt("n",n);

                PlusOneFragment frg = new PlusOneFragment();
                frg.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);  //works with add and replace and backstack
                //transaction.replace(R.id.container, frg, "a"+n);
                transaction.add(R.id.container, frg, "a"+n);
                transaction.commit();
            }
        });

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent("printlog");
                getApplicationContext().sendBroadcast(intent);

            }
        });

        Button show = (Button) findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                FragmentManager fragmentManager = getSupportFragmentManager();
                List<Fragment> fragments = fragmentManager.getFragments();
                if(fragments != null)
                {
                    for(Fragment fragment : fragments)
                    {
                        if(fragment != null && !fragment.isVisible())
                        {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);  //works
                            transaction.show(fragment).commit();
                        }
                    }
                }
            }
        });
    }

    public void printcomma()
    {
        System.out.print(",");
    }
}
