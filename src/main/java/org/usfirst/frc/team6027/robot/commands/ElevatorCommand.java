package org.usfirst.frc.team6027.robot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team6027.robot.sensors.LimitSwitchSensors;
import org.usfirst.frc.team6027.robot.sensors.LimitSwitchSensors.LimitSwitchId;
import org.usfirst.frc.team6027.robot.sensors.SensorService;
import org.usfirst.frc.team6027.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team6027.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorCommand extends Command {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public final static String NAME = "Elevator Command";
    private Preferences prefs = Preferences.getInstance();
    protected static final int LOG_REDUCTION_MOD = 10;
    protected int execCount = 0;

    
    public enum ElevatorDirection {
        Up,
        Down
    };
    
    private ElevatorDirection direction = null;
    private ElevatorSubsystem elevator;
    private LimitSwitchSensors limitSwitches;
    private DigitalInput topLimitSwitch;
    private DigitalInput bottomLimitSwitch;
    private DrivetrainSubsystem driveTrain;
    
    private double power = 0.5;
    
    public ElevatorCommand(ElevatorDirection direction, double power, SensorService sensorService, ElevatorSubsystem elevator, DrivetrainSubsystem drivetrain) {
        this.direction = direction;
        this.elevator = elevator;
        this.driveTrain = drivetrain;
        this.power = power;
        this.limitSwitches = sensorService.getLimitSwitchSensors();
        this.topLimitSwitch = this.limitSwitches.getLimitSwitch(LimitSwitchId.MastTop);
        this.bottomLimitSwitch = this.limitSwitches.getLimitSwitch(LimitSwitchId.MastBottom);
        
        this.setName(NAME);
        requires(elevator);
    }

    @Override
    protected void initialize() {
        logger.info("Elevator Command starting...");
    }
    
    @Override
    protected void end() {
        this.clearRequirements();
    }
    
    @Override
    protected boolean isFinished() {
         
        boolean bottomSwitchTripped = this.bottomLimitSwitch.get();
        boolean topSwitchTripped = this.topLimitSwitch.get();

        // Checking isGoingUp/Down may be affecting communication
        boolean done = (this.direction == ElevatorDirection.Up && this.elevator.isGoingUp() && topSwitchTripped) 
                           ||
                       (this.direction == ElevatorDirection.Down && this.elevator.isGoingDown() && bottomSwitchTripped);

        if (done) {
            logger.info(">>>>> Elevator command FINISHED. topSwitch: {}, bottomSwitch: {}", topSwitchTripped, bottomSwitchTripped);
            this.elevator.elevatorStop();
            this.clearRequirements();
        }
        return done;
    }
    
    protected void execute() {
        this.execCount++;
        if (this.direction == ElevatorDirection.Up) {
            this.elevator.elevatorUp(power);
        } else if (this.direction == ElevatorDirection.Down) {
            this.elevator.elevatorDown(power);
        } else {
            logger.error("Elevator Execute stopped!  Direction not set!");
        }
        // Be explicit in order to try reduce motor "Output not updated often enough" warnings
        this.driveTrain.stopMotor();
    }

    
}
