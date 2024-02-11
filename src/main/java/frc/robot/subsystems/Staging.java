package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Staging extends SubsystemBase {
    TalonSRX m_StagingMotor;

    public Staging() {
        //TalonSRX requires Phoenix 
        m_StagingMotor = new TalonSRX(Constants.Motors.STAGING);
    }


    public void enableStaging() {
        m_StagingMotor.set(TalonSRXControlMode.PercentOutput, 1);
    }

    public void disableStaging() {
        m_StagingMotor.set(TalonSRXControlMode.PercentOutput, 0);

    }
}
