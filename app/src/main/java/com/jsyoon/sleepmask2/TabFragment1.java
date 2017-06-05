package com.jsyoon.sleepmask2;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class TabFragment1 extends Fragment {
    private static final String TAG = "TabFragment1";
    boolean isAlarmEnabled = false;
    Button btn_run, alabtn;
    private Spinner mode_spinner;
    SharedPreferences sharedPreferences;

    LineGraphSeries<DataPoint> series1, series2;

    public TabFragment1() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment1, container, false);

        mode_spinner = (Spinner) view.findViewById(R.id.modespinner);
        //mode_spinner.setSelection(0);

        alabtn = (Button) view.findViewById(R.id.btn_alaena);

        alabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAlarmEnabled = sharedPreferences.getBoolean("AlarmOnOff", true);
                if (isAlarmEnabled) {
                    alabtn.setText(R.string.text_alarm_off);
                    isAlarmEnabled = false;
                } else {
                    alabtn.setText(R.string.text_alarm_on);
                    isAlarmEnabled = true;
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("AlarmOnOff", isAlarmEnabled);
                editor.commit();
            }
        });

        btn_run = (Button) view.findViewById(R.id.btn_run);
        btn_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Toast.makeText(MyAndroidAppActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(mode_spinner.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
                */
            }
        });

        ToggleButton toggle = (ToggleButton) view.findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });

        testeegGraph(view);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        //저장 방식
        //int minutesAfterMidnight = (hours * 60) + minutes;
        int atime = sharedPreferences.getInt("timekey", 0);
        int hour = atime / 60;
        int min = atime % 60;
        String alatext = String.format("%02d : %02d", hour, min);

        Button alarmstatus = (Button) view.findViewById(R.id.btn_alarmstatus);
        alarmstatus.setText(alatext);

        isAlarmEnabled = sharedPreferences.getBoolean("AlarmOnOff", true);
        if (isAlarmEnabled) {
            alabtn.setText(R.string.text_alarm_on);
        } else {
            alabtn.setText(R.string.text_alarm_off);
        }
        Log.d(TAG, "onCreateView");
        return view;
    }

    private void testeegGraph(View view) {
        double w, y, x;
        x = -5.0;

        GraphView graph = (GraphView) view.findViewById(R.id.graph);

        series1 = new LineGraphSeries<>();
        series2 = new LineGraphSeries<>();
        for (int i = 0; i < 300; i++) {
            x = x + 0.1;
            y = Math.sin(x);
            w = Math.cos(x);
            series1.appendData(new DataPoint(x, y), false, 300);
            series2.appendData(new DataPoint(x, w), true, 300);
        }
        series1.setTitle("Sine Curve 1");
        series1.setColor(Color.GREEN);
        series1.setDrawDataPoints(true);
        series1.setDataPointsRadius(10);
        series1.setThickness(8);
        graph.addSeries(series1);

        series2.setTitle("Cos Curve 1");
        series2.setColor(Color.RED);
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(10);
        series2.setThickness(8);
        graph.addSeries(series2);

         /*
// set second scale
        graph.getSecondScale().addSeries(series2);
// the y bounds are always manual for second scale
        //graph.getSecondScale().setMinY(0);
        //graph.getSecondScale().setMaxY(100);
        series2.setColor(Color.RED);
        //graph.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.RED);
        */
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

