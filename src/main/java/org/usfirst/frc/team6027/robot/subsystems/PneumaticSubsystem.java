package org.usfirst.frc.team6027.robot.subsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team6027.robot.OperatorDisplay;
import org.usfirst.frc.team6027.robot.RobotConfigConstants;
import org.usfirst.frc.team6027.robot.commands.PneumaticsInitializationCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PneumaticSubsystem extends Subsystem {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private OperatorDisplay operatorDisplay;

	private StatefulSolenoid driveSolenoid;
    private StatefulSolenoid gripperSolenoid;
    private StatefulSolenoid kickerSolenoid;
    private StatefulSolenoid carriageBiFunctionSolenoid;
    
    private PneumaticsInitializationCommand pneumaticsInitializationCommand = null;
	
	public PneumaticSubsystem(OperatorDisplay operatorDisplay) {
        this.operatorDisplay = operatorDisplay;
        
        this.driveSolenoid = new StatefulSolenoid(RobotConfigConstants.PCM_1_ID_NUMBER,
                RobotConfigConstants.SOLENOID_1_PORT_A, RobotConfigConstants.SOLENOID_1_PORT_B);

        this.gripperSolenoid = new StatefulSolenoid(RobotConfigConstants.PCM_1_ID_NUMBER,
                RobotConfigConstants.SOLENOID_2_PORT_A, RobotConfigConstants.SOLENOID_2_PORT_B);

        this.kickerSolenoid = new StatefulSolenoid(RobotConfigConstants.PCM_1_ID_NUMBER,
                RobotConfigConstants.SOLENOID_3_PORT_A, RobotConfigConstants.SOLENOID_3_PORT_B);
        
        this.carriageBiFunctionSolenoid = new StatefulSolenoid(RobotConfigConstants.PCM_2_ID_NUMBER, 
                RobotConfigConstants.SOLENOID_4_PORT_A, RobotConfigConstants.SOLENOID_4_PORT_B);
	}

	@Override
	public void initDefaultCommand() {
	    /*
	    this.pneumaticInitializationCommand = new PneumaticsInitializationCommand(this);
	    this.setDefaultCommand(this.pneumaticInitializationCommand);
*/
	}

	/**
	 * When the run method of the scheduler is called this method will be called.
	 */
	@Override
	public void periodic() {
	}

	public DoubleSolenoid getDriveSolenoid() {
		return driveSolenoid;
	}

    public DoubleSolenoid getGripperSolenoid() {
        return gripperSolenoid;
    }
    
    public DoubleSolenoid getKickerSolenoid() {
        return kickerSolenoid;
    }

	public void toggleDriveSolenoid() {
        if (this.driveSolenoid.getState() == null) {
            logger.warn("Don't know drive solenoid state yet, can't toggle drive solenoid");
            return;
        }
        
        this.operatorDisplay.setFieldValue("Solenoid State", this.driveSolenoid.getState().name());

        if (this.driveSolenoid.getState() == DoubleSolenoid.Value.kReverse) {
            logger.trace("Calling toggleSolenoidForward");
            toggleDriveSolenoidForward();
        } else {
            logger.trace("Calling toggleSolenoidReverse");
            toggleDriveSolenoidReverse();
        }
	}

    public void toggleGripperSolenoid() {
        if (this.gripperSolenoid.getState() == null) {
            logger.warn("Don't know gripper solenoid state yet, can't toggle gripper solenoid");
            return;
        }
        
        this.operatorDisplay.setFieldValue("Gripper Solenoid State", this.gripperSolenoid.getState().name());

        if (this.gripperSolenoid.getState() == DoubleSolenoid.Value.kReverse) {
            logger.trace("Calling toggleGripperSolenoidForward");
            toggleGripperSolenoidForward();
        } else {
            logger.trace("Calling toggleGripperSolenoidReverse");
            toggleGripperSolenoidReverse();
        }
    }
    
    public boolean isKickerOut() {
        return this.kickerSolenoid.get() == DoubleSolenoid.Value.kReverse;
    }
    
    public void toggleKickerSolenoid() {
        if (this.kickerSolenoid.getState() == null) {
            logger.warn("Don't know kicker solenoid state yet, can't toggle kicker solenoid");
            return;
        }

        this.operatorDisplay.setFieldValue("Kicker Solenoid State", this.kickerSolenoid.getState().name());

        if (this.kickerSolenoid.getState() == DoubleSolenoid.Value.kReverse) {
            logger.trace("Calling toggleKickerSolenoidForward");
            toggleKickerSolenoidForward();
        } else {
            logger.trace("Calling toggleKickerSolenoidReverse");
            toggleKickerSolenoidReverse();
        }
    }
	
	public void toggleDriveSolenoidReverse() {
		logger.trace("Running toggleDriveSolenoidReverse");
		this.driveSolenoid.toggleReverse();
		this.operatorDisplay.setFieldValue("Speed", "HIGH");
	}

	public void toggleDriveSolenoidForward() {
		logger.trace("Running toggleDriveSolenoidForward");
        this.driveSolenoid.toggleForward();
		this.operatorDisplay.setFieldValue("Speed", "LOW");
	}
	
	public void toggleDriveSolenoidOff() {
		logger.trace("Running toggleDriveSolenoidOff");
        this.driveSolenoid.toggleOff();
	}

    
    public void toggleGripperSolenoidReverse() {
        logger.trace("Running toggleGripperSolenoidReverse");
        this.gripperSolenoid.toggleReverse();
        this.operatorDisplay.setFieldValue("Gripper", "OPENED");
    }

    public void toggleGripperSolenoidForward() {
        logger.trace("Running toggleGripperSolenoidForward");
        this.gripperSolenoid.toggleForward();
        this.operatorDisplay.setFieldValue("Gripper", "CLOSED");
    }
    
    public void toggleGripperSolenoidOff() {
        logger.trace("Running toggleGripperSolenoidOff");
        this.gripperSolenoid.toggleOff();
    }
    
    public void toggleKickerSolenoidReverse() {
        logger.trace("Running toggleKickerSolenoidReverse");
        this.kickerSolenoid.toggleReverse();
        this.operatorDisplay.setFieldValue("Kicker", "FORWARD");
    }

    public void toggleKickerSolenoidForward() {
        logger.trace("Running toggleKickerSolenoidForward");
        this.kickerSolenoid.toggleForward();
        this.operatorDisplay.setFieldValue("Kicker", "Retracted");
    }

    public void toggleKickerSolenoidOff() {
        logger.trace("Running toggleKickerSolenoidOff");
        this.kickerSolenoid.toggleOff();
    }
    
    public void toggleCarriageSolenoidOff() {
        logger.trace("Running toggleCarriageSolenoidOff");
        this.carriageBiFunctionSolenoid.toggleOff();
    }
    
    public void dropCarriageForDelivery() {
        logger.trace("Running dropCarriageForDelivery");
        this.carriageBiFunctionSolenoid.toggleReverse();
        this.operatorDisplay.setFieldValue("Carriage", "DELIVERY");
    }

    public void dropCarriageForClimb() {
        logger.trace("Running dropCarriageForClimb");
        this.carriageBiFunctionSolenoid.toggleForward();
        this.operatorDisplay.setFieldValue("Carriage", "CLIMB");
    }
    
    public boolean isReset() {
        return this.pneumaticsInitializationCommand != null && ! this.pneumaticsInitializationCommand.isRunning() 
                && this.pneumaticsInitializationCommand.isCompleted();
    }
    
    public void reset() {
        if (this.pneumaticsInitializationCommand == null) {
            pneumaticsInitializationCommand = new PneumaticsInitializationCommand(this);
        }
        
        pneumaticsInitializationCommand.start();
    }
}
