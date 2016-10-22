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
 * Created by pitichampeethong on 10/22/2016 AD.
 */

public class MedicineAdapter extends BaseAdapter {

    private Context context;
    private TextView brandTextView, nameTextView;
    private ImageView imageView;
    private Medicine[] medicines;

    public MedicineAdapter(Context context, Medicine[] medicines) {
        this.context = context;
        this.medicines = medicines;
    }

    @Override
    public int getCount() {
        return medicines.length;
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
        View view1 = layoutInflater.inflate(R.layout.medicine_listview, viewGroup, false);

        //Bind Widget;
        TextView brandTextView = (TextView) view1.findViewById(R.id.brandTextView);
        TextView nameTextView = (TextView) view1.findViewById(R.id.nameTextView);
        ImageView imageView = (ImageView) view1.findViewById(R.id.imageView4);

        //Show View
        brandTextView.setText(medicines[i].getBrandString());
        nameTextView.setText(medicines[i].getNameString());
        Picasso.with(context).load(medicines[i].getImageString()).into(imageView);

        return view1;
    }
}
