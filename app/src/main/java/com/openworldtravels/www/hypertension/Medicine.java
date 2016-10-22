package com.openworldtravels.www.hypertension;

import java.io.Serializable;

/**
 * Created by pitichampeethong on 10/22/2016 AD.
 */

public class Medicine implements Serializable {
    private String idString, brandString, charactorString, dosString, numberString
            , descString, imageString, nameString, affectString;


    public Medicine(String idString, String brandString, String charactorString, String dosString, String numberString
            , String descString, String imageString, String nameString, String affectString) {
        this.idString = idString;
        this.brandString = brandString;
        this.charactorString = charactorString;
        this.dosString = dosString;
        this.numberString = numberString;
        this.descString = descString;
        this.imageString = imageString;
        this.nameString = nameString;
        this.affectString = affectString;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getBrandString() {
        return brandString;
    }

    public void setBrandString(String brandString) {
        this.brandString = brandString;
    }

    public String getCharactorString() {
        return charactorString;
    }

    public void setCharactorString(String charactorString) {
        this.charactorString = charactorString;
    }

    public String getDosString() {
        return dosString;
    }

    public void setDosString(String dosString) {
        this.dosString = dosString;
    }

    public String getNumberString() {
        return numberString;
    }

    public void setNumberString(String numberString) {
        this.numberString = numberString;
    }

    public String getDescString() {
        return descString;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public String getAffectString() {
        return affectString;
    }

    public void setAffectString(String affectString) {
        this.affectString = affectString;
    }
}
