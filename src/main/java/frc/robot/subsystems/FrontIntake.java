package frc.robot.subsystems;

import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FrontIntake extends SubsystemBase {
    TalonSRX m_frontIntakeMotor;
    int m_MotorDirection = 0;

    public FrontIntake() {
        //TalonSRX requires Phoenix 
        m_frontIntakeMotor = new TalonSRX(Constants.Motors.INTAKE_FRONT);
    }


    public void enableIntake() {
        m_frontIntakeMotor.set(TalonSRXControlMode.PercentOutput, (m_MotorDirection*2)-1);
    }

    public void disableIntake() {
        m_frontIntakeMotor.set(TalonSRXControlMode.PercentOutput, 0);

    }
    /**
     * Sets the direction of the front intake motor
     * 
     * @param direction 0 is forward, 1 is backward
     */
    public void setIntakeDirection(int direction) {
        m_MotorDirection = direction;
    }

    public int getIntakeDirection() {
        return m_MotorDirection;
    }

}
