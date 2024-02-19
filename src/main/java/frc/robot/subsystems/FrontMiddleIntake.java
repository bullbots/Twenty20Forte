package frc.robot.subsystems;

import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FrontMiddleIntake extends SubsystemBase {
    TalonSRX m_IntakeMotors;
    int m_MotorDirection = 0;

    public FrontMiddleIntake() {
        //TalonSRX requires Phoenix 
        m_IntakeMotors = new TalonSRX(Constants.Motors.INTAKE_FRONT);
    }


    public void enableIntake() {
        m_IntakeMotors.set(TalonSRXControlMode.PercentOutput, (m_MotorDirection*2)-1);
    }

    public void disableIntake() {
        m_IntakeMotors.set(TalonSRXControlMode.PercentOutput, 0);

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

    public boolean isEnabled(){
        return m_IntakeMotors.getMotorOutputPercent()!=0;
    }
}
