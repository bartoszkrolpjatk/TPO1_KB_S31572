package zad2.gui.side;

import zad2.gui.side.rates.CustomRatePanel;
import zad2.gui.side.rates.NBPRatePanel;
import zad2.gui.side.weather.InputCityCountryPanel;
import zad2.gui.side.weather.WeatherDataPanel;

import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class SidePanel extends JPanel {

    public SidePanel(InputCityCountryPanel inputCityCountryPanel, WeatherDataPanel weatherDataPanel, NBPRatePanel NBPRatePanel, CustomRatePanel customRatePanel) {
        setLayout(new GridBagLayout());
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 0, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(inputCityCountryPanel, gbc);

        gbc.gridy = 1;
        add(weatherDataPanel, gbc);

        gbc.gridy = 2;
        add(NBPRatePanel, gbc);

        gbc.gridy = 3;
        add(customRatePanel, gbc);
    }
}
