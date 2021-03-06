package frc.team6027.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

/**
 * The RobotConfigConstants class maps the various robot sensors and actuator
 * ports into a constant. This provides flexibility changing wiring, makes
 * checking the wiring easier and significantly reduces the number of magic
 * numbers floating around.
 * 
 * 
 * <pre>
                              CONTROLLER BOARD LAYOUT
                                     [FRONT]
    L                                                                      R
    ========================================================================
    |                                                        VRM     PCM   |
    |                                                       -----   -----  |
    |                                                      |     | |  1  | | 
    |                                    SPARK              -----   -----  |
    |                            ----  ----  ----  ----       ---------    |
    |                           |    ||    ||    ||    |     |   PDP   |   |
    |                           | 20 || 21 || 22 || 23 |     |         |   |
    |                           |    ||    ||    ||    |     |         |   |
    |                            ----  ----  ----  ----      |         |   |
    |                                                        |    9    |   |
    |                            ----------------------      |         |   |
    |                           |                      |     |         |   |
    |            SRX            |                      |     |         |   |
    |  ----  ----  ----  ----   |         RIO          |     |         |   |
    | |    ||    ||    ||    |  |          +           |     |         |   |
    | | 30 || 31 || 32 || 33 |  |         NAVX         |      ---------    |
    | |    ||    ||    ||    |  |                      |                   |
    |  ----  ----  ----  ----   |                      |                   |
    |                            ----------------------                    |
    ========================================================================
    L                                                                      R
                                     [REAR]
 * </pre>
 */
public class RobotConfigConstants {

    /**
     * The device identifier for the Talon SRX speed controller right gear box
     * drive motor 1
     * 
     * <pre>
     *      3
     *     1 2
     * </pre>
     */
    public static final int RIGHT_GEARBOX_MASTER_CIM_1_ID = 22;

    /**
     * The device identifier for the Talon SRX speed controller right gear box
     * drive motor 2
     * 
     * <pre>
     *      3
     *     1 2
     * </pre>
     */
    public static final int RIGHT_GEARBOX_SLAVE_CIM_2_ID = 23;

    /**
     * The device identifier for the Talon SRX speed controller right gear box
     * drive motor 3
     * 
     * <pre>
     *      3
     *     1 2
     * </pre>
     */
    public static final int RIGHT_GEARBOX_CIM_3_ID = -1; // not currently used

    /**
     * The device identifier for the Talon SRX speed controller left gear box
     * drive motor 1
     * 
     * <pre>
     *      3
     *     1 2
     * </pre>
     */
    public static final int LEFT_GEARBOX_MASTER_CIM_1_ID = 20;

    /**
     * The device identifier for the Talon SRX speed controller left gear box
     * drive motor 2
     * 
     * <pre>
     *      3
     *     1 2
     * </pre>
     */
    public static final int LEFT_GEARBOX_SLAVE_CIM_2_ID = 21;

    /**
     * The device identifier for the Talon SRX speed controller left gear box
     * drive motor 3
     * 
     * <pre>
     *      3
     *     1 2
     * </pre>
     */
    public static final int LEFT_GEARBOX_CIM_3_ID = -1; // not currently used

    /**
     * The motor controller identifier for the shooter motor.
     */
    public static final int SHOOTER_MOTOR_CIM_ID = 35;

    /**
     * The motor controller identifier for the turret motor;
     */
    public static final int TURRET_CIM_ID = 34;

    public static final int ELEVATOR_GEARBOX_CIM_1_ID = 32;

    public static final int MAST_SLIDE_GEARBOX_CIM_1_ID = 31;

    public static final int BALL_ELEVATOR_MOTOR_CIM_ID = 38;

    public static final int BALL_INTAKE_MOTOR_CIM_ID = 39;

    /**
     * The joystick port number corresponds to the 'USB Order' serial number on
     * the FRC Driver Station. It is used on construction of a new Joystick
     * object.
     */
    public static final int JOYSTICK1_PORT_NUMBER = 0;

    public static final int JOYSTICK2_PORT_NUMBER = 1;
    
   

    /**
     * 
     */
    public static final int ELEVATOR_ENCODER_DIO_CHANNEL_A = 8;

    /**
     * 
     */
    public static final int ELEVATOR_ENCODER_DIO_CHANNEL_B = 9;
    
    
    public final static int LIMIT_SWITCH_MAST_BOTTOM_DIO_CHANNEL = 7;
    public final static int LIMIT_SWITCH_MAST_TOP_DIO_CHANNEL = 6;

    public final static int LIMIT_SWITCH_MAST_SLIDE_FORWARD_DIO_CHANNEL = 4;
    public final static int LIMIT_SWITCH_MAST_SLIDE_BACKWARD_DIO_CHANNEL = 5;

        /*
     * Ultrasonic sensor constants
     */
    public static final int ULTRASONIC_FRONT_PING_DIO_CHANNEL = 0;
    public static final int ULTRASONIC_FRONT_ECHO_DIO_CHANNEL = 1;

//    public static final int ULTRASONIC_RIGHT_PING_DIO_CHANNEL = 0;
//    public static final int ULTRASONIC_RIGHT_ECHO_DIO_CHANNEL = 1;


    /**
     * The identifier of the left analog joystick on the Xbox controller used
     * for ArcadeDrive
     */
    public static final int LEFT_ANALOG_STICK = 1;
    /**
     * The identifier of the right analog joystick on the Xbox controller used
     * for ArcadeDrive
     */
    public static final int RIGHT_ANALOG_STICK = 4;

    /**
     * ******************************************************************** GYRO
     * constants
     ********************************************************************/

    /**
     * The port that the gyro sensor connects to
     */
    // TODO: try using SPI or I2C interfaces. SPI is recommended. See
    // https://www.chiefdelphi.com/forums/showthread.php?t=162704
    public static final Port GYRO_PORT = SerialPort.Port.kUSB;
    public static final edu.wpi.first.wpilibj.SPI.Port GYRO_ALT_PORT = SPI.Port.kMXP;
    /*
     * SOLENOID constants
     */
    public static final int PCM_1_ID_NUMBER = 1;

    /** Controlled by PCM_1 */
    public static final int SOLENOID_1_PORT_A = 0;
    public static final int SOLENOID_1_PORT_B = 1;

    /** Controlled by PCM_1 */
    public static final int SOLENOID_2_PORT_A = 2;
    public static final int SOLENOID_2_PORT_B = 3;

    /*
     * Pressure sensor constants
     */
    public static final int PRESSURE_SENSOR_PORT = 7;


    /* set to 1 if inversion is not needed, -1 if inversion is needed */
    public final static int OPTIONAL_LEFT_JOYSTICK_INVERSION = -1;
    /* set to 1 if inversion is not needed, -1 if inversion is needed */
    public final static int OPTIONAL_RIGHT_JOYSTICK_INVERSION = -1;

    /* set to 1 if inversion is not needed, -1 if inversion is needed */
    public final static int OPTIONAL_DRIVETRAIN_DIRECTION_INVERSION = 1;
}
