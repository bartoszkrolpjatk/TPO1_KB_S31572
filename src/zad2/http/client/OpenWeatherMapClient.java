package zad2.http.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import zad2.http.dto.GeographicalCoordinatesResponse;
import zad2.http.dto.weather.WeatherResponse;
import zad2.http.provider.HttpClientProvider;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import static zad2.http.provider.HttpClientProvider.getWeatherApiKey;

public class OpenWeatherMapClient {

    private static final String GEOGRAPHICAL_COORDINATES_URL = "http://api.openweathermap.org/geo/1.0/direct?q=%s,%s&limit=1&appid=%s";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric";
    private final Gson gson;

    public OpenWeatherMapClient(Gson gson) {
        this.gson = gson;
    }

    public CompletableFuture<WeatherResponse> getWeather(Double latitude, Double longitude) {
        var client = HttpClientProvider.getHttpClient();
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(WEATHER_URL.formatted(latitude, longitude, getWeatherApiKey())))
                .build();

        return client.sendAsync(request, new JsonBodyHandler<>(gson, WeatherResponse.class))
                .thenApply(HttpResponse::body);
    }

    public CompletableFuture<String> getWeatherAsJson(GeographicalCoordinatesResponse coords) {
        var client = HttpClientProvider.getHttpClient();
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(WEATHER_URL.formatted(coords.lat(), coords.lon(), getWeatherApiKey())))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }

    public CompletableFuture<List<GeographicalCoordinatesResponse>> getGeographicalCoordinates(Locale country, String city) {
        var client = HttpClientProvider.getHttpClient();
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(GEOGRAPHICAL_COORDINATES_URL.formatted(URLEncoder.encode(city, StandardCharsets.UTF_8),
                        country.getCountry(), getWeatherApiKey())))
                .build();

        Type type = new TypeToken<List<GeographicalCoordinatesResponse>>() {}.getType();
        JsonBodyHandler<List<GeographicalCoordinatesResponse>> handler = new JsonBodyHandler<>(gson, type);
        return client.sendAsync(request, handler)
                .thenApply(HttpResponse::body);
    }
}
