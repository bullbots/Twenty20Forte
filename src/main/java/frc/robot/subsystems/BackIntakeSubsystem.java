package frc.robot.subsystems;

import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BackIntakeSubsystem extends SubsystemBase {
    TalonSRX BeltMotor1;
    TalonSRX BeltMotor2;
    int m_MotorDirection = 0;

    public BackIntakeSubsystem() {
        BeltMotor1 = new TalonSRX(Constants.Motors.BELT1);
        BeltMotor2 = new TalonSRX(Constants.Motors.BELT2);
    }


    public void enableIntake() {
        BeltMotor1.set(TalonSRXControlMode.PercentOutput, (m_MotorDirection*2)-1);
        BeltMotor1.set(TalonSRXControlMode.PercentOutput, -((m_MotorDirection*2)-1));
    }

    public void disableIntake() {
        BeltMotor1.set(TalonSRXControlMode.PercentOutput, 0);

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
    public boolean isOn(){
        return BeltMotor1.getMotorOutputPercent()!=0;
    }

}
