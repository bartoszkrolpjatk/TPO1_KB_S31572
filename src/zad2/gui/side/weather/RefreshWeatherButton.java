package zad2.gui.side.weather;

import zad2.LocaleFinder;
import zad2.exception.ErrorDialogProvider;
import zad2.gui.AppEventBus;
import zad2.gui.event.dto.CityAndCountryChangeEvent;
import zad2.gui.event.dto.WeatherChangeEvent;
import zad2.http.OpenWeatherMapService;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class RefreshWeatherButton extends JButton {

    public RefreshWeatherButton(JTextField cityTextField,
                                JTextField countryTextField,
                                OpenWeatherMapService openWeatherMapService) {
        super("Load data");
        this.addActionListener(e -> {
            var city = cityTextField.getText();
            var country = countryTextField.getText();
            var countryLocale = LocaleFinder.findLocaleByCountryName(country);

            openWeatherMapService.getWeather(countryLocale, city)
                    .thenApply(WeatherChangeEvent::mapToEventDto)
                    .thenAccept(weatherChangeEvent -> SwingUtilities.invokeLater(() -> AppEventBus.bus().post(weatherChangeEvent)))
                    .exceptionally(ErrorDialogProvider.errorFunction("Error while loading weather for: %s %s".formatted(countryLocale, city)));

            SwingUtilities.invokeLater(() -> AppEventBus.bus().post(CityAndCountryChangeEvent.mapToEventDto(countryLocale, city)));
        });
    }
}
