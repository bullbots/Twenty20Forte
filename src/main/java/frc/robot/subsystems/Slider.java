package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.motors.WPI_CANSparkMax;

public class Slider extends SubsystemBase {
    public enum Mode {
        UP,
        DOWN
    }


    WPI_CANSparkMax m_SliderMotor;


    public Slider() {
        m_SliderMotor = new WPI_CANSparkMax(Constants.Motors.SLIDER, MotorType.kBrushless);
    }


    public void slide(Mode mode) {
        //I belive the motors the motors need to run in opposite directions to pull the note the same direciton

        switch (mode) {
            case DOWN:
                System.out.println("Moving DOWN");
                m_SliderMotor.set(0.75);
                break;
            case UP:
                System.out.println("Moving UP");
                m_SliderMotor.set(-0.75);
                break;
            default:
                break;
        }
    }

    public void stop() {
        m_SliderMotor.set(0);
    }
}
