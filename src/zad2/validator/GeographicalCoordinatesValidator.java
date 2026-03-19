package zad2.validator;

import zad2.exception.AppException;
import zad2.http.dto.GeographicalCoordinatesResponse;

import java.util.List;
import java.util.Locale;

public class GeographicalCoordinatesValidator {

    public void validateGeographicalCoordinatesNotEmpty(List<GeographicalCoordinatesResponse> geographicalCoordinates, Locale country, String city) {
        if (geographicalCoordinates.isEmpty())
            throw new AppException.NotFound("Geographical coordinates for %s: %s not found".formatted(country.getDisplayCountry(), city));
    }
}
