package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class Day {

    @SerializedName("date")
    public String date;

    @SerializedName("sunrise")
    public String sunrise;

    @SerializedName("sunset")
    public String sunset;

    @SerializedName("parts")
    public HashMap<String, Data> parts;

    @SerializedName("hours")
    public ArrayList<Data> hours;



}
