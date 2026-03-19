package zad2;

import zad2.exception.AppException;

import java.util.Arrays;
import java.util.Locale;

public class LocaleFinder {

    public static Locale findLocaleByCountryName(String country) {
        var locales = Locale.getAvailableLocales();
        return Arrays.stream(locales)
                .filter(locale -> Arrays.stream(locales)
                        .anyMatch(countryLocale -> locale.getDisplayCountry(countryLocale).equalsIgnoreCase(country)))
                .findAny()
                .orElseThrow(() -> new AppException.NotFound("Country not found for: %s".formatted(country)));
    }
}
