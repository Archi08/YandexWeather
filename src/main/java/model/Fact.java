package model;

import com.google.gson.annotations.SerializedName;

public class Fact {

    @SerializedName("temp")
    public int temp;

    @SerializedName("feels_like")
    public int feels_like;

    @SerializedName("temp_water")
    public int temp_water;

    @SerializedName("pressure_mm")
    public int pressure_mm;

    @SerializedName("uv_index")
    public int uv_index;

    @SerializedName("condition")
    public String condition;

    @SerializedName("season")
    public String season;
}
