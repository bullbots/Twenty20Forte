package frc.robot.subsystems;

import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BackIntake extends SubsystemBase {
    TalonSRX m_BeltMotor;
    int m_Direction = 1;
    private static BackIntake instance = null;
    public BackIntake getInstance(){
        if(instance ==null){
            instance = new BackIntake();
        }
        return instance;
    }

    public BackIntake() {
        m_BeltMotor = new TalonSRX(Constants.Motors.BELT);
    }


    public void enable() {

        m_BeltMotor.set(TalonSRXControlMode.PercentOutput, m_Direction);

    }

    public void disable() {
        m_BeltMotor.set(TalonSRXControlMode.PercentOutput, 0);
    }
    /**
     * Sets the direction of the front intake motor
     * 
     * @param direction 1 is forward, -1 is backward, 0 is stop
     */
    public void setDirection(int direction) {
        m_Direction = direction;
    }

    public int getDirection() {
        return m_Direction;
    }
    public boolean isEnabled(){
        return m_BeltMotor.getMotorOutputPercent()!=0;
    }

}
