package org.usfirst.frc.team6027.robot;

/**
 * An interface used to define the operations necessary for displaying information and feedback to the robot human 
 * driver.  Using this interface allows us to change the underlying implementation of how information is displayed
 * to the robot driver.
 */
public interface OperatorDisplay {

    /**
     * The name of the field representing how far the robot has traveled.
     */
    String DISTANCE_FIELD_NAME = "Distance";

    /**
     * Sets a new numeric value on the display.
     * @param fieldName The name of the field whose value should be changed.
     * @param value The new field value.
     */
    public void setNumericFieldValue(String fieldName, Double value);
}
