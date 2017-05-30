package com.jsyoon.sleepmask2.setting;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import com.jsyoon.sleepmask2.R;

public class FragPref extends PreferenceFragmentCompat {
    private static final String TAG = "FragPref";
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.setting_pref);

        Preference button = findPreference("VersionInfo");
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //code for what you want it to do
                preference.setSummary("Button Clicked");
                return true;
            }
        });

        Log.d(TAG, "onCreatePreferences");
    }
}
