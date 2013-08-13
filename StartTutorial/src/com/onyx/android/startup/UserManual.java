package com.onyx.android.startup;

import java.util.Arrays;

import com.onyx.android.startsetting.R;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class UserManual extends StartupTutorialActivity implements OnTouchListener, OnClickListener
{
    private final static int USER_GUIDE_IMAGE_NUM = 32;

    private ImageView iv;

    private int mUserGuideImageReids[] = null;
    private int imageIndex = 0;
    private float downX;

    private Button btnStart;
    private Button btnSkip;
    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String guideImagePrefix;
        if (Build.MODEL.equalsIgnoreCase("Tagus_C65HD")) {
            guideImagePrefix = "c65hd_";
        } else if (Build.MODEL.equalsIgnoreCase("Tagus_C65ML")) {
            guideImagePrefix = "c65ml_h_";
        } else {
            guideImagePrefix = "c65hd_";
        }

        mUserGuideImageReids = getResids(guideImagePrefix);

        setContentView(R.layout.activity_user_manual);

        iv = (ImageView) findViewById(R.id.image_view);
        iv.setBackgroundResource(mUserGuideImageReids[0]);
        iv.setOnTouchListener(UserManual.this);

        btnSkip = (Button) findViewById(R.id.skip_guide);
        btnSkip.setOnClickListener(this);
        btnStart = (Button) findViewById(R.id.start_guide);
        btnStart.setOnClickListener(this);
    }

    private int[] getResids(final String prefix)
    {

        int resId[] = new int[USER_GUIDE_IMAGE_NUM];
        for (int i = 0; i < USER_GUIDE_IMAGE_NUM; i++) {
            String resPath;
            if (i < 9)
                resPath = prefix + "0" + (i + 1);
            else
                resPath = prefix + (i + 1);
            try {
                resId[i] = getResidFromString(resPath);
            } catch (Exception e) {
                return Arrays.copyOf(resId, i);
            }
        }
        return resId;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (firstTime)
            return true;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("SNO", "X = " + event.getX());
            Log.d("SNO", "Y = " + event.getY());
            downX = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            float upX = event.getX();
            if (upX - downX > 100) {
                prev();
            } else if (downX - upX > 100) {
                next();
            } else if (upX > 300 && downX > 300) {
                next();
            } else if (upX < 300 && downX < 300) {
                prev();
            }
        }
        return true;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (firstTime)
            return true;
        
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            next();
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            prev();
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (imageIndex == mUserGuideImageReids.length - 1) {
                setResult(StartTutorialApplicatin.EXIT_REQUEST);
                finish();
            }
        }
        return true;
        // return super.onKeyDown(keyCode, event);
    }

    protected void prev()
    {
        if (imageIndex > 0) {
            imageIndex--;
            iv.setBackgroundResource(mUserGuideImageReids[imageIndex]);
        }
    }

    protected void next()
    {
        if (imageIndex < mUserGuideImageReids.length - 1) {
            imageIndex++;
            iv.setBackgroundResource(mUserGuideImageReids[imageIndex]);
        }
        ((LinearLayout) btnSkip.getParent()).setVisibility(View.GONE);
        firstTime = false;
    }

    private int getResidFromString(String resString) throws Exception
    {

        int resId = 0;
        R.drawable drawable = new R.drawable();
        resId = (Integer) R.drawable.class.getDeclaredField(resString).get(drawable);

        return resId;
    }

    @Override
    public void onClick(View v)
    {
        if (v == btnStart) {
            next();
        } else if (v == btnSkip) {
            setResult(StartTutorialApplicatin.EXIT_REQUEST);
            finish();
        }

    }

}
