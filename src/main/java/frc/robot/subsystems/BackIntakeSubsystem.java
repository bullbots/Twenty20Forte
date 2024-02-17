package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.subsystems.BackIntakeSubsystem;
public class BackIntakeSubsystem extends SubsystemBase {
    private BackIntakeSubsystem(){}
    private static BackIntakeSubsystem instance =null;
    //get the motor for the belt here
    private TalonSRX motor = new TalonSRX(0);
    public static BackIntakeSubsystem getInstance(){
        if(instance==null){
            instance = new BackIntakeSubsystem();
        }
        return instance;
    }
    public void startForward(){
        motor.set(TalonSRXControlMode.PercentOutput, 1);
    }
    public void startReverse(){
        motor.set(TalonSRXControlMode.PercentOutput, -1);
    }
    public void stop(){
        motor.set(TalonSRXControlMode.PercentOutput, -1);
    }
    public String getState(){
        if(motor.getMotorOutputPercent()==0.0){
            return "off";
        }
        else if(motor.getMotorOutputPercent()>0){
            return "forward";
        }
        else{
            return "reverse";
        }

    }
    public boolean isOn(){
        if(getState() == "off"){
            return false;
        }
        else{
            return true;
        }
    }
}
