package zad2.http.dto;

public class GeographicalCoordinatesResponse {
    private double lat;
    private double lon;
    private String country;

    public GeographicalCoordinatesResponse() {}

    public double lat() {
        return lat;
    }

    public double lon() {
        return lon;
    }

    public String country() {
        return country;
    }

    @Override
    public String toString() {
        return "GeographicalCoordinatesResponse{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", country='" + country + '\'' +
                '}';
    }
}
