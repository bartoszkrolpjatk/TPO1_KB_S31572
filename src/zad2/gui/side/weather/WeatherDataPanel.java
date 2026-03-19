package zad2.gui.side.weather;

import zad2.gui.AppEventBus;
import zad2.gui.event.dto.WeatherChangeEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class WeatherDataPanel extends JPanel {

    private static final String DEFAULT_TEXT = "Choose city";

    private final JLabel mainLabel;
    private final JLabel descLabel;
    private final JLabel tempLabel;
    private final JLabel feelsLikeLabel;
    private final JLabel minTempLabel;
    private final JLabel maxTempLabel;
    private final JLabel pressureLabel;
    private final JLabel humidityLabel;
    private final JLabel sunriseLabel;
    private final JLabel sunsetLabel;

    public WeatherDataPanel() {
        AppEventBus.bus().subscribe(WeatherChangeEvent.class, this::onWeatherChange);

        mainLabel = new JLabel(DEFAULT_TEXT);
        descLabel = new JLabel(DEFAULT_TEXT);
        tempLabel = new JLabel(DEFAULT_TEXT);
        feelsLikeLabel = new JLabel(DEFAULT_TEXT);
        minTempLabel = new JLabel(DEFAULT_TEXT);
        maxTempLabel = new JLabel(DEFAULT_TEXT);
        pressureLabel = new JLabel(DEFAULT_TEXT);
        humidityLabel = new JLabel(DEFAULT_TEXT);
        sunriseLabel = new JLabel(DEFAULT_TEXT);
        sunsetLabel = new JLabel(DEFAULT_TEXT);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        var gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 0, 5);
        gbc.weightx = 0;

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Temperature"), gbc);
        gbc.gridy++;
        add(new JLabel("Feels like"), gbc);
        gbc.gridy++;
        add(new JLabel("Minimum temperature"), gbc);
        gbc.gridy++;
        add(new JLabel("Maximum temperature"), gbc);
        gbc.gridy++;
        add(new JLabel("Pressure"), gbc);
        gbc.gridy++;
        add(new JLabel("Humidity"), gbc);
        gbc.gridy++;
        add(new JLabel("Sunrise"), gbc);
        gbc.gridy++;
        add(new JLabel("Sunset"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(mainLabel, gbc);

        gbc.gridy = 1;
        add(descLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(tempLabel, gbc);
        gbc.gridy++;
        add(feelsLikeLabel, gbc);
        gbc.gridy++;
        add(minTempLabel, gbc);
        gbc.gridy++;
        add(maxTempLabel, gbc);
        gbc.gridy++;
        add(pressureLabel, gbc);
        gbc.gridy++;
        add(humidityLabel, gbc);
        gbc.gridy++;
        add(sunriseLabel, gbc);
        gbc.gridy++;
        add(sunsetLabel, gbc);
    }

    public void onWeatherChange(WeatherChangeEvent event) {
        mainLabel.setText(event.main());
        descLabel.setText(event.description());
        tempLabel.setText(String.valueOf(event.temp()));
        feelsLikeLabel.setText(String.valueOf(event.feelsLikeTemp()));
        minTempLabel.setText(String.valueOf(event.tempMin()));
        maxTempLabel.setText(String.valueOf(event.tempMax()));
        pressureLabel.setText(String.valueOf(event.pressure()));
        humidityLabel.setText(String.valueOf(event.humidity()));
        sunriseLabel.setText(String.valueOf(event.sunrise()));
        sunsetLabel.setText(String.valueOf(event.sunset()));
    }

}
