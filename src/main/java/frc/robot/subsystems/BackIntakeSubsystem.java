package frc.robot.subsystems;

import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BackIntakeSubsystem extends SubsystemBase {
    TalonSRX m_backIntakeMotor;
    int m_MotorDirection = 0;

    public BackIntakeSubsystem() {
        //TalonSRX requires Phoenix 
        m_backIntakeMotor = new TalonSRX(Constants.Motors.INTAKE_BACK);
    }


    public void enableIntake() {
        m_backIntakeMotor.set(TalonSRXControlMode.PercentOutput, (m_MotorDirection*2)-1);
    }

    public void disableIntake() {
        m_backIntakeMotor.set(TalonSRXControlMode.PercentOutput, 0);

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
        if(m_backIntakeMotor.getMotorOutputPercent()==0){
            return false;
        }
        else{
            return true;
        }
    }

}
