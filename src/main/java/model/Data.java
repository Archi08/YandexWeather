package model;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("temp_min")
    public float temp_min;

    @SerializedName("temp_max")
    public float temp_max;

    @SerializedName("feels_like")
    public float feels_like;

    @SerializedName("wind_speed")
    public float wind_speed;

    @SerializedName("pressure_mm")
    public float pressure_mm;

    @SerializedName("prec_mm")
    public float prec_mm;

    @SerializedName("prec_period")
    public float prec_period;

    @SerializedName("prec_prob")
    public float prec_prob;

    @SerializedName("prec_type")
    public float prec_type;

    @SerializedName("prec_strength")
    public float prec_strength;

    @SerializedName("hour")
    public String hour;

}
