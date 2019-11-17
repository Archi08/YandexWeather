package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Weather {

    @SerializedName("now")
    public long now;

    @SerializedName("now_dt")
    public String now_dt;

    @SerializedName("info")
    public Info info;

    @SerializedName("fact")
    public Fact fact;

    @SerializedName("forecasts")
    public ArrayList<Day> forecasts;


}
