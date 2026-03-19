package zad2.http.provider;

import java.net.http.HttpClient;

public class HttpClientProvider {

    private static final HttpClient INSTANCE = HttpClient.newHttpClient();

    public static HttpClient getHttpClient() {
        return INSTANCE;
    }
    
    public static String getWeatherApiKey() {
        return ApiKeys.getWeatherApiKey();
    }

    public static String getRatesApiKey() {
        return ApiKeys.getRatesApiKey();
    }

    public static void closeHttpClient() {//todo: close http client on closing window event
        INSTANCE.close();
    }

    private HttpClientProvider() {}
}
