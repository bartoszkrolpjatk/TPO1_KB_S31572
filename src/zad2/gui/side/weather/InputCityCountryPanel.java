package zad2.gui.side.weather;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class InputCityCountryPanel extends JPanel {

    public InputCityCountryPanel(RefreshWeatherButton refreshWeatherButton, JTextField countryTextField, JTextField cityTextField) {
        countryTextField.setColumns(10);
        cityTextField.setColumns(10);

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 0, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Country"), gbc);

        gbc.gridx = 1;
        add(countryTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("City"), gbc);

        gbc.gridx = 1;
        add(cityTextField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 5, 5);
        add(refreshWeatherButton, gbc);
    }
}
