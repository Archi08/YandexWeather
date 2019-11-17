package model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Info {

    @SerializedName("lat")
    public double lat;

    @SerializedName("lon")
    public double lon;

    @SerializedName("tzinfo")
    public Map<String, String> tzinfo;

    @SerializedName("def_pressure_mm")
    public double def_pressure_mm;

    @SerializedName("def_pressure_pa")
    public double def_pressure_pa;

    @SerializedName("_h")
    public boolean _h;

    @SerializedName("url")
    public String url;

}
