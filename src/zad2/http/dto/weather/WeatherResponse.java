package zad2.http.dto.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {

    @SerializedName("weather")
    private List<WeatherInfo> weatherInfos;
    @SerializedName("main")
    private WeatherConditions weatherConditions;
    @SerializedName("sys")
    private SunInfo sunInfo;
    private long timezone;

    WeatherResponse() {}

    public List<WeatherInfo> weatherInfos() {
        return weatherInfos;
    }

    public WeatherConditions weatherConditions() {
        return weatherConditions;
    }

    public SunInfo sunInfo() {
        return sunInfo;
    }

    public long timezone() {
        return timezone;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "weatherInfoList=" + weatherInfos +
                ", weatherConditions=" + weatherConditions +
                ", sunInfo=" + sunInfo +
                '}';
    }
}
