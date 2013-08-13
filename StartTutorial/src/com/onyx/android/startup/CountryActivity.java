package com.onyx.android.startup;

import java.util.ArrayList;
import java.util.List;

import com.onyx.android.startsetting.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CountryActivity extends StartupTutorialActivity implements OnItemClickListener
{

    private List<String> countryNames = new ArrayList<String>();
    private ListView countryListView;
    private Intent mIntent;
    private OnyxAdapter mOnyxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_country);
        countryNames = StartTutorialApplicatin.getCountryName();
        countryListView = (ListView) findViewById(R.id.listview_country);
        countryListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        countryListView.setBackgroundColor(Color.BLACK);
        countryListView.setSelection(0);
        mOnyxAdapter = new OnyxAdapter(CountryActivity.this, countryNames);
        countryListView.setAdapter(mOnyxAdapter);
        countryListView.setOnItemClickListener(this);
        mIntent = new Intent(CountryActivity.this, LanguageActivity.class);
        mIntent.putExtra("country", countryNames.get(0));
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        mOnyxAdapter.setSelected(arg2);
        mIntent.putExtra("country", countryNames.get(arg2));
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run()
            {
                startActivityForResult(mIntent, 0);
            }
        }, 500);

    }

    @Override
    protected void next()
    {
        mOnyxAdapter.selectNextPosition();
        countryListView.setSelection(mOnyxAdapter.getSelectPosition());
        mOnyxAdapter.notifyDataSetChanged();
    }

    @Override
    protected void prev()
    {
        mOnyxAdapter.selectPrevPosition();
        countryListView.setSelection(mOnyxAdapter.getSelectPosition());
        mOnyxAdapter.notifyDataSetChanged();
    }
}
