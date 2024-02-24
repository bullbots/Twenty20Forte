package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Stager extends SubsystemBase {
    private static Stager instance = null;
    public Stager getInstance(){
        if(instance ==null){
            instance = new Stager();
        }
        return instance;
    }
    
    enum Mode {
        MAX_SPEED,
        HALF_SPEED,
        QUARTER_SPEED
    }


    //Staging uses two different motors
    TalonSRX m_StagerMotor;
    
    public Stager() {
        //TalonSRX requires Phoenix 5
        m_StagerMotor = new TalonSRX(Constants.Motors.STAGER);
    }


    public void start(Mode mode) {

        switch (mode) {
            case MAX_SPEED:

                m_StagerMotor.set(TalonSRXControlMode.PercentOutput, 1);

            case HALF_SPEED:

                m_StagerMotor.set(TalonSRXControlMode.PercentOutput, 0.5);

            case QUARTER_SPEED:

                m_StagerMotor.set(TalonSRXControlMode.PercentOutput, 0.25);
                
            default:
                break;
        }
    }

    public void stop() {
        m_StagerMotor.set(TalonSRXControlMode.PercentOutput, 0);
    }

}
