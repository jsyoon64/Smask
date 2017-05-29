package com.jsyoon.sleepmask2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class TabFragment3 extends Fragment {
    private static final String TAG = "TabFragment3";
    private static View view = null;

    public TabFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.tab_fragment3, container, false);
        }

        Log.d(TAG, "onCreateView");
        return view;
    }
}
