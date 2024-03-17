package frc.robot.utils;

public class FieldOrientation {

    private boolean isField = false;

    public boolean isFieldRelative() {
        return isField;
    }

    public void toggleOrientation() {
        isField = !isField;  
    }

}
