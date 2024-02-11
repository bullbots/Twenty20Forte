package frc.robot.subsystems;

import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FrontIntake extends SubsystemBase {
    TalonSRX frontIntakeMotor;
    int MotorDirection;

    public void frontIntake() {
        frontIntakeMotor = new TalonSRX(Constants.Motors.INTAKE_FRONT);
    }


    public void enableIntake() {
        frontIntakeMotor.set(TalonSRXControlMode.PercentOutput, (MotorDirection*2)-1);
    }

    public void disableIntake() {
        frontIntakeMotor.set(TalonSRXControlMode.PercentOutput, 0);

    }
    /**
     * Sets the direction of the front intake motor
     * 
     * @param direction 0 is forward, 1 is backward
     */
    public void setIntakeDirection(int direction) {
        MotorDirection = direction;
    }

    public int getIntakeDirection() {
        return MotorDirection;
    }

}
