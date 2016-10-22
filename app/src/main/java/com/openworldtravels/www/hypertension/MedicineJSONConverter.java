package com.openworldtravels.www.hypertension;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by pitichampeethong on 10/22/2016 AD.
 */

public class MedicineJSONConverter {

    private String jsonString;
    private Medicine[] medicines;


    public MedicineJSONConverter(String jsonString) {
        this.jsonString = jsonString;
        this.execute();
    }

    public void execute(){
        try{
            MyConstant myConstant = new MyConstant();
            JSONArray jsonArray = new JSONArray(jsonString);
            medicines = new Medicine[jsonArray.length()];
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nameString = jsonObject.getString("med_name");
                String idString = jsonObject.getString("med_id");
                String brandString = jsonObject.getString("med_brand");
                String charactorString = jsonObject.getString("med_charactor");
                String dosString = jsonObject.getString("med_dos");
                String numberString = jsonObject.getString("med_number");
                String descString = jsonObject.getString("med_desc");
                String imageString = jsonObject.getString("med_image");
                String affectString = jsonObject.getString("med_affect");

                if (imageString.length() > 3) {
                    imageString = myConstant.getUrlUpload() + imageString;
                }

                medicines[i] = new Medicine(idString, brandString, charactorString
                        , dosString, numberString, descString
                        , imageString, nameString, affectString);


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Medicine[] getMedicines() {
        return medicines;
    }
}
