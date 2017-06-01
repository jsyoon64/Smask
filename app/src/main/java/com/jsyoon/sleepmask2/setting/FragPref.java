package com.jsyoon.sleepmask2.setting;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jsyoon.sleepmask2.R;

import java.util.Calendar;
import java.lang.Object;

public class FragPref extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        Preference.OnPreferenceChangeListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "FragPref";

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

        Preference btnDateFilter = (Preference) findPreference("btnDateFilter");
        btnDateFilter.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showDateDialog();
                return false;
            }
        });

        Preference btnTimeFilter = (Preference) findPreference("btnTimeFilter");
        btnTimeFilter.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showTimeDialog();
                return false;
            }
        });

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
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

        // Add the OnPreferenceChangeListener specifically to the EditTextPreference
        Preference preference = findPreference(getString(R.string.text_size_key));
        if (null != preference)
            preference.setOnPreferenceChangeListener(this);

        Log.d(TAG, "onCreatePreferences");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        findListPreferenceNSetSummary(sharedPreferences, preference);
    }

    // is triggered before a value is saved to the SharedPreferences file.
    // it can prevent an invalid update to a preference.
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Toast error = Toast.makeText(getContext(), "Please select a number between 1 and 45", Toast.LENGTH_SHORT);
        // if EditTextPreference key
        if (preference.getKey().equals(getString(R.string.text_size_key))) {
            String stringSize = (String) newValue;
            try {
                float size = Float.parseFloat(stringSize);
                // If the number is outside of the acceptable range, show an error.
                if (size > 45 || size <= 0) {
                    error.show();
                    return false;
                }
            } catch (NumberFormatException nfe) {
                // If whatever the user entered can't be parsed to a number, show an error
                error.show();
                return false;
            }
        }
        return true;
    }

    private void findListPreferenceNSetSummary(SharedPreferences sharedPreferences, Preference preference) {
        if (null != preference) {
            // Updates the summary for the preference
            if ((preference instanceof ListPreference) ||
                    (preference instanceof EditTextPreference) ) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
            else if ( preference.getKey().equals("btnDateFilter") || preference.getKey().equals("btnTimeFilter"))                  {
                //long ii = sharedPreferences.getLong(preference.getKey(), 0);
                //Log.d(TAG, "Preferences : " + ii);
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

    private void showDateDialog() {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(getContext(), this, year, month, day).show();
    }

    private void showTimeDialog() {
        final Calendar c = Calendar.getInstance();
        int hourOfDay = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        new TimePickerDialog(getContext(), this, hourOfDay, minute, true).show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.i(TAG, "Selected Date: year " + year + " month " + month + " day " + dayOfMonth);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.i(TAG, "Selected Time: Hour " + hourOfDay + " minute " + minute);
    }
}
