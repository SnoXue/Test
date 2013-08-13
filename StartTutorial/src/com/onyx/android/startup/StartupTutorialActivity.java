package com.onyx.android.startup;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;

public abstract class StartupTutorialActivity extends Activity
{

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == StartTutorialApplicatin.EXIT_REQUEST) {
            setResult(StartTutorialApplicatin.EXIT_REQUEST);
            finish();
        }
    }

    protected abstract void next();

    protected abstract void prev();
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
//            prev();
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
//            next();
        }
        return true;
    }
}
