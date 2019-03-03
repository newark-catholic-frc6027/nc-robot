package frc.team6027.robot.sensors;

import org.apache.logging.log4j.Logger;

import frc.team6027.robot.RobotConfigConstants;
import frc.team6027.robot.data.Datahub;
import frc.team6027.robot.data.DatahubRegistry;
import frc.team6027.robot.sensors.EncoderSensors.EncoderKey;
import frc.team6027.robot.sensors.UltrasonicSensorManager.UltrasonicSensorKey;

import java.util.Map;

import org.apache.logging.log4j.LogManager;

public class SensorService {
    private final Logger logger = LogManager.getLogger(getClass());

    private EncoderSensors encoderSensors;
    private PIDCapableGyro gyroSensor;
    private AirPressureSensor airPressureSensor;
    private UltrasonicSensorManager ultrasonicSensorManager;
    private CameraSensors cameraSensor;
    private LimitSwitchSensors limitSwitchSensors;
    protected Datahub visionData;

    public SensorService() {
        this.encoderSensors = new EncoderSensors();
        this.gyroSensor = new NavxGyroSensor();
        this.airPressureSensor = new AirPressureSensor();
        this.ultrasonicSensorManager = new UltrasonicSensorManager();
        this.cameraSensor = new CameraSensors();
        this.limitSwitchSensors = new LimitSwitchSensors();
    }

    public void addMotorEncoders(Map<EncoderKey, MotorEncoder> encoders) {
        this.encoderSensors.registerEncoders(encoders);
    }
    
    public EncoderSensors getEncoderSensors() {
        return encoderSensors;
    }

    public PIDCapableGyro getGyroSensor() {
        return gyroSensor;
    }

    public AirPressureSensor getAirPressureSensor() {
        return airPressureSensor;
    }

    public UltrasonicSensor getUltrasonicSensor(UltrasonicSensorKey key) {
        return ultrasonicSensorManager.getSensor(key);
    }

    public CameraSensors getCameraSensor() {
        return cameraSensor;
    }

    public LimitSwitchSensors getLimitSwitchSensors() {
        return limitSwitchSensors;
    }

    public void resetAll() {
        logger.info("SensorService is resetting all sensors...");
        this.getGyroSensor().reset();
        this.getEncoderSensors().reset();

        // TODO: add more resets?
    }


    public double getElevatorHeightInches() {
        double elevatorEncoderRawValue = this.encoderSensors.getElevatorEncoder().getRaw();
        double heightInches = (EncoderSensors.ELEVATOR_ENCODER_EQUATION_M_FACTOR * elevatorEncoderRawValue) 
            + EncoderSensors.ELEVATOR_ENCODER_EQUATION_B;
        return heightInches;
    }
    
    public double getCurAngleHeadingToVisionTarget() {
        Datahub visionData = this.getVisionDatahub();
    
        // ContoursCenterXEntry x value of the center between the two contours-- 
        double centerContour = visionData.getDouble(RobotConfigConstants.CONTOURS_CENTER_X, 160.0);
        double visionDistanceInches = this.getCurDistToVisionTarget();
        //Calculate how far off from center -- The "c" variable in the trig calculation atan(c/a)
        double offDistancePixels = centerContour-160;
        double xFieldOfViewInches = (0.875 * visionDistanceInches) + 1.5;
        double pixelsToInchesConversionFactor = xFieldOfViewInches/320;
        double offDistanceInches = offDistancePixels * pixelsToInchesConversionFactor;

        //Calculated off center angle -- result of atan(c/a) 
        //TODO: Guard against divide by zero
        double offAngle = Math.atan(offDistanceInches/visionDistanceInches)*180/Math.PI;

        //Calculated angle to turn the robot -- current gyro heading + offAngle
        double adjustedAngle = this.getGyroSensor().getYawAngle() + offAngle;

		logger.info(">>> offAngle: {}, adjustedAngle: {}, offsetDistance: {}", offAngle, adjustedAngle, offDistancePixels);
        return  adjustedAngle;
    }

    public double getCurDistToVisionTarget() {
        return this.getCurDistToVisionTarget(false);
    }

    public double getCurDistToVisionTarget(boolean withUltrasonicFallback) {
        Datahub visionData = this.getVisionDatahub();

        double distance = visionData.getDouble(RobotConfigConstants.DISTANCE_TO_TARGET_INCHES, -1.0);
        if (distance < 0 && withUltrasonicFallback) {
            double ultdistance = Math.abs(this.getUltrasonicSensor(UltrasonicSensorKey.Front).getDistanceInches());
            logger.warn("Vision distance returned was {}, using ultrasonic distance reading of {}", distance, ultdistance);
            distance = ultdistance;
        }

        return distance;
    }

    protected Datahub getVisionDatahub() {
        if (this.visionData == null) {
            this.visionData = DatahubRegistry.instance().get(DatahubRegistry.VISION_KEY);
        }

        return this.visionData;
    }
}
