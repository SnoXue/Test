package com.onyx.android.startup;

import java.util.List;

import com.onyx.android.startsetting.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public final class OnyxAdapter extends BaseAdapter
{
    Context mContext;
    List<String> mList = null;
    int mSelectedPosition = 0;
    int mPreSelectedPosition = -1;
    boolean mFirstTime = true;

    public OnyxAdapter(Context context, List<String> list)
    {
        mContext = context;
        mList = list;
    }

    public void setSelected(int position)
    {
        mPreSelectedPosition = mSelectedPosition;
        mSelectedPosition = position;
    }

    public int getSelectPosition()
    {
        return mSelectedPosition;
    }

    public void selectNextPosition()
    {
        if (mSelectedPosition < getCount() - 1)
            mPreSelectedPosition = mSelectedPosition++;
        else {
            mPreSelectedPosition = mSelectedPosition;
            mSelectedPosition = 0;
        }
    }

    public void selectPrevPosition()
    {
        if (mSelectedPosition > 0)
            mPreSelectedPosition = mSelectedPosition--;
        else {
            mPreSelectedPosition = mSelectedPosition;
            mSelectedPosition = getCount() - 1;
        }
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        ((TextView) convertView.findViewById(R.id.label)).setText(mList.get(position));
        LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.list_item_view);
        LinearLayout.LayoutParams lp = (LayoutParams) ll.getLayoutParams();

        if (position == mSelectedPosition) {
            ll.setSelected(true);
            convertView.findViewById(R.id.select_image).setSelected(true);
        } else {
            ll.setSelected(false);
            convertView.findViewById(R.id.select_image).setSelected(false);
        }

        return convertView;
    }
}