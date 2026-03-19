package zad2.gui.event.dto;

import zad2.http.dto.weather.WeatherResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record WeatherChangeEvent(String main,
                                 String description,
                                 double temp,
                                 double feelsLikeTemp,
                                 double tempMin,
                                 double tempMax,
                                 double pressure,
                                 double humidity,
                                 LocalDateTime sunrise,
                                 LocalDateTime sunset) {
    public static WeatherChangeEvent mapToEventDto(WeatherResponse response) {
        var weatherInfo = response.weatherInfos().getFirst();
        var weatherConditions = response.weatherConditions();
        var sunInfo = response.sunInfo();
        return new WeatherChangeEvent(
                weatherInfo.main(),
                weatherInfo.description(),
                weatherConditions.temp(),
                weatherConditions.feelsLike(),
                weatherConditions.tempMin(),
                weatherConditions.tempMax(),
                weatherConditions.pressure(),
                weatherConditions.humidity(),
                mapFromEpochSeconds(sunInfo.sunrise(), response.timezone()),
                mapFromEpochSeconds(sunInfo.sunset(), response.timezone())
        );
    }

    private static LocalDateTime mapFromEpochSeconds(long epoch, long timezoneOffset) {
        Instant instant = Instant.ofEpochSecond(epoch);
        ZoneOffset offset = ZoneOffset.ofTotalSeconds((int) timezoneOffset);
        return LocalDateTime.ofInstant(instant, offset);
    }
}
