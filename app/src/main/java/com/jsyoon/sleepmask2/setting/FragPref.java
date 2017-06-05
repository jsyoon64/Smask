package com.jsyoon.sleepmask2.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.widget.Toast;

import com.jsyoon.sleepmask2.R;
import com.jsyoon.sleepmask2.target;

import java.lang.Object;

public class FragPref extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "FragPref";

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);

        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);

        Log.d(TAG, "onDestroy");
    }

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

        sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();

        int count = prefScreen.getPreferenceCount();

        // Go through all of the preferences, and set up their preference summary.
        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            if (p instanceof PreferenceCategory) {
                int count1 = ((PreferenceCategory) p).getPreferenceCount();
                for (int j = 0; j < count1; j++) {
                    Preference p1 = ((PreferenceCategory) p).getPreference(j);
                    findListPreferenceNSetSummary(sharedPreferences, p1);
                }
            } else {
                findListPreferenceNSetSummary(sharedPreferences, p);
            }
        }
        Log.d(TAG, "onCreatePreferences");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        findListPreferenceNSetSummary(sharedPreferences, preference);
    }

    private void findListPreferenceNSetSummary(SharedPreferences sharedPreferences, Preference preference) {
        if (null != preference) {
            // Updates the summary for the preference
            if ((preference instanceof ListPreference) ||
                    (preference instanceof EditTextPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            } else if (preference instanceof TimePreference) {
                int value = sharedPreferences.getInt(preference.getKey(), 0);
                setIntPreferenceSummary(preference, value);
            }
        }
    }

    private void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            // For list preferences, figure out the label of the selected value
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                // Set the summary to that label
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else if (preference instanceof EditTextPreference) {
            EditTextPreference edittextPreference = (EditTextPreference) preference;
            edittextPreference.setSummary(value);
        }
    }

    private void setIntPreferenceSummary(Preference preference, int value) {
        if ((preference instanceof TimePreference) && (value != 0)) {
            // For list preferences, figure out the label of the selected value
            TimePreference timePreference = (TimePreference) preference;
            int hour = value / 60;
            int min = value % 60;

            //String summary = Integer.toString(hour) + " : " +  Integer.toString(min);
            String summary = String.format("%02d : %02d", hour, min);
            timePreference.setSummary(summary);
        }
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {

        // Try if the preference is one of our custom Preferences
        android.support.v4.app.DialogFragment dialogFragment = null;
        if (preference instanceof TimePreference) {
            // Create a new instance of TimePreferenceDialogFragment with the key of the related
            // Preference
            dialogFragment = TimePreferenceDialogFragmentCompat.newInstance(preference.getKey());
        }

        if (dialogFragment != null) {
            // The dialog was created (it was one of our custom Preferences), show the dialog for it
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(this.getFragmentManager(), "android.support.v7.preference" +
                    ".PreferenceFragment.DIALOG");
        } else {
            // Dialog creation could not be handled here. Try with the super method.
            super.onDisplayPreferenceDialog(preference);
        }

    }
}
