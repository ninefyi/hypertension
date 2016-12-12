package com.openworldtravels.www.hypertension;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by pitichampeethong on 10/22/2016 AD.
 */

public class HistoryJSONConverter {

    private String jsonString;
    private HistoryTime[] historyTimes;


    public HistoryJSONConverter(String jsonString) {
        this.jsonString = jsonString;
        this.execute();
    }

    public void execute() {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            historyTimes = new HistoryTime[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String pTakeMedIdString = jsonObject.getString("p_take_med_id");
                String pMedIdString = jsonObject.getString("p_med_id");
                String dateTakenString = jsonObject.getString("date_taken");

                String breakfastString = "";
                if(!jsonObject.isNull("breakfast")){
                    breakfastString = jsonObject.getString("breakfast");
                }
                String lunchString = "";
                if(!jsonObject.isNull("lunch")){
                    lunchString = jsonObject.getString("lunch");
                }
                String dinnerString = "";
                if(!jsonObject.isNull("dinner")){
                    dinnerString = jsonObject.getString("dinner");
                }

                String patientIdString = jsonObject.getString("patient_id");

                historyTimes[i] = new HistoryTime(pTakeMedIdString, pMedIdString, dateTakenString, breakfastString, lunchString, dinnerString, patientIdString);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HistoryTime[] getHistoryTimes() {
        return historyTimes;
    }
}
