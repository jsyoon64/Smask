package com.jsyoon.sleepmask2;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    Button btn_run;
    private Spinner mode_spinner;
    LineGraphSeries<DataPoint> series1,series2;

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
        View view =  inflater.inflate(R.layout.tab_fragment1, container, false);

        mode_spinner = (Spinner) view.findViewById(R.id.modespinner);
        //mode_spinner.setSelection(0);

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
            series1.appendData(new DataPoint(x,y),false,300);
            series2.appendData(new DataPoint(x,w),true,300);
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

