package zad2.gui.side.rates;

import zad2.exception.ErrorDialogProvider;
import zad2.gui.AppEventBus;
import zad2.gui.event.dto.CityAndCountryChangeEvent;
import zad2.http.client.ExchangeRateClient;
import zad2.http.dto.ExchangeRateResponse;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;

public class CustomRatePanel extends JPanel {

    private static final String DEFAULT_TEXT = "Choose rate and country";

    private final JComboBox<Currency> currencyComboBox;
    private final JLabel targetCurrencyLabel;
    private final ExchangeRateClient exchangeRateClient;

    private Currency currentlyBaseCurrency;

    public CustomRatePanel(ExchangeRateClient exchangeRateClient) {
        AppEventBus.bus().subscribe(CityAndCountryChangeEvent.class, this::onCountryChange);
        targetCurrencyLabel = new JLabel(DEFAULT_TEXT);
        this.exchangeRateClient = exchangeRateClient;
        currencyComboBox = new JComboBox<>();
        addCurrenciesToComboBox();
        currencyComboBox.addItemListener(e -> {
            if (currentlyBaseCurrency == null) return;
            Currency targetCurrency = (Currency) currencyComboBox.getSelectedItem();
            updateLabel(targetCurrency);
        });
        currencyComboBox.setSelectedItem(Currency.getInstance("USD"));

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(currencyComboBox, gbc);

        gbc.gridy = 1;
        add(targetCurrencyLabel, gbc);
    }

    void addCurrenciesToComboBox() {
        var availableCurrencies = new ArrayList<>(Currency.getAvailableCurrencies());
        availableCurrencies.sort(Comparator.comparing(Currency::getCurrencyCode));
        for (var c : availableCurrencies) {
            currencyComboBox.addItem(c);
        }
    }

    public void onCountryChange(CityAndCountryChangeEvent event) {
        Currency targetCurrency = (Currency) currencyComboBox.getSelectedItem();
        if (targetCurrency == null) return;

        currentlyBaseCurrency = Currency.getInstance(event.country());
        updateLabel(targetCurrency);
    }

    private void updateLabel(Currency targetCurrency) {
        exchangeRateClient.getExchangeRate(currentlyBaseCurrency, targetCurrency)
                .thenApply(ExchangeRateResponse::rate)
                .thenAccept(rate -> SwingUtilities.invokeLater(() -> targetCurrencyLabel.setText("1 %s = %s %s".formatted(currentlyBaseCurrency, rate, targetCurrency))))
                .exceptionally(ErrorDialogProvider.errorFunction("Error while loading custom rate %s to %s".formatted(currentlyBaseCurrency, targetCurrency)));
    }
}
