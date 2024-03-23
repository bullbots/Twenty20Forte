package frc.robot.commands.Autonomous;

import frc.robot.commands.drivetrain.TurningRobotFuzzyLogic;
import edu.wpi.first.networktables.NetworkTableInstance;
public class TurnToSpeaker extends TurningRobotFuzzyLogic{
    
    public TurnToSpeaker(){
        super(() -> NetworkTableInstance.getDefault().getTable("limelight-limea").getEntry("tx").getDouble(0));
    }
}
