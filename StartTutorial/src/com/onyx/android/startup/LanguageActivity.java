package com.onyx.android.startup;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.onyx.android.startsetting.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LanguageActivity extends StartupTutorialActivity implements OnClickListener, OnItemClickListener
{
    private OnyxAdapter mAdapter;
    private List<Language> languageList = new ArrayList<Language>();
    private List<String> languageLabels = new ArrayList<String>();
    private List<Locale> locales = new ArrayList<Locale>();
    private ListView mListView;
    private Button mPrev;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        String country = intent.getStringExtra("country");
        if (country == null) {
            finish();
            return;
        }

        setContentView(R.layout.activity_select_language);
        mListView = (ListView) findViewById(R.id.language_listview);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mAdapter = new OnyxAdapter(LanguageActivity.this, languageLabels);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        for (int i = 0; i < StartTutorialApplicatin.mCountryList.size(); i++) {
            if (country.equalsIgnoreCase(StartTutorialApplicatin.mCountryList.get(i).name)) {
                List<Country> countryList = StartTutorialApplicatin.mCountryList;
                languageList = StartTutorialApplicatin.mCountryList.get(i).languages;
                break;
            }
        }

        Locale curLocale = getResources().getConfiguration().locale;
        for (int j = 0; j < languageList.size(); j++) {
            languageLabels.add(languageList.get(j).name);
            locales.add(languageList.get(j).locale);
            if (languageList.get(j).locale.getCountry().equalsIgnoreCase(curLocale.getCountry())
                    && languageList.get(j).locale.getLanguage().equalsIgnoreCase(curLocale.getLanguage())) {
                mAdapter.setSelected(j);
            }
        }

        mPrev = (Button) findViewById(R.id.prev_step);
        mPrev.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == mPrev) {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        Log.d("SNO", "onItemClick " + " arg2 = " + arg2 + ", arg3 = " + arg3);
        mAdapter.setSelected(arg2);
        changeSystemLanguage(arg2);

        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        intent.putExtra("startupTutorial", true);
        intent.putExtra("skip", "com.onyx.android.startsetting.WIFI_SETTING_SKIP");
        startActivityForResult(intent, 0);
    }

    private void changeSystemLanguage(int position)
    {
        try {
            Object objIActMag;
            Class clzIActMag = Class.forName("android.app.IActivityManager");
            Class clzActMagNative = Class.forName("android.app.ActivityManagerNative");
            Method mtdActMagNative$getDefault = clzActMagNative.getDeclaredMethod("getDefault");
            objIActMag = mtdActMagNative$getDefault.invoke(clzActMagNative);
            Method mtdIActMag$getConfiguration = clzIActMag.getDeclaredMethod("getConfiguration");
            Configuration config = (Configuration) mtdIActMag$getConfiguration.invoke(objIActMag);
            config.locale = locales.get(position);
            // config.locale = Locale.CHINA;
            Class[] clzParams = { Configuration.class };
            Method mtdIActMag$updateConfiguration = clzIActMag.getDeclaredMethod("updateConfiguration", clzParams);
            mtdIActMag$updateConfiguration.invoke(objIActMag, config);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        // Intent intent = new Intent();
        // intent.setClass(this, LanguageActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // this.startActivity(intent);
    }

    @Override
    protected void next()
    {
        mAdapter.selectNextPosition();
        mListView.setSelection(mAdapter.getSelectPosition());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void prev()
    {
        mAdapter.selectPrevPosition();
        mListView.setSelection(mAdapter.getSelectPosition());
        mAdapter.notifyDataSetChanged();
    }
}
