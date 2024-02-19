package frc.robot.subsystems;

import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BackIntake extends SubsystemBase {
    TalonSRX m_BeltMotor1;
    TalonSRX m_BeltMotor2;
    int m_MotorDirection = 0;
    private static BackIntake instance = null;
    public BackIntake getInstance(){
        if(instance ==null){
            instance = new BackIntake();
        }
        return instance;
    }

    public BackIntake() {
        m_BeltMotor1 = new TalonSRX(Constants.Motors.BELT1);
        m_BeltMotor2 = new TalonSRX(Constants.Motors.BELT2);
    }


    public void enable() {
        m_BeltMotor1.set(TalonSRXControlMode.PercentOutput, (m_MotorDirection*2)-1);
        m_BeltMotor2.set(TalonSRXControlMode.PercentOutput, -((m_MotorDirection*2)-1));
    }

    public void disable() {
        m_BeltMotor1.set(TalonSRXControlMode.PercentOutput, 0);
        m_BeltMotor2.set(TalonSRXControlMode.PercentOutput, 0);
    }
    /**
     * Sets the direction of the front intake motor
     * 
     * @param direction 0 is forward, 1 is backward
     */
    public void setDirection(int direction) {
        m_MotorDirection = direction;
    }

    public int getDirection() {
        return m_MotorDirection;
    }
    public boolean isEnabled(){
        return m_BeltMotor1.getMotorOutputPercent()!=0;
    }

}
