package io.github.itzispyder.clickcrystals.events;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Represents an event bus for calling and passing events
 */
public class EventBus {

    private final Map<Class<? extends Listener>, Listener> subscribedListeners;

    /**
     * Constructs a new event bus for handling events
     */
    public EventBus() {
        this.subscribedListeners = new HashMap<>();
    }

    /**
     * Subscribe a listener to the event bus
     * @param listener listener
     */
    public void subscribe(Listener listener) {
        if (listener == null) return;
        subscribedListeners.remove(listener.getClass());
        subscribedListeners.put(listener.getClass(),listener);
    }

    /**
     * Unsubscribes a listener from the event bus
     * @param listener listener
     */
    public void unsubscribe(Listener listener) {
        if (listener == null) return;
        subscribedListeners.remove(listener.getClass());
    }

    /**
     * Passes an event
     * @param event event
     * @return true if cancelled, false if not
     * @param <E> event type
     */
    public <E extends Event> boolean pass(E event) {
        listeners().values().forEach(listener -> {
            List<Method> methods = Arrays
                    .stream(listener.getClass().getDeclaredMethods())
                    .filter(Objects::nonNull)
                    .filter(method -> method.isAnnotationPresent(EventHandler.class))
                    .sorted(Comparator.comparing(method -> method.getAnnotation(EventHandler.class).priority()))
                    .toList();

            methods = new ArrayList<>(methods);
            Collections.reverse(methods);
            methods.forEach(method -> {
                try {
                    if (isValid(method, event)) {
                        method.setAccessible(true);
                        method.invoke(listener, event);
                    }
                }
                catch (Exception ignore) {}
            });
        });

        return event instanceof Cancellable c && c.isCancelled();
    }

    private <E extends Event> boolean isValid(Method method, E event) {
        if (method == null || event == null) return false;
        if (!method.isAnnotationPresent(EventHandler.class)) return false;
        if (method.getReturnType() != void.class) return false;
        if (method.getParameterCount() != 1) return false;
        return method.getParameters()[0].getType() == event.getClass();
    }

    /**
     * Returns a map of registered events by the event bus
     * @return map
     */
    public HashMap<Class<? extends Listener>, Listener> listeners() {
        return new HashMap<>(subscribedListeners);
    }
}
