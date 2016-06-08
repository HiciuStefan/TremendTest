package com.tremend.testtremend.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tremend.testtremend.Models.ModelBrowse;
import com.tremend.testtremend.R;

import java.util.ArrayList;

/**
 * Created by Hiciu on 6/8/2016.
 */
public class BrowserListAdapter extends ArrayAdapter<ModelBrowse> {

    private final Context context;
    private final int mTotalHeight;
    private int mLayout;
    private ArrayList<ModelBrowse> mItems;

    public BrowserListAdapter(Context context, int resource, ArrayList<ModelBrowse> objects, int height) {
        super(context, resource, objects);
        mLayout = resource;
        mItems = objects;
        this.context = context;
        mTotalHeight = height;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderBrowseModel viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolderBrowseModel();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mLayout, parent, false);
            viewHolder.holder = (LinearLayout) convertView.findViewById(R.id.item_holder);
            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.directory_name);
            viewHolder.textViewTotalSize = (TextView) convertView.findViewById(R.id.directory_total_size);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderBrowseModel) convertView.getTag();
        }

        ModelBrowse item = getItem(position);
        int height = item.layoutPercentage == 0 ? 10 : item.layoutPercentage * mTotalHeight / 100;
        viewHolder.holder.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        viewHolder.holder.setBackgroundColor(getColor(position%3));
        viewHolder.textViewName.setText(item.fileName);
        viewHolder.textViewTotalSize.setText(item.totalFileSize.toString() + "kb");


        return convertView;
    }

    private int getColor(int position) {
        int color = Color.WHITE;
        if (position == 1) {
            color = Color.LTGRAY;
        }
        if (position == 2) {
            color = Color.GREEN;
        }
        return color;
    }


    private class ViewHolderBrowseModel {
        LinearLayout holder;
        TextView textViewName;
        TextView textViewTotalSize;
    }
}


