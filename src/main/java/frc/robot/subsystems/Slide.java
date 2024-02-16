package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Slide extends SubsystemBase {
    enum Mode {
        UP,
        DOWN
    }


    //Staging uses two different motors
    WPI_CANSparkMax m_SlideMotor;


    public Slide() {
        //TalonSRX requires Phoenix 
        m_SlideMotor = new WPI_CANSparkMax(Constants.Motors.SLIDE, MotorType.kBrushless);
    }


    public void slide(Mode mode) {    
        //I belive the motors the motors need to run in opposite directions to pull the note the same direciton

        switch (mode) {
            case UP:

                m_SlideMotor.set(1);

            case DOWN:

                m_SlideMotor.set(-1);
            default:
                break;
        }
    }

    public void stop() {
        m_SlideMotor.set(0);
    }
}
