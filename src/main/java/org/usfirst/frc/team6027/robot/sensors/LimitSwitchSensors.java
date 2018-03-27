package org.usfirst.frc.team6027.robot.sensors;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team6027.robot.RobotConfigConstants;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitchSensors {

    public enum LimitSwitchId {
        MastTop,
        MastBottom
    }
    
    
    private DigitalInput limitSwitchBottomA = new DigitalInput(RobotConfigConstants.LIMIT_SWITCH_BOTTOM_A_CHANNEL); // bottom a
    private DigitalInput limitSwitchBottomB = new DigitalInput(RobotConfigConstants.LIMIT_SWITCH_BOTTOM_B_CHANNEL); // bottom b
    private DigitalInput limitSwitchTopA = new DigitalInput(RobotConfigConstants.LIMIT_SWITCH_TOP_A_CHANNEL); // top a
    private DigitalInput limitSwitchTopB = new DigitalInput(RobotConfigConstants.LIMIT_SWITCH_TOP_B_CHANNEL); // top b
    
    
    private Map<LimitSwitchId, DigitalInput> limitSwitchMap = new HashMap<>();
    
    
    public LimitSwitchSensors() {
    }

    @Deprecated
    public boolean isLimitSwitchTripped(LimitSwitchId id) {
        boolean switchTripped = true;
        
        if (id == LimitSwitchId.MastTop) {
            switchTripped = this.limitSwitchTopA.get();
        } else if (id == LimitSwitchId.MastBottom) {
            switchTripped = this.limitSwitchBottomA.get();
        }
        
        return switchTripped;
    }
    
    /**
     * TODO: Once all limit switches are installed, use this code
     * @param id
     * @return
     */
    public boolean isLimitSwitchTrippedAlt(LimitSwitchId id) {
        boolean switchTripped = true;
        if (id == LimitSwitchId.MastTop) {
            switchTripped = this.limitSwitchTopA.get() && this.limitSwitchTopB.get();
        } else if (id == LimitSwitchId.MastBottom) {
            switchTripped = this.limitSwitchBottomA.get() && this.limitSwitchBottomB.get();
        }
        
        return switchTripped;
    }
    
}
