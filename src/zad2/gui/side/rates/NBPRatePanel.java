package zad2.gui.side.rates;

import zad2.exception.ErrorDialogProvider;
import zad2.gui.AppEventBus;
import zad2.gui.event.dto.CityAndCountryChangeEvent;
import zad2.http.client.NBPClient;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Currency;
import java.util.Locale;

public class NBPRatePanel extends JPanel {

    private static final String DEFAULT_TEXT = "Choose country";

    private final NBPClient nbpClient;
    private final JLabel currencyLabel;
    private final JLabel rateLabel;

    public NBPRatePanel(NBPClient nbpClient) {
        this.nbpClient = nbpClient;
        currencyLabel = new JLabel(DEFAULT_TEXT);
        rateLabel = new JLabel(DEFAULT_TEXT);
        AppEventBus.bus().subscribe(CityAndCountryChangeEvent.class, this::onCountryChange);

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new GridBagLayout());
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("PLN"), gbc);

        gbc.gridy = 1;
        add(rateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(currencyLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(new JLabel("1"), gbc);
    }

    public void onCountryChange(CityAndCountryChangeEvent event) {
        if (event.country().equals(Locale.forLanguageTag("pl-PL"))) {
            currencyLabel.setText("PLN");
            rateLabel.setText("1");
            return;
        }
        Currency target = Currency.getInstance(event.country());
        nbpClient.getNBPRate(target)
                .thenApply(nbpRatesResponse -> nbpRatesResponse.rates().getFirst().rate())
                .thenAccept(rate -> SwingUtilities.invokeLater(() -> {
                    currencyLabel.setText(target.toString());
                    rateLabel.setText(rate.toString());
                }))
                .exceptionally(ErrorDialogProvider.errorFunction("Error while loading NBP rate for: %s".formatted(target)));
    }
}
