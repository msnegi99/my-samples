package com.msnegi.dateprocessing;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.msnegi.dateprocessing.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateFragment extends Fragment {

    AppCompatTextView calendar1, calendar2, calendar3, calendar4;
    Date date1 = new Date();
    TextView dateFrom, dateTo;
    String strMonth = "";
    String strDay = "";
    EditText diffDate;

    AppCompatSpinner spnDateFormat1;
    TextView txtDateFormat1, selectedTimeTV;
    Button btn_compare, pickTimeBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date, container, false);

        TimePicker simpleTimePicker = (TimePicker) view.findViewById(R.id.simpleTimePicker); // initiate a time picker
        // set the value for current hours
        simpleTimePicker.setHour(5); // from api level 23
        simpleTimePicker.setMinute(35); // from api level 23
        int hours =simpleTimePicker.getHour(); // after api level 23
        int minutes = simpleTimePicker.getMinute(); // after api level 23
        simpleTimePicker.setIs24HourView(true); // set 24 hours mode for the time picker
        Boolean mode=simpleTimePicker.is24HourView(); // check the current mode of the time picker

        // on below line we are initializing our variables.
        pickTimeBtn = view.findViewById(R.id.idBtnPickTime);
        selectedTimeTV = view.findViewById(R.id.idTVSelectedTime);

        pickTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                            {
                                // on below line we are setting selected time
                                // in our text view.
                                StringBuilder twelveHr = showTime(hourOfDay, minute);
                                selectedTimeTV.setText(hourOfDay + ":" + minute + " | " + twelveHr.toString());
                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });

        calendar1 = view.findViewById(R.id.calendar1);
        calendar2 = view.findViewById(R.id.calendar2);
        calendar3 = view.findViewById(R.id.calendar3);
        calendar4 = view.findViewById(R.id.calendar4);

        calendar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker1(calendar1);
            }
        });

        calendar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker2(calendar2);
            }
        });

        calendar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker3(calendar3);
            }
        });

        calendar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker3(calendar4);
            }
        });

        btn_compare = view.findViewById(R.id.btn_compare);
        btn_compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DateUtils.isSameAsCurrentDate(calendar4.getText().toString())) {
                    Toast.makeText(getActivity(), "Same As Current Date", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Not Same", Toast.LENGTH_LONG).show();
                }
            }
        });

        final AppCompatTextView sDayOfWeek = view.findViewById(R.id.sDayOfWeek);
        final AppCompatTextView sMonth = view.findViewById(R.id.sMonth);
        final AppCompatTextView sDate = view.findViewById(R.id.sDate);
        final AppCompatTextView sYear = view.findViewById(R.id.sYear);

        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH) + 1;
        int cday = c.get(Calendar.DAY_OF_MONTH);

        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse("" + cday + "/" + cmonth + "/" + cyear);
        } catch (Exception e) {
        }

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String dayOfTheWeek = sdf.format(date1);
        sDayOfWeek.setText(dayOfTheWeek);

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
        String cMonth = monthFormat.format(date1);
        sMonth.setText(cMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        String cDay = dateFormat.format(date1);
        sDate.setText(cDay);

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String cYear = yearFormat.format(date1);
        sYear.setText(cYear);

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setMinDate(Calendar.getInstance().getTime().getTime());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                try {
                    month++;
                    date1 = new SimpleDateFormat("dd/MM/yyyy").parse("" + dayOfMonth + "/" + month + "/" + year);
                } catch (Exception e) {
                }

                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                String dayOfTheWeek = sdf.format(date1);
                sDayOfWeek.setText(dayOfTheWeek);

                SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
                String cMonth = monthFormat.format(date1);
                sMonth.setText(cMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
                String cDay = dateFormat.format(date1);
                sDate.setText(cDay);

                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                String cYear = yearFormat.format(date1);
                sYear.setText(cYear);
            }
        });

        //--
        spnDateFormat1 = (AppCompatSpinner) view.findViewById(R.id.spnDateFormat);
        txtDateFormat1 = (TextView) view.findViewById(R.id.txtDateFormat);
        txtDateFormat1.setText(sDate + "-" + sMonth + "-" + sYear);

        List<String> list = new ArrayList<String>();
        list.add("dd-MM-yyyy");
        list.add("yyyy-MM-dd");
        list.add("MM-dd-yyyy");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnDateFormat1.setAdapter(dataAdapter);

        spnDateFormat1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    SimpleDateFormat sdf = new SimpleDateFormat(selectedItem);

                    Calendar cal = Calendar.getInstance();
                    String today = sdf.format(cal.getTime());

                    txtDateFormat1.setText(today);

                } catch (Exception pe) {
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //--
        final EditText firstDate = (EditText) view.findViewById(R.id.firstDate);
        final EditText secondDate = (EditText) view.findViewById(R.id.secondDate);
        diffDate = (EditText) view.findViewById(R.id.diffDate);
        Button btn_calculate = (Button) view.findViewById(R.id.btn_calculate);
        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");

                try {
                    Date date1 = simpleDateFormat.parse(firstDate.getText().toString());
                    Date date2 = simpleDateFormat.parse(secondDate.getText().toString());

                    printDifference(date1, date2);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private String format = "";

    public StringBuilder showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        return new StringBuilder().append(hour).append(" : ").append(min).append(" ").append(format);

    }

    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long difference = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = difference / daysInMilli;
        difference = difference % daysInMilli;

        long elapsedHours = difference / hoursInMilli;
        difference = difference % hoursInMilli;

        long elapsedMinutes = difference / minutesInMilli;
        difference = difference % minutesInMilli;

        long elapsedSeconds = difference / secondsInMilli;

        System.out.printf("%d days, %d hours, %d minutes, %d seconds%n", elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        diffDate.setText(elapsedDays + " days, " + elapsedHours + " hours, " + elapsedMinutes + " minutes, " + elapsedSeconds + " seconds");
    }

    public void datePicker1(final AppCompatTextView textView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = addZeroInBeginingIfLessThanTen(dayOfMonth) + "/" + addZeroInBeginingIfLessThanTen(++month) + "/" + year;
                textView.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    //--
    public void datePicker2(final AppCompatTextView textView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = addZeroInBeginingIfLessThanTen(dayOfMonth) + "/" + addZeroInBeginingIfLessThanTen(++month) + "/" + year;
                textView.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        calendar.add(Calendar.MONTH, 1);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }

    //--
    public void datePicker3(final AppCompatTextView textView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = addZeroInBeginingIfLessThanTen(dayOfMonth) + "/" + addZeroInBeginingIfLessThanTen(++month) + "/" + year;
                textView.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());

        //calendar.add(Calendar.MONTH, -1);
        calendar.add(Calendar.DAY_OF_MONTH, -15);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        //-- 15 days back and 1 month ahead
        calendar.add(Calendar.DAY_OF_MONTH, 45);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }

    public String addZeroInBeginingIfLessThanTen(int value) {
        return value < 10 ? "0" + value : String.valueOf(value);
    }

}
