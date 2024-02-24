package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Staging extends SubsystemBase {
    private static Staging instance = null;
    public Staging getInstance(){
        if(instance ==null){
            instance = new Staging();
        }
        return instance;
    }
    enum Mode {
        MAX_SPEED,
        HALF_SPEED,
        QUARTER_SPEED
    }


    //Staging uses two different motors
    TalonSRX m_StagingMotor;
    TalonSRX m_BackStagingMotor;
    
    public Staging() {
        //TalonSRX requires Phoenix 5
        m_StagingMotor = new TalonSRX(Constants.Motors.STAGING);
    }


    public void start(Mode mode) {

        switch (mode) {
            case MAX_SPEED:

                m_StagingMotor.set(TalonSRXControlMode.PercentOutput, 1);

            case HALF_SPEED:

                m_StagingMotor.set(TalonSRXControlMode.PercentOutput, 0.5);

            case QUARTER_SPEED:

                m_StagingMotor.set(TalonSRXControlMode.PercentOutput, 0.25);
                
            default:
                break;
        }
    }

    public void stop() {
        m_StagingMotor.set(TalonSRXControlMode.PercentOutput, 0);
    }

    public boolean isEnabled() {
        return m_StagingMotor.getMotorOutputPercent()!=0;
    }
}
