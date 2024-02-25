package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.motors.WPI_CANSparkMax;

public class Slider extends SubsystemBase {
public static enum Mode {
        UP,
        DOWN
    }


    //Staging uses two different motors
    WPI_CANSparkMax m_SlideMotor;


    public Slider() {
        //TalonSRX requires Phoenix 
        m_SlideMotor = new WPI_CANSparkMax(Constants.Motors.SLIDE, MotorType.kBrushless);
    }


    public void slide(Mode mode) {    
        //I belive the motors the motors need to run in opposite directions to pull the note the same direciton

        switch (mode) {
            case DOWN:
                System.out.println("Moving DOWN");
                m_SlideMotor.set(0.75);
                break;
            case UP:
                System.out.println("Moving UP");
                m_SlideMotor.set(-0.75);
                break;
            default:
                break;
        }
    }

    public void moveToPosition(){
        m_SlideMotor.set(0.3);
    }

    public void stop() {
        m_SlideMotor.set(0);
    }
}
