package com.example.android.quakereport;

import java.util.Date;

/**
 * Created by Alex on 27/02/2017.
 */

public class Earthquake {

    // constructor for and EartchQauke object
    public Earthquake(double magnitude, String location, long dateInUnixFormat, String DetailPage) {
        mMagnitude = magnitude;
        mLocation = location;
        mDate = dateInUnixFormat;
        mDetailPage=DetailPage;
    }

    // TODO Magnitude defined in Earthquake class as String. Shall be replaced by the proper data type
    private double mMagnitude;
    private String mLocation;
    private long mDate;
    private String mDetailPage;

    // return magnitude
    public double getMagnitude() {
        return mMagnitude;
    }
    
    // return URL wih the detaile page
    public String getDetailPage() {
        return mDetailPage;
    }
    // return Lcation
    public String getLocation() {
        return mLocation;
    }

    // return Time Stamp
    public long getDate() {
        return mDate;
    }

} // END class definition
