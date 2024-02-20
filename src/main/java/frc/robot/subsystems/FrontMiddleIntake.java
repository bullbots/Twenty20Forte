package frc.robot.subsystems;

import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FrontMiddleIntake extends SubsystemBase {
    TalonSRX m_IntakeMotors;
    int m_Direction = 1;
    private static FrontMiddleIntake instance = null;
    public FrontMiddleIntake getInstance(){
        if(instance ==null){
            instance = new FrontMiddleIntake();
        }
        return instance;
    }

    public FrontMiddleIntake() {
        //TalonSRX requires Phoenix 
        m_IntakeMotors = new TalonSRX(Constants.Motors.INTAKE_FRONT);
    }


    public void enable() {
        m_IntakeMotors.set(TalonSRXControlMode.PercentOutput, m_Direction);
    }

    public void disable() {
        m_IntakeMotors.set(TalonSRXControlMode.PercentOutput, 0);

    }
    /**
     * Sets the direction of the front intake motor
     * 
     * @param direction 1 is forward, 0 is backward
     */
    public void setDirection(int direction) {
        m_Direction = direction;
    }

    public int getDirection() {
        return m_Direction;
    }

    public boolean isEnabled(){
        return m_IntakeMotors.getMotorOutputPercent()!=0;
    }
}
