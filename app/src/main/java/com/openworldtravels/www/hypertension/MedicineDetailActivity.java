package com.openworldtravels.www.hypertension;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MedicineDetailActivity extends AppCompatActivity {

    private Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);

        medicine = (Medicine) getIntent().getSerializableExtra("data");

        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        TextView brandTextView = (TextView) findViewById(R.id.brandTextView);
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView characterTextView = (TextView) findViewById(R.id.charactorTextView);
        TextView affectTextView = (TextView) findViewById(R.id.affectTextView);

        //Log.d("22oct", medicine.toString());

        brandTextView.setText(medicine.getBrandString());
        nameTextView.setText(medicine.getNameString());
        characterTextView.setText(medicine.getCharactorString());
        affectTextView.setText(medicine.getAffectString());
        Picasso.with(MedicineDetailActivity.this).load(medicine.getImageString()).into(imageView);


    }
}
