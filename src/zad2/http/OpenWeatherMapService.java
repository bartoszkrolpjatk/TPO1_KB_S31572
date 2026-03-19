package zad2.http;

import zad2.http.client.OpenWeatherMapClient;
import zad2.http.dto.weather.WeatherResponse;
import zad2.validator.GeographicalCoordinatesValidator;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class OpenWeatherMapService {

    private final OpenWeatherMapClient openWeatherMapClient;
    private final GeographicalCoordinatesValidator geographicalCoordinatesValidator;

    public OpenWeatherMapService(OpenWeatherMapClient openWeatherMapClient, GeographicalCoordinatesValidator geographicalCoordinatesValidator) {
        this.openWeatherMapClient = openWeatherMapClient;
        this.geographicalCoordinatesValidator = geographicalCoordinatesValidator;
    }

    public CompletableFuture<WeatherResponse> getWeather(Locale country, String city) {
        return openWeatherMapClient.getGeographicalCoordinates(country, city)
                .thenCompose(coordinatesList -> {
                    geographicalCoordinatesValidator.validateGeographicalCoordinatesNotEmpty(coordinatesList, country, city);
                    var finalCoordinates = coordinatesList.stream()
                            .filter(gc -> gc.country().equals(country.getCountry()))
                            .findFirst()
                            .orElse(coordinatesList.getFirst());
                    return openWeatherMapClient.getWeather(finalCoordinates.lat(), finalCoordinates.lon());
                });
    }
}
