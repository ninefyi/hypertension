package com.openworldtravels.www.hypertension;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by pitichampeethong on 10/22/2016 AD.
 */

public class PatientJSONConverter {
    private String jsonString, userString, passwordString, idString, nameString
            , bodString, weightString, heightString, allergyString
            , breakfastString, lunchString, dinnerString, phoneString;

    public PatientJSONConverter(String jsonString) {
        this.jsonString = jsonString;
        this.execute();
    }

    private String convertToTimePattern(String value){
        if(value.equals("")){
            return value;
        }else{
            return value.substring(0, 2) + ":" + value.substring(2, 4);
        }
    }

    public void execute(){
        try{
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                this.nameString = jsonObject.getString("patient_name");
                this.breakfastString = jsonObject.getString("patient_breakfast");
                this.lunchString = jsonObject.getString("patient_lunch");
                this.dinnerString = jsonObject.getString("patient_dinner");
                this.userString = jsonObject.getString("patient_user");
                this.passwordString = jsonObject.getString("patient_pwd");
                this.weightString = jsonObject.getString("patient_weight");
                this.heightString = jsonObject.getString("patient_height");
                this.allergyString = jsonObject.getString("patient_allergy");
                this.bodString = jsonObject.getString("patient_bod");
                this.phoneString = jsonObject.getString("patient_phone");
                this.idString = jsonObject.getString("patient_id");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getUserString() {
        return userString;
    }

    public void setUserString(String userString) {
        this.userString = userString;
    }

    public String getPasswordString() {
        return passwordString;
    }

    public void setPasswordString(String passwordString) {
        this.passwordString = passwordString;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getBodString() {
        return bodString;
    }

    public void setBodString(String bodString) {
        this.bodString = bodString;
    }

    public String getWeightString() {
        return weightString;
    }

    public void setWeightString(String weightString) {
        this.weightString = weightString;
    }

    public String getHeightString() {
        return heightString;
    }

    public void setHeightString(String heightString) {
        this.heightString = heightString;
    }

    public String getAllergyString() {
        return allergyString;
    }

    public void setAllergyString(String allergyString) {
        this.allergyString = allergyString;
    }

    public String getBreakfastString() {
        if(this.breakfastString != null){
            return convertToTimePattern(this.breakfastString);
        }else {
            return "";
        }
    }

    public void setBreakfastString(String breakfastString) {
        this.breakfastString = breakfastString;
    }

    public String getLunchString() {
        if(this.lunchString != null){
            return convertToTimePattern(this.lunchString);
        }else{
            return "";
        }
    }

    public void setLunchString(String lunchString) {
        this.lunchString = lunchString;
    }

    public String getDinnerString() {
        if(this.dinnerString != null) {
            return convertToTimePattern(this.dinnerString);
        }else{
            return "";
        }

    }

    public void setDinnerString(String dinnerString) {
        this.dinnerString = dinnerString;
    }

    public String getPhoneString() {
        return phoneString;
    }

    public void setPhoneString(String phoneString) {
        this.phoneString = phoneString;
    }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public String getRealBreakfastString(){
        return breakfastString;
    }

    public String getRealLunchString(){
        return lunchString;
    }

    public String getRealDinnerString(){
        return dinnerString;
    }
}
