package com.openworldtravels.www.hypertension;

import java.io.Serializable;

/**
 * Created by pitichampeethong on 10/22/2016 AD.
 */

public class HistoryTime implements Serializable {
    private String pTakeMedIdString, pMedIdString, dateTakenString, breakfastString, lunchString, dinnerString, patientIdString;

    public HistoryTime(String pTakeMedIdString, String pMedIdString, String dateTakenString, String breakfastString, String lunchString, String dinnerString, String patientIdString) {
        this.pTakeMedIdString = pTakeMedIdString;
        this.pMedIdString = pMedIdString;
        this.dateTakenString = dateTakenString;
        this.breakfastString = breakfastString;
        this.lunchString = lunchString;
        this.dinnerString = dinnerString;
        this.patientIdString = patientIdString;
    }

    public String getpTakeMedIdString() {
        return pTakeMedIdString;
    }

    public void setpTakeMedIdString(String pTakeMedIdString) {
        this.pTakeMedIdString = pTakeMedIdString;
    }

    public String getpMedIdString() {
        return pMedIdString;
    }

    public void setpMedIdString(String pMedIdString) {
        this.pMedIdString = pMedIdString;
    }

    public String getDateTakenString() {
        return dateTakenString;
    }

    public void setDateTakenString(String dateTakenString) {
        this.dateTakenString = dateTakenString;
    }

    public String getBreakfastString() {
        return breakfastString;
    }

    public void setBreakfastString(String breakfastString) {
        this.breakfastString = breakfastString;
    }

    public String getLunchString() {
        return lunchString;
    }

    public void setLunchString(String lunchString) {
        this.lunchString = lunchString;
    }

    public String getDinnerString() {
        return dinnerString;
    }

    public void setDinnerString(String dinnerString) {
        this.dinnerString = dinnerString;
    }

    public String getPatientIdString() {
        return patientIdString;
    }

    public void setPatientIdString(String patientIdString) {
        this.patientIdString = patientIdString;
    }
}
