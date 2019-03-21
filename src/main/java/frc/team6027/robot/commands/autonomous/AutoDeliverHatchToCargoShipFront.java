package frc.team6027.robot.commands.autonomous;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import frc.team6027.robot.OperatorDisplay;
import frc.team6027.robot.commands.DriveStraightCommand;
import frc.team6027.robot.commands.PneumaticsInitializationCommand;
import frc.team6027.robot.commands.ResetSensorsCommand;
import frc.team6027.robot.commands.ToggleKickHatchCommand;
import frc.team6027.robot.commands.TurnCommand;
import frc.team6027.robot.commands.TurnWhileDrivingCommand;
import frc.team6027.robot.commands.VisionTurnCommand;
import frc.team6027.robot.commands.DriveStraightCommand.DriveDistanceMode;
import frc.team6027.robot.commands.TurnWhileDrivingCommand.TargetVector;
import frc.team6027.robot.commands.autonomous.AutonomousPreference;
import frc.team6027.robot.data.Datahub;
import frc.team6027.robot.data.DatahubRegistry;
import frc.team6027.robot.data.VisionDataConstants;
import frc.team6027.robot.field.Field;
import frc.team6027.robot.field.StationPosition;
import frc.team6027.robot.sensors.SensorService;
import frc.team6027.robot.subsystems.DrivetrainSubsystem;
import frc.team6027.robot.subsystems.ElevatorSubsystem;
import frc.team6027.robot.subsystems.PneumaticSubsystem;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoDeliverHatchToCargoShipFront extends CommandGroup implements KillableAutoCommand {
    private final Logger logger = LogManager.getLogger(getClass());

    private SensorService sensorService;
    private DrivetrainSubsystem drivetrainSubsystem;
    private PneumaticSubsystem pneumaticSubsystem;
    private ElevatorSubsystem elevatorSubsystem;
    private OperatorDisplay operatorDisplay;
    private Preferences prefs = Preferences.getInstance();
    private StationPosition stationPosition;
    private Field field;


    public AutoDeliverHatchToCargoShipFront(AutonomousPreference cargoShipSide,
            StationPosition stationPosition, SensorService sensorService, 
            DrivetrainSubsystem drivetrainSubsystem, PneumaticSubsystem pneumaticSubsystem, ElevatorSubsystem elevatorSubsystem, 
            OperatorDisplay operatorDisplay, Field field) {
        
        this.sensorService = sensorService;
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.pneumaticSubsystem = pneumaticSubsystem;
        this.operatorDisplay = operatorDisplay;
        this.stationPosition = stationPosition;
        this.elevatorSubsystem = elevatorSubsystem;
        this.field = field;
  
//        this.addSequential(new PneumaticsInitializationCommand(this.pneumaticSubsystem));
/*
        this.addSequential( new DriveStraightCommand(this.sensorService, this.drivetrainSubsystem, 
            this.operatorDisplay, 36.0, DriveDistanceMode.DistanceFromObject, 0.5)
        );
*/       
        /*
        double leg1Distance = this.prefs.getDouble("A-L1-Storm-Hatch", -48.0);
        double leg1Angle = 0.0;
        double leg2Distance = this.prefs.getDouble("A-L2-Storm-Hatch", 47.0);
        double leg2Angle = this.prefs.getDouble("A-A1-Storm-Hatch", 120.0);//30.0 * (this.startingSide == StationPosition.Right ? 1.0 : -1.0);
        //double leg3Distance = this.prefs.getDouble("A-L3-SS-Scale", 220.0);
        //double leg3Angle = 0.0;

        TargetVector[] targetVectors = new TargetVector[] { 
                new TargetVector(leg1Angle, leg1Distance),
                new TargetVector(leg2Angle, leg2Distance),
                //new TargetVector(leg3Angle, leg3Distance),
                
        };

        this.addSequential(new TurnWhileDrivingCommand(sensorService, drivetrainSubsystem, operatorDisplay, targetVectors, 
            DriveDistanceMode.DistanceReadingOnEncoder, .4));
            */

        AutoCommandHelper.addAutoInitCommands(this, pneumaticSubsystem, sensorService);

        // Off ramp forward
        this.addSequential(new DriveStraightCommand("B-L1-Storm-Hatch", DriveDistanceMode.DistanceReadingOnEncoder, "B-P1-Storm-Hatch", 
            null, this.sensorService, this.drivetrainSubsystem, this.operatorDisplay)
        );

        // Turn toward rocket
        this.addSequential(new TurnCommand("B-A1-Storm-Hatch", this.sensorService, this.drivetrainSubsystem, this.operatorDisplay, 
          "B-A1P-Storm-Hatch"));

        // Travel toward rocket
        this.addSequential(new DriveStraightCommand("B-L2-Storm-Hatch", DriveDistanceMode.DistanceReadingOnEncoder, "B-P2-Storm-Hatch", 
            null, this.sensorService, this.drivetrainSubsystem, this.operatorDisplay)
        );

        this.addSequential(new VisionTurnCommand(this.sensorService, this.drivetrainSubsystem, this.operatorDisplay));

        // Travel toward rocket
        this.addSequential(new DriveStraightCommand("B-L3-Storm-Hatch", DriveDistanceMode.DistanceReadingOnEncoder, "B-P3-Storm-Hatch", 
            null, this.sensorService, this.drivetrainSubsystem, this.operatorDisplay)
        );

        this.addSequential(new VisionTurnCommand(this.sensorService, this.drivetrainSubsystem, this.operatorDisplay));
        // Travel toward rocket
        this.addSequential(new DriveStraightCommand("B-L4-Storm-Hatch", DriveDistanceMode.DistanceReadingOnEncoder, "B-P4-Storm-Hatch", 
            null, this.sensorService, this.drivetrainSubsystem, this.operatorDisplay)
        );
        this.addSequential(new VisionTurnCommand(this.sensorService, this.drivetrainSubsystem, this.operatorDisplay));

        // TODO: replace this with Vision turn, use VisionTurnCommand
        // TODO: Add logic to handle potential failure of Vision turn

        // Turn toward rocket
//        this.addSequential(new TurnCommand("A-A2-Storm-Hatch", this.sensorService, this.drivetrainSubsystem, this.operatorDisplay));
        
        // Turn toward rocket with vision

//        this.addSequential(this.makeVisionDistanceCommand());

        /*
        this.addSequential(new DriveStraightCommand("A-L3-2-Storm-Hatch", 
            DriveStraightCommand.DriveDistanceMode.DistanceFromObject, 
            "A-P3-2-Storm-Hatch", null, this.sensorService, this.drivetrainSubsystem, this.operatorDisplay));
        */
        //Approach rocket
        this.addSequential(new DriveStraightCommand("B-L5-Storm-Hatch", 
            DriveStraightCommand.DriveDistanceMode.DistanceFromObject, 
            "B-P5-Storm-Hatch", null, this.sensorService, this.drivetrainSubsystem, this.operatorDisplay));
        
        this.addSequential(new ToggleKickHatchCommand(this.pneumaticSubsystem));
        this.addSequential(new ToggleKickHatchCommand(this.pneumaticSubsystem));
        // Last leg to rocket 
        /*
        Command multiLegDriveCmd = createMultiLegDriveCommand();
        this.addSequential(multiLegDriveCmd);
        */
//        this.addSequential(new ElevatorCommand(70.0, 0.6, this.sensorService, this.elevatorSubsystem));
        // TODO: Raise arm to top of rocket
        // TODO: Deliver Hatch
        // TODO: Lower arm to proper position
        // Back up from rocket
/*        
        this.addSequential(new DriveStraightCommand("A-L6-Storm-Hatch", DriveDistanceMode.DistanceFromObject, "A-P6-Storm-Hatch", 
            null, this.sensorService, this.drivetrainSubsystem, this.operatorDisplay)
        );


        // TODO: Set arm to proper position
        // TODO: Pick up hatch
*/        
        

    }
/*
    protected Command createDriveToScaleCommand() {
        Command cmd = new DriveStraightCommand(
                this.sensorService, this.drivetrainSubsystem, this.operatorDisplay,
                this.prefs.getDouble("A-L4-SS-Scale", 30.0),
                DriveDistanceMode.DistanceReadingOnEncoder, 
                0.55
        );
        
        return cmd;
    }
*/

    
    @Override
    public void start() {
        registerAsKillable();
        this.logger.info(">>>>>>>>>>>>>>>>>>>> {} command starting...", this.getClass().getSimpleName());
        super.start();
    }

    protected Command makeDelayCommand(int delayMs) {
        Command cmd = new Command() {
            Long elapsedTime = null;
            Long startTime = null;
            @Override
            protected boolean isFinished() {
                boolean finished = false;
                if (this.elapsedTime >= delayMs) {
                    finished = true;
                    this.elapsedTime = null;
                }
                return finished;
            }       

            @Override
            protected void execute() {
                if (elapsedTime == null) {
                    startTime = System.currentTimeMillis();
                }
                elapsedTime = System.currentTimeMillis() - this.startTime;
            }
        };
        return cmd;
    }
    protected Command makeVisionDistanceCommand() {
        Command cmd = new Command() {
            Datahub visionData = DatahubRegistry.instance().get(VisionDataConstants.VISION_DATA_KEY);
            Preferences prefs = Preferences.getInstance();
            Long elapsedTime = null;
            Long startTime = null;
            Double visionDist = null;
            @Override
            protected boolean isFinished() {
                boolean finished = false;
                if (this.elapsedTime >= 350) {
                    finished = true;
                } else {
                    if (this.visionDist >= 0.0) {
                        finished = true;
                    }
                }

                if (finished) {
                    this.prefs.putDouble(VisionDataConstants.TARGET_DISTANCE_KEY, this.visionDist);
                    AutoDeliverHatchToCargoShipFront.this.logger.info("Vision Distance to be used for driving to target: {}", this.visionDist);
                    this.visionDist = null;
                    this.elapsedTime = null;
                    this.startTime = null;
                }
                return finished;
            }

            @Override
            protected void execute() {
                if (elapsedTime == null) {
                    startTime = System.currentTimeMillis();
                }
                elapsedTime = System.currentTimeMillis() - this.startTime;

                this.visionDist = visionData.getDouble(VisionDataConstants.TARGET_DISTANCE_KEY, -1.0);
            }

        };
        return cmd;
    }
    
    protected Command createMultiLegDriveCommand() {
        TargetVector[] turnVectors = new TargetVector[] { 
                new TargetVector(null, "A-L3-1-Storm-Hatch", "A-P3-1-Storm-Hatch"),
                new TargetVector(null, "A-L3-2-Storm-Hatch", "A-P3-2-Storm-Hatch"),
        };
        
        Command cmd = new TurnWhileDrivingCommand(
                this.getSensorService(), this.getDrivetrainSubsystem(), this.getOperatorDisplay(), 
                turnVectors,
                DriveDistanceMode.DistanceReadingOnEncoder, 0.5
        );
        
        return cmd;
    }



    public DrivetrainSubsystem getDrivetrainSubsystem() {
        return drivetrainSubsystem;
    }


    public void setDrivetrainSubsystem(DrivetrainSubsystem drivetrainSubsystem) {
        this.drivetrainSubsystem = drivetrainSubsystem;
    }


    public PneumaticSubsystem getPneumaticSubsystem() {
        return pneumaticSubsystem;
    }


    public void setPneumaticSubsystem(PneumaticSubsystem pneumaticSubsystem) {
        this.pneumaticSubsystem = pneumaticSubsystem;
    }


    public OperatorDisplay getOperatorDisplay() {
        return operatorDisplay;
    }


    public void setOperatorDisplay(OperatorDisplay operatorDisplay) {
        this.operatorDisplay = operatorDisplay;
    }


    public StationPosition getStationPosition() {
        return stationPosition;
    }


    public void setStationPosition(StationPosition stationPosition) {
        this.stationPosition = stationPosition;
    }


    public SensorService getSensorService() {
        return sensorService;
    }


    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    public ElevatorSubsystem getElevatorSubsystem() {
        return elevatorSubsystem;
    }

    public void setElevatorSubsystem(ElevatorSubsystem elevatorSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
    }
}