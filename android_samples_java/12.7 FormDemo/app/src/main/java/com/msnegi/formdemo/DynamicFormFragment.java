package com.msnegi.formdemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.*;
import java.util.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class DynamicFormFragment extends Fragment {

    ScrollView sv;
    LinearLayout ll, main_content;
    CallBackInterface callback;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (HomeActivity) context;
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        callback.setTitle("Dynamic Form Sample");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dynamic_form, container, false);

        main_content = view.findViewById(R.id.main_content);

        sv = new ScrollView(getActivity());
        ll = new LinearLayout(getActivity());
        ll.setPadding(5, 2, 5, 2);

        sv.setBackgroundColor(getResources().getColor(R.color.white));
        sv.addView(ll);
        ll.setOrientation(LinearLayout.VERTICAL);

        View view1 = createTextView(1,true, 1,1, "Hello World");
        ll.addView(view1);

        View view2 = createEditText(2,100, "Text", "Hint Text",  true, true,1,1, "Hello World");
        ll.addView(view2);

        View view3 = createCheckBox(3, "Is Red ?");
        ll.addView(view3);

        ArrayList<String> strArr = new ArrayList<>();
        strArr.add("One");
        strArr.add("Two");
        strArr.add("Three");

        View view4 = createRadioGroup(strArr);
        ll.addView(view4);

        View view5 = createSpinner(getActivity(),new String[]{"fff","gg","srf"});
        ll.addView(view5);

        View view6 = createCalendarView();
        ll.addView(view6);

        createDatePicker();
        createTimePicker();

        //setContentView(sv);
        main_content.addView(sv);

        return view;
    }


    public View createTextView(int fieldID, boolean isSingleLine, int minLines, int maxLines, String value)
    {
        Typeface face = Typeface.createFromAsset(getActivity().getResources().getAssets(), "fonts/Poppins-Regular.ttf");

        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.setMargins(10, 10, 10, 10);

        TextView textView = new TextView(getActivity());
        textView.setLayoutParams(lparams);
        //textView.setBackgroundResource(R.drawable.dynamic_edittxt_bg);
        textView.setId(fieldID);
        textView.setTypeface(face);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28f);

        textView.setSingleLine(isSingleLine);
        textView.setLines(minLines);
        textView.setMaxLines(maxLines);

        textView.setText(value);
        return textView;
    }

    public View createEditText(int fieldID, int maxLen, String dataType, String displayText, boolean isEditable, boolean isSingleLine, int minLines, int maxLines, String value)
    {
        Typeface face = Typeface.createFromAsset(getActivity().getResources().getAssets(), "fonts/Poppins-Regular.ttf");
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.setMargins(10, 10, 10, 10);

        EditText editText = new EditText(getActivity());
        //editText.setTypeface(type);
        editText.setLayoutParams(lparams);
        editText.setBackgroundResource(R.drawable.edittxt_bg);
        editText.setId(fieldID);
        editText.setGravity(Gravity.TOP);
        editText.setTypeface(face);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);

        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(Integer.valueOf(maxLen));
        editText.setFilters(FilterArray);

        if (dataType.equals("Text")) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
        }else if (dataType.equals("Numeric")) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else if(dataType.equals("Password")){
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        editText.setHint(displayText);

        editText.setEnabled(isEditable);
        editText.setSingleLine(isSingleLine);
        editText.setLines(minLines);
        editText.setMaxLines(maxLines);

        editText.setText(value);
        return editText;
    }

    public View createCheckBox(int fieldID, String value)
    {
        Typeface face = Typeface.createFromAsset(getActivity().getResources().getAssets(), "fonts/Poppins-Regular.ttf");
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.setMargins(10, 10, 10, 10);

        CheckBox checkBox = new CheckBox(getActivity());
        checkBox.setText(value);
        checkBox.setId(fieldID);
        checkBox.setTypeface(face);

        return checkBox;
    }

    public View createRadioGroup(ArrayList<String> strArr)
    {
        RadioGroup grp = new RadioGroup(getActivity());

        for(int n=0; n<strArr.size(); n++) {
            RadioButton rb = new RadioButton(getActivity());
            rb.setText(strArr.get(n));
            rb.setId(n);
            grp.addView(rb);
        }

        return grp;
    }

    public View createSpinner(Context context,String [] stringList)
    {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        Spinner spinner = new Spinner(getActivity());
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.brown), PorterDuff.Mode.SRC_ATOP);
        //spinner.setBackground(getResources().getDrawable(R.drawable.dynamic_edittxt_bg));
        spinner.setLayoutParams(layoutParams);
        layoutParams.setMargins(20, 20, 20, 20);

        ArrayAdapter spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, stringList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        return spinner;
    }

    public View createCalendarView(){

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CalendarView calendarView = new CalendarView(getActivity());
        layoutParams.setMargins(20, 20, 20, 20);
        calendarView.setLayoutParams(layoutParams);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Note that months are indexed from 0. So, 0 means january, 1 means February, 2 means march etc.
                String msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(getActivity(),
                        msg,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return calendarView;
    }

    public void createDatePicker(){

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Toast.makeText(getActivity(), dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_LONG).show();

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    public void createTimePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(getActivity(), hourOfDay + ":" + minute, Toast.LENGTH_LONG).show();
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }    
}