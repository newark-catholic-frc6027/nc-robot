package frc.team6027.robot.data;

public interface Datahub {
    String getName();
    String getString(String key);
    String getString(String key, String defaultValue);
    Double getDouble(String key);
    Double getDouble(String key, Double defaultValue);
    Float getFloat(String key);
    Float getFloat(String key, Float defaultValue);

    void put(String key, String value);
    void put(String key, Double value);
}