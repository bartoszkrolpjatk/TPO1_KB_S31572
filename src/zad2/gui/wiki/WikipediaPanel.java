package zad2.gui.wiki;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import zad2.gui.AppEventBus;
import zad2.gui.event.dto.CityAndCountryChangeEvent;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WikipediaPanel extends JFXPanel {

    private static final String WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/%s";
    private WebView webView;

    public WikipediaPanel() {
        setLayout(new FlowLayout());
        AppEventBus.bus().subscribe(CityAndCountryChangeEvent.class, this::onCityChange);
        Platform.runLater(() -> {
            webView = new WebView();
            Scene scene = new Scene(webView);
            this.setScene(scene);
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1400, 1000);
    }

    void onCityChange(CityAndCountryChangeEvent event) {
        Platform.runLater(() -> {
            String url = WIKIPEDIA_URL.formatted(
                    URLEncoder.encode(event.city(), StandardCharsets.UTF_8).replace("+", "_")
            );
            System.out.println(url);
            webView.getEngine().load(url);
        });
    }
}
