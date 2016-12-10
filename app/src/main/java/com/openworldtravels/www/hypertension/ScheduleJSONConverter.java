package com.openworldtravels.www.hypertension;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by pitichampeethong on 11/19/2016 AD.
 */

public class ScheduleJSONConverter {

    private String jsonString;
    private Schedule[] schedules;

    public ScheduleJSONConverter(String jsonString) {
        this.jsonString = jsonString;
    }

    public void execute() {
        try {
            MyConstant myConstant = new MyConstant();
            JSONArray jsonArray = new JSONArray(jsonString);
            schedules = new Schedule[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String medBrandString = jsonObject.getString("med_brand");
                String pMedIdString = jsonObject.getString("p_med_id");
                String patientIdString = jsonObject.getString("patient_id");
                String medIdString = jsonObject.getString("med_id");
                String dosageString = jsonObject.getString("dosage");
                String totalString = jsonObject.getString("total");
                String startDateString = jsonObject.getString("start_date");
                String endDateString = jsonObject.getString("end_date");
                String snoozeString = jsonObject.getString("snooze");
                String breakfastString = jsonObject.getString("breakfast");
                String lunchString = jsonObject.getString("lunch");
                String dinnerString = jsonObject.getString("dinner");
                String historyString = jsonObject.getString("history");
                schedules[i] = new Schedule(medBrandString, pMedIdString, patientIdString, medIdString, dosageString, totalString, startDateString, endDateString, snoozeString, breakfastString, lunchString, dinnerString, historyString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Schedule[] getSchedules() {
        return schedules;
    }

}
