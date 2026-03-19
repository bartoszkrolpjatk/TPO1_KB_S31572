package zad2.http.client;

import com.google.gson.Gson;
import zad2.http.dto.ExchangeRateResponse;
import zad2.http.provider.HttpClientProvider;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Currency;
import java.util.concurrent.CompletableFuture;

import static zad2.http.provider.HttpClientProvider.getRatesApiKey;

public class ExchangeRateClient {

    private static final String EXCHANGE_RATE_URL = "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s";
    private final Gson gson;

    public ExchangeRateClient(Gson gson) {
        this.gson = gson;
    }

    public CompletableFuture<ExchangeRateResponse> getExchangeRate(Currency base, Currency target) {
        var client = HttpClientProvider.getHttpClient();
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(EXCHANGE_RATE_URL.formatted(getRatesApiKey(), base, target)))
                .build();

        return client.sendAsync(request, new JsonBodyHandler<>(gson, ExchangeRateResponse.class))
                .thenApply(HttpResponse::body);
    }
}
