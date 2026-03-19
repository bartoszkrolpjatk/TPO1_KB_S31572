package zad2.http.dto.weather;

import com.google.gson.annotations.SerializedName;

public class WeatherConditions {
    private double temp;
    @SerializedName("feels_like")
    private double feelsLike;
    @SerializedName("temp_min")
    private double tempMin;
    @SerializedName("temp_max")
    private double tempMax;
    private double pressure;
    private byte humidity;

    WeatherConditions() {}

    public double temp() {
        return temp;
    }

    public double feelsLike() {
        return feelsLike;
    }

    public double tempMin() {
        return tempMin;
    }

    public double tempMax() {
        return tempMax;
    }

    public double pressure() {
        return pressure;
    }

    public byte humidity() {
        return humidity;
    }

    @Override
    public String toString() {
        return "WeatherConditions{" +
                "temp=" + temp +
                ", feelsLike=" + feelsLike +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                '}';
    }
}
