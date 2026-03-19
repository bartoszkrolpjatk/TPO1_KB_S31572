package zad2.http.dto.weather;

public class WeatherInfo {
    private String main;
    private String description;

    WeatherInfo() {}

    public String main() {
        return main;
    }

    public String description() {
        return description;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "main='" + main + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
