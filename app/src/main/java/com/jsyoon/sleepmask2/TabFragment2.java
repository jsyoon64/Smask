package com.jsyoon.sleepmask2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class TabFragment2 extends Fragment {
    private static final String TAG = "TabFragment2";
    CalendarView calendarView;
    View frag2view;

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    TextView ctextView, stextView;

    public TabFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frag2view = inflater.inflate(R.layout.tab_fragment2, container, false);

        ctextView = (TextView) frag2view.findViewById(R.id.currentDateText);
        stextView = (TextView) frag2view.findViewById(R.id.selectedDateText);

        calendarView = (CalendarView) frag2view.findViewById(R.id.calendarView2);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {

                //SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //String date = format.format(Date.parse("Your date string"));
                String stext = "Selected :" + month + " " + dayOfMonth + " , " + year;
                stextView.setText(stext);

                /*
                Toast.makeText(frag2view.getContext(), "Selected Date:\n"
                                + "Day = " + dayOfMonth + "\n"
                                + "Month = " + month + "\n"
                                + "Year = " + year,
                        Toast.LENGTH_SHORT).show();
                */
            }
        });
        String ctext = "Current :" + FORMATTER.format(calendarView.getDate());
        ctextView.setText(ctext);

        Log.d(TAG, "onCreateView");
        return frag2view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }
}
