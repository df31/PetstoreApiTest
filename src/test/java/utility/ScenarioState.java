package utility;

import lombok.Builder;
import lombok.Value;
import org.picocontainer.Startable;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
public class ScenarioState implements Startable {
    private LinkedHashMap<String, DataEnvelope<?>> dataMap = new LinkedHashMap<>();

    private DataEnvelope latest;

    @Override
    public void start() {

    }
    @Override
    public void stop() {
        dataMap.clear();
        latest = null;
    }

    public <T> T getData(String key) {
        Object data = dataMap.get(key).getData();
        Objects.requireNonNull(data, "ScenarioState doesn't contain anything with key " + key + ". Available data: " + dataMap.keySet());

        //noinspection unchecked
        return ((T) data);
    }

    public <T> T getData(String key, @SuppressWarnings("unused") Class<T> clazz) {
        return getData(key);
    }

    public <T> Optional<T> getOptionalData(String key) {
        //noinspection unchecked
        return Optional.ofNullable(dataMap.get(key))
                .map(DataEnvelope::getData)
                .map(data -> ((T) data));
    }

    public <T> Optional<T> getOptionalData(String key, @SuppressWarnings("unused") Class<T> clazz) {
        return getOptionalData(key);
    }

    public boolean containsKey(String key) {
        return dataMap.containsKey(key);
    }

    public String setData(String key, Object newData) {
        return setData(key, newData, false);
    }

    public String setData(String key, Object newData, boolean replaceOld) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        latest = DataEnvelope.builder()
                .data(newData)
                .traceElements(stackTrace)
                .build();
        if (!replaceOld && dataMap.containsKey(key)) {
            DataEnvelope dataEnvelope = dataMap.get(key);
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement element : dataEnvelope.getTraceElements()) {
                sb.append(element);
                sb.append("\n");
            }
            throw new IllegalArgumentException("Data already contains key \"" + key + "\" with value " + dataEnvelope.getData() + "\n"
                    + "\nOriginal value set in: \n\n" + sb.toString()
                    + "\nSecond value set in:");
        }
        dataMap.put(key, latest);
        return key;
    }

    public String setData(Object newData) {
        return setData(newData, false);
    }

    public String setData(Object newData, boolean replaceOld) {
        String key = UUID.randomUUID().toString();
        return setData(key, newData, replaceOld);
    }

    public <T> T getDataLatest() {
        Objects.requireNonNull(latest, "No data has been set!");
        //noinspection unchecked
        return ((T) latest.getData());
    }

    public <T> T getDataLatest(@SuppressWarnings("unused") Class<T> clazz) {
        return getDataLatest();
    }

    @Value
    @Builder
    private static class DataEnvelope<T> {
        private T data;
        private StackTraceElement[] traceElements;
    }

}
