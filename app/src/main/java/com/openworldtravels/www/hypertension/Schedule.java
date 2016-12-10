package com.openworldtravels.www.hypertension;

import java.io.Serializable;

/**
 * Created by pitichampeethong on 10/22/2016 AD.
 */

public class Schedule implements Serializable {
    private String medBrandString, pMedIdString, patientIdString, medIdString, dosageString, totalString, startDateString, endDateString, snoozeString, breakfastString, lunchString, dinnerString, historyString;

    public Schedule(String medBrandString, String pMedIdString, String patientIdString, String medIdString, String dosageString, String totalString, String startDateString, String endDateString, String snoozeString, String breakfastString, String lunchString, String dinnerString, String historyString) {
        this.medBrandString = medBrandString;
        this.pMedIdString = pMedIdString;
        this.patientIdString = patientIdString;
        this.medIdString = medIdString;
        this.dosageString = dosageString;
        this.totalString = totalString;
        this.startDateString = startDateString;
        this.endDateString = endDateString;
        this.snoozeString = snoozeString;
        this.breakfastString = breakfastString;
        this.lunchString = lunchString;
        this.dinnerString = dinnerString;
        this.historyString = historyString;
    }

    public String getMedBrandString() {
        return medBrandString;
    }

    public void setMedBrandString(String medBrandString) {
        this.medBrandString = medBrandString;
    }

    public String getpMedIdString() {
        return pMedIdString;
    }

    public void setpMedIdString(String pMedIdString) {
        this.pMedIdString = pMedIdString;
    }

    public String getPatientIdString() {
        return patientIdString;
    }

    public void setPatientIdString(String patientIdString) {
        this.patientIdString = patientIdString;
    }

    public String getMedIdString() {
        return medIdString;
    }

    public void setMedIdString(String medIdString) {
        this.medIdString = medIdString;
    }

    public String getDosageString() {
        return dosageString;
    }

    public void setDosageString(String dosageString) {
        this.dosageString = dosageString;
    }

    public String getTotalString() {
        return totalString;
    }

    public void setTotalString(String totalString) {
        this.totalString = totalString;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public String getSnoozeString() {
        return snoozeString;
    }

    public void setSnoozeString(String snoozeString) {
        this.snoozeString = snoozeString;
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

    public String getHistoryString() {
        return historyString;
    }

    public void setHistoryString(String historyString) {
        this.historyString = historyString;
    }
}
