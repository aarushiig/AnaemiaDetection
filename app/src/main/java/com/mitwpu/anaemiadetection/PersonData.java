package com.mitwpu.anaemiadetection;

import java.io.Serializable;

public class PersonData implements Serializable {

    public   String name;
    public   String age;
    public   String sex;
    public   String mobileNo;
    public   String symptoms;
    public   String result;
    public   String percentage;
    public   String email;
    public   StringBuffer leftEyeImagePath, rightEyeImagePath;

    PersonData(){
        this.result="Not Set";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public void setLeftEyeImagePath(StringBuffer leftEyeImage) {
        this.leftEyeImagePath = leftEyeImage;
    }

    public void setRightEyeImagePath(StringBuffer rightEyeImage) {
        this.rightEyeImagePath = rightEyeImage;
    }
}
