package zad2.http.dto.weather;

public class SunInfo {
    private long sunrise;
    private long sunset;

    SunInfo() {}

    public long sunrise() {
        return sunrise;
    }

    public long sunset() {
        return sunset;
    }

    @Override
    public String toString() {
        return "SunInfo{" +
                "sunrise=" + sunrise +
                ", sunset=" + sunset +
                '}';
    }
}
