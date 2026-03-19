package zad2.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class AppEventBus {

    private static final AppEventBus INSTANCE = new AppEventBus();

    private final Map<Class<Object>, List<Consumer<Object>>> listeners = new ConcurrentHashMap<>();

    public static AppEventBus bus() {
        return INSTANCE;
    }

    public <T> void subscribe(Class<T> eventType, Consumer<T> listener) {
        listeners.computeIfAbsent((Class<Object>) eventType, et -> new ArrayList<>()).add((Consumer<Object>) listener);
    }

    public void post(Object event) {
        var eventListeners = listeners.get(event.getClass());
        for (var listener : eventListeners) {
            listener.accept(event);
        }
    }


    private AppEventBus() {}
}
