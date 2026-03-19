/**
 * @author Król Bartosz s31572
 */

package zad2;

import com.google.gson.Gson;
import zad2.http.client.ExchangeRateClient;
import zad2.http.client.NBPClient;
import zad2.http.client.OpenWeatherMapClient;
import zad2.http.dto.GeographicalCoordinatesResponse;
import zad2.validator.GeographicalCoordinatesValidator;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class Service {

    private final Locale country;
    private final OpenWeatherMapClient openWeatherMapClient;
    private final Gson gson;
    private final GeographicalCoordinatesValidator geographicalCoordinatesValidator;
    private final ExchangeRateClient exchangeRateClient;
    private final NBPClient nbpClient;

    public Service(String country) {
        this.country = LocaleFinder.findLocaleByCountryName(country);
        System.out.printf("Country identified: %s - %s\n", this.country.getCountry(), this.country.getDisplayCountry());
        this.gson = new Gson();
        this.openWeatherMapClient = new OpenWeatherMapClient(gson);
        this.geographicalCoordinatesValidator = new GeographicalCoordinatesValidator();
        this.exchangeRateClient = new ExchangeRateClient(gson);
        this.nbpClient = new NBPClient(gson);
    }

    public String getWeather(String city) {
        System.out.printf("Get weather for %s: %s requested!\n", country.getDisplayCountry(), city);
        List<GeographicalCoordinatesResponse> geographicalCoordinates = openWeatherMapClient.getGeographicalCoordinates(country, city).join();
        geographicalCoordinatesValidator.validateGeographicalCoordinatesNotEmpty(geographicalCoordinates, country, city);
        var finalCoordinates = geographicalCoordinates.stream()
                .filter(gc -> gc.country().equals(country.getCountry()))
                .findFirst()
                .orElse(geographicalCoordinates.getFirst());
        System.out.printf("Coordinates for %s: %s found! Latitude: %s, Longitude: %s\n", country.getDisplayCountry(), city, finalCoordinates.lat(), finalCoordinates.lon());
        String weatherAsJson = openWeatherMapClient.getWeatherAsJson(finalCoordinates).join();
        System.out.println(weatherAsJson);
        return weatherAsJson;
    }

    public Double getRateFor(String currencyString) {
        System.out.printf("Get rate for %s requested!\n", currencyString);
        Currency targetCurrency = Currency.getInstance(currencyString);
        Currency baseCurrency = Currency.getInstance(country);
        System.out.printf("Currencies identified! Requesting rate for baseCurrency: %s to targetCurrency: %s\n", baseCurrency.getDisplayName(), targetCurrency.getDisplayName());
        double rate = exchangeRateClient.getExchangeRate(baseCurrency, targetCurrency).join().rate();
        System.out.printf("Rate received! 1 %s = %f %s\n", baseCurrency.getCurrencyCode(), rate, targetCurrency.getCurrencyCode());
        return rate;
    }

    public Double getNBPRate() {
        System.out.println("Get NBP rate requested!");
        Currency currency = Currency.getInstance(country);
        System.out.printf("Base currency: %s\n", currency.getDisplayName());
        double rate = country.equals(Locale.forLanguageTag("pl-PL")) ? 1D : nbpClient.getNBPRate(currency).join().rates().getFirst().rate();;
        System.out.printf("NBP rate received! 1 %s = %f PLN\n", currency.getCurrencyCode(), rate);
        return rate;
    }
}
