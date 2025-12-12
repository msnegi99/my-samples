package com.msnegi.basiccomponents;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RadioButton radio1, radio2, radio3;
    LinearLayout mainLayout;
    int [] images = {
            R.drawable.avacado,
            R.drawable.banana,
            R.drawable.chakotha,
            R.drawable.chikku,
            R.drawable.coconut,
            R.drawable.custard_apple,
            R.drawable.dates,
            R.drawable.lychees,
            R.drawable.mango,
            R.drawable.mulberry,
            R.drawable.muskmelon,
            R.drawable.oranges,
            R.drawable.papayas,
            R.drawable.pears,
            R.drawable.star_fruit,
            R.drawable.watermelon
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Product> products = new ArrayList<Product>();
        products.add(new Product(images[0],"avacado",""));
        products.add(new Product(images[1],"banana",""));
        products.add(new Product(images[2],"chakotha",""));
        products.add(new Product(images[3],"chikku",""));
        products.add(new Product(images[4],"coconut",""));
        products.add(new Product(images[5],"custard_apple",""));
        products.add(new Product(images[6],"dates",""));
        products.add(new Product(images[7],"lychees",""));

        TextView grid_text = findViewById(R.id.grid_text);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        GridViewAdapter adapter = new GridViewAdapter(this, products);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                grid_text.setText("You selected : " + products.get(position).gettitle());
            }
        });


        ListView listLst = findViewById(R.id.listLst);
        TextView list_text = findViewById(R.id.list_text);

        String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand","United States","Britain"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.layout_listview, R.id.textView, countryList);
        listLst.setAdapter(arrayAdapter);
        listLst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                list_text.setText("You selected : " + countryList[position]);
            }
        });

        mainLayout = findViewById(R.id.mainLayout);
        Button simpleSnackBar = findViewById(R.id.simpleSnackBar);
        simpleSnackBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(mainLayout,"This is Simple Snackbar",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        Button snackbarWithAction = findViewById(R.id.snackbarWithAction);
        snackbarWithAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(mainLayout,"Snackbar With Action",Snackbar.LENGTH_SHORT);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"Undo action",Toast.LENGTH_SHORT).show();
                    }
                });
                snackbar.show();
            }
        });


        Button customSnackbar = findViewById(R.id.customSnackbar);
        customSnackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(mainLayout,"Custom Snackbar",Snackbar.LENGTH_SHORT);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"Undo action",Toast.LENGTH_SHORT).show();
                    }
                });

                snackbar.setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();
                TextView snackbarText = snackbarView.findViewById(R.id.snackbar_text);
                snackbarText.setTextColor(Color.YELLOW);
                snackbarText.setText("dynamic title");
                snackbar.show();
            }
        });

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View toastRoot = inflater.inflate(R.layout.custom_toast, null);

                Toast toast = new Toast(getApplicationContext());
                toast.setView(toastRoot);
                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }
        });

        autoCompleteTextView();
        switchView();
        radioGroup();
        checkBox();
        SeekBarView();
        ratingBarView();
        rangeBarView();
    }

    public void autoCompleteTextView()
    {
        AutoCompleteTextView autocompletetextview;
        String[] array = { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };

        autocompletetextview = (AutoCompleteTextView) findViewById(R.id.autocompletetextview);
        autocompletetextview.setThreshold(1);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item, array);
        autocompletetextview.setAdapter(adapter);
    }

    public void switchView(){
        TextView txtSwitchOutput = findViewById(R.id.txtSwitchOutput);

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Is the toggle on?
                boolean on = ((Switch) view).isChecked();

                if (on) {
                    txtSwitchOutput.setText("Switch Button is ON");
                } else {
                    txtSwitchOutput.setText("Switch Button is OFF");
                }
            }
        });
    }

    public void radioGroup(){
        radio1 = findViewById(R.id.radioButton1);
        radio1.setOnClickListener(view1 -> {
            onRadioButtonClicked(view1);
        });

        radio2 = findViewById(R.id.radioButton2);
        radio2.setOnClickListener(view1 -> {
            onRadioButtonClicked(view1);
        });

        radio3 = findViewById(R.id.radioButton3);
        radio3.setOnClickListener(view1 -> {
            onRadioButtonClicked(view1);
        });
    }

    public void onRadioButtonClicked(View view) {
        TextView txtRadioGroupOutput = findViewById(R.id.txtRadioGroupOutput);

        switch (view.getId()) {
            case R.id.radioButton1:
                txtRadioGroupOutput.setText("Radio Button 1 Selected");
                break;
            case R.id.radioButton2:
                txtRadioGroupOutput.setText("Radio Button 2 Selected");
                break;
            case R.id.radioButton3:
                txtRadioGroupOutput.setText("Radio Button 3 Selected");
                break;
        }
    }

    public void checkBox(){
        TextView txtCheckBoxOutput = findViewById(R.id.txtCheckBoxOutput);

        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Is the toggle on?
                boolean on = ((CheckBox) view).isChecked();

                if (on) {
                    txtCheckBoxOutput.setText("Check box is Selected.");
                } else {
                    txtCheckBoxOutput.setText("Check box is Unselected.");
                }
            }
        });

        CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Is the toggle on?
                boolean on = ((CheckBox) view).isChecked();

                if (on) {
                    txtCheckBoxOutput.setText("Check box is Selected.");
                } else {
                    txtCheckBoxOutput.setText("Check box is Unselected.");
                }
            }
        });

        CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Is the toggle on?
                boolean on = ((CheckBox) view).isChecked();

                if (on) {
                    txtCheckBoxOutput.setText("Check box is Selected.");
                } else {
                    txtCheckBoxOutput.setText("Check box is Unselected.");
                }
            }
        });
    }

    public void SeekBarView(){
        TextView txtSeekBarOutput = findViewById(R.id.txtSeekBarOutput);

        SeekBar simpleSeekBar = (SeekBar) findViewById(R.id.simpleSeekBar);
        simpleSeekBar.setMax(200); // 200 maximum value for the Seek bar
        simpleSeekBar.setProgress(50); // 50 default progress value
        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                txtSeekBarOutput.setText("Seek bar progress is :" + progressChangedValue);
            }
        });
    }

    public void ratingBarView(){
        RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar); // initiate a rating bar
        simpleRatingBar.setRating((float) 3.5); // set default rating

        simpleRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String rating = String.valueOf(simpleRatingBar.getRating());
                TextView output = findViewById(R.id.txtRatingBarOutput);
                output.setText(rating);
            }
        });
    }

    public void rangeBarView(){
        RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(MainActivity.this);
        rangeSeekBar.setRangeValues(15, 90);
        rangeSeekBar.setSelectedMinValue(20);
        rangeSeekBar.setSelectedMaxValue(88);

        LinearLayout layout = findViewById(R.id.seekbar_placeholder);
        layout.addView(rangeSeekBar);

        EditText range = findViewById(R.id.minRange);

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                //  //Now you have the minValue and maxValue of your RangeSeekbar
                // Toast.makeText(getActivity(), minValue + "-" + maxValue, Toast.LENGTH_LONG).show();
                range.setText(minValue + " - " + maxValue);
            }
        });
    }

}