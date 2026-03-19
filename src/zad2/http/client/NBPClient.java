package zad2.http.client;

import com.google.gson.Gson;
import zad2.http.dto.nbp.NBPRatesResponse;
import zad2.http.provider.HttpClientProvider;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Currency;
import java.util.concurrent.CompletableFuture;

public class NBPClient {

    private static final String NBP_RATE_URL = "https://api.nbp.pl/api/exchangerates/rates/a/%s";
    private final Gson gson;

    public NBPClient(Gson gson) {
        this.gson = gson;
    }

    public CompletableFuture<NBPRatesResponse> getNBPRate(Currency target) {
        var client = HttpClientProvider.getHttpClient();
        var request = HttpRequest.newBuilder()
                .GET()
                .header("Accept", "application/json")
                .uri(URI.create(NBP_RATE_URL.formatted(target)))
                .build();

        return client.sendAsync(request, new JsonBodyHandler<>(gson, NBPRatesResponse.class))
                        .thenApply(HttpResponse::body);
    }
}
