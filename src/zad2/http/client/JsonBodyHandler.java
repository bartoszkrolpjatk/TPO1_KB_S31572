package zad2.http.client;

import com.google.gson.Gson;
import zad2.exception.AppException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

class JsonBodyHandler<T> implements HttpResponse.BodyHandler<T> {

    private final Gson gson;
    private final Type targetType;

    JsonBodyHandler (Gson gson, Type classType) {
        this.gson = gson;
        this.targetType = classType;
    }

    JsonBodyHandler (Gson gson, Class<T> targetClass) {
        this.gson = gson;
        this.targetType = targetClass;
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
        int status = responseInfo.statusCode();
        if (status >= 200 && status < 300) {
            return correctBodySubscriber();
        }
        return errorBodySubscriber(status);
    }

    private HttpResponse.BodySubscriber<T> errorBodySubscriber(int status) {
        HttpResponse.BodySubscriber<Void> discarding = HttpResponse.BodySubscribers.discarding();
        Function<Void, T> exception = v -> {
            throw new AppException.ExternalAPI(status);
        };
        return HttpResponse.BodySubscribers.mapping(discarding, exception);
    }

    private HttpResponse.BodySubscriber<T> correctBodySubscriber() {
        HttpResponse.BodySubscriber<InputStream> upstream = HttpResponse.BodySubscribers.ofInputStream();
        Function<InputStream, T> downstream = is -> {
            try (InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                return gson.fromJson(reader, targetType);
            } catch (IOException e) {
                throw new AppException.Internal(e);
            }
        };
        return HttpResponse.BodySubscribers.mapping(upstream, downstream);
    }
}
