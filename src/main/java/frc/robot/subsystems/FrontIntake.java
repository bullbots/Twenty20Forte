package frc.robot.subsystems;

import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FrontIntake extends SubsystemBase {
    TalonSRX frontIntakeMotor;
    int MotorDirection;

    public FrontIntake(){
        frontIntakeMotor = new TalonSRX(Constants.Motors.INTAKE_FRONT);
    }


    public void EnableIntake(){
        frontIntakeMotor.set(TalonSRXControlMode.PercentOutput, (MotorDirection*2)-1);
    }

    public void DisableIntake() {
        frontIntakeMotor.set(TalonSRXControlMode.PercentOutput, 0);

    }
    /**
     * Sets the direction of the front intake motor
     * 
     * @param direction 0 is forward, 1 is backward
     */
    public void SetIntakeDirection(int direction) {
        MotorDirection = direction;
    }

    public int GetIntakeDirection() {
        return MotorDirection;
    }

}
