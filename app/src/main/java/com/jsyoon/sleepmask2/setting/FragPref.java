package com.jsyoon.sleepmask2.setting;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import com.jsyoon.sleepmask2.R;

public class FragPref extends PreferenceFragmentCompat {
    private static final String TAG = "FragPref";
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.setting_pref);
        Log.d(TAG, "onCreatePreferences");
    }
}
