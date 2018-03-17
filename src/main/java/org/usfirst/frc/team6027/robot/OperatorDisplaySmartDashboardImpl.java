package org.usfirst.frc.team6027.robot;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An implementation of the OperatorDisplay interface which uses the SmartDashboard to display information back to the
 * robot human driver.
 */
public class OperatorDisplaySmartDashboardImpl implements OperatorDisplay {
    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(getClass());


    private SendableChooser<Integer> positionChooser = new SendableChooser<>();
    private SendableChooser<String> scenarioChooser = new SendableChooser<>();
    private SendableChooser<String> unlessChooser = new SendableChooser<>();
    
    @SuppressWarnings("rawtypes")
    private Map<ChooserName, SendableChooser> chooserCache = new HashMap<>();
    
    public OperatorDisplaySmartDashboardImpl() {
       initPositionChooser();
       initScenarioChooser();
       initUnlessChooser();
    }

    protected void initScenarioChooser() {
        this.chooserCache.put(ChooserName.Scenario, this.scenarioChooser);
        this.registerAutoScenario("NO SELECTION", true);
        SmartDashboard.putData(ChooserName.Scenario.displayName(), this.scenarioChooser);
    }
    
    protected void initUnlessChooser() {
        this.chooserCache.put(ChooserName.Unless, this.unlessChooser);
        this.unlessChooser.addDefault("NO SELECTION", "NO SELECTION" );
        SmartDashboard.putData(ChooserName.Unless.displayName(), this.unlessChooser);
    }
    
    protected void initPositionChooser() {
        this.chooserCache.put(ChooserName.Position, this.positionChooser);
        this.positionChooser.addDefault("NO SELECTION", new Integer(0));
        this.positionChooser.addObject("Pos 1", new Integer(1));
        this.positionChooser.addObject("Pos 2", new Integer(2));
        this.positionChooser.addObject("Pos 3", new Integer(3));
        
        SmartDashboard.putData(ChooserName.Position.displayName(), this.positionChooser);
    }

    @Override
    public void setData(NamedSendable sendable) {
        SmartDashboard.putData(sendable);
    }

    @Override
    public void setFieldValue(String fieldName, Double numValue) {
        SmartDashboard.putNumber(fieldName, numValue);
    }
        
    @Override
    public void setFieldValue(String fieldName, int numValue) {
        SmartDashboard.putNumber(fieldName, numValue);
    }
    
    @Override
    public void setFieldValue(String fieldName, String value) {
        SmartDashboard.putString(fieldName, value);
    }
    
    @Override
    public void setFieldValue(String fieldName, boolean value) {
        SmartDashboard.putBoolean(fieldName, value);
    }

    @Override
    public void registerAutoScenario(String displayName) {
        this.registerAutoScenario(displayName, false);
    }
    
    @Override
    public void registerAutoScenario(String displayName, boolean isDefaultCommand) {
        if (isDefaultCommand) {
            this.scenarioChooser.addDefault(displayName, displayName);
        } else {
            this.scenarioChooser.addObject(displayName, displayName);
        }
    }

    @Override
    public void registerUnlessOption(String displayName) {
        this.registerAutoScenario(displayName, false);
    }

    @Override
    public void registerUnlessOption(String displayName, boolean isDefaultCommand) {
        if (isDefaultCommand) {
            this.unlessChooser.addDefault(displayName, displayName);
        } else {
            this.unlessChooser.addObject(displayName, displayName);
        }
    }
    
    @Override
    public String getSelectedAutoScenario() {
        return (String) this.scenarioChooser.getSelected();
    }

    @Override
    public Integer getSelectedPosition() {
        return (Integer) this.positionChooser.getSelected();
    }

    @Override
    public String getSelectedUnlessOption() {
        return (String) this.unlessChooser.getSelected();
    }



       
}
