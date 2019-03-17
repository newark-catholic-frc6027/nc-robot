package frc.team6027.robot.sensors;

import java.util.HashMap;
import java.util.Map;

import frc.team6027.robot.RobotConfigConstants;

public class UltrasonicSensorManager {
    public enum UltrasonicSensorKey {
        Front,
 //       Right
    }

    private Map<UltrasonicSensorKey, UltrasonicSensor> sensorRegistry = new HashMap<>();

    public UltrasonicSensorManager() {
        
        this.sensorRegistry.put(UltrasonicSensorKey.Front, 
            new UltrasonicSensor(
                RobotConfigConstants.ULTRASONIC_FRONT_PING_DIO_CHANNEL,
                RobotConfigConstants.ULTRASONIC_FRONT_ECHO_DIO_CHANNEL
            )
        );
        /*
        this.sensorRegistry.put(UltrasonicSensorKey.Right, 
            new UltrasonicSensor(
                RobotConfigConstants.ULTRASONIC_RIGHT_PING_DIO_CHANNEL,
                RobotConfigConstants.ULTRASONIC_RIGHT_ECHO_DIO_CHANNEL
            )
        );
        */
    }

    public UltrasonicSensorManager(Map<UltrasonicSensorKey, UltrasonicSensor> ultrasonics) {
        this.registerSensors(ultrasonics);
    }

    public void registerSensor(UltrasonicSensorKey key, UltrasonicSensor sensor) {
        this.sensorRegistry.put(key, sensor);
    }

    public void registerSensors(Map<UltrasonicSensorKey, UltrasonicSensor> encoders) {
        this.sensorRegistry.putAll(encoders);
    }

    public UltrasonicSensor getSensor(UltrasonicSensorKey key) {
        return this.sensorRegistry.get(key);
    }

    public void removeSensor(UltrasonicSensorKey key) {
        this.sensorRegistry.remove(key);
    }

    protected void initialize() {
    }
}