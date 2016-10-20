package com.openworldtravels.www.hypertension;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by pitichampeethong on 10/20/2016 AD.
 */

public class UserAdapter extends BaseAdapter {

    //Explicit
    private Context context;
    private String[] nameStrings, iconStrings;
    private TextView textView;
    private ImageView imageView;

    public UserAdapter(Context context, String[] nameStrings, String[] iconStrings) {
        this.context = context;
        this.nameStrings = nameStrings;
        this.iconStrings = iconStrings;
    }

    @Override
    public int getCount() {
        return nameStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.user_listview, viewGroup, false);

        //Bind Widget;
        textView = (TextView) view1.findViewById(R.id.textView2);
        imageView = (ImageView) view1.findViewById(R.id.imageView2);

        //Show View
        textView.setText(nameStrings[i]);
        Picasso.with(context).load(iconStrings[i]).into(imageView);

        return view1;
    }
}// UserAdapter class
