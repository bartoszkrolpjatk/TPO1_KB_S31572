package zad2.gui.event.dto;

import java.util.Locale;

public record CityAndCountryChangeEvent(String city,
                                        Locale country) {

    public static CityAndCountryChangeEvent mapToEventDto(Locale country, String city) {
        return new CityAndCountryChangeEvent(city, country);
    }
}
