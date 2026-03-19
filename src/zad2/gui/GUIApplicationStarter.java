package zad2.gui;

import com.google.gson.Gson;
import zad2.gui.side.SidePanel;
import zad2.gui.side.rates.CustomRatePanel;
import zad2.gui.side.rates.NBPRatePanel;
import zad2.gui.side.weather.InputCityCountryPanel;
import zad2.gui.side.weather.RefreshWeatherButton;
import zad2.gui.side.weather.WeatherDataPanel;
import zad2.http.OpenWeatherMapService;
import zad2.http.client.ExchangeRateClient;
import zad2.http.client.NBPClient;
import zad2.http.client.OpenWeatherMapClient;
import zad2.validator.GeographicalCoordinatesValidator;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GUIApplicationStarter {

    public static void startGuiApplication() {
        SwingUtilities.invokeLater(() -> {
            //utils
            var gson = new Gson();

            //business logic
            var geographicalCoordinatesValidator = new GeographicalCoordinatesValidator();
            var openWeatherMapClient = new OpenWeatherMapClient(gson);
            var openWeatherMapService = new OpenWeatherMapService(openWeatherMapClient, geographicalCoordinatesValidator);
            var exchangeRateClient = new ExchangeRateClient(gson);
            var nbpClient = new NBPClient(gson);

            //gui
            var countryTextField = new JTextField();
            var cityTextField = new JTextField();
            var refreshWeatherButton = new RefreshWeatherButton(cityTextField, countryTextField, openWeatherMapService);
            var inputCityCountryPanel = new InputCityCountryPanel(refreshWeatherButton, countryTextField, cityTextField);
            var customRatePanel = new CustomRatePanel(exchangeRateClient);
            var weatherDataPanel = new WeatherDataPanel();
            var nbpRatePanel = new NBPRatePanel(nbpClient);
            var sidePanel = new SidePanel(inputCityCountryPanel, weatherDataPanel, nbpRatePanel, customRatePanel);
            var mainPanel = new MainPanel(sidePanel);
            new MainFrame(mainPanel);
        });
    }

    private GUIApplicationStarter() {}
}
