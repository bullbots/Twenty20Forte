package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.motors.WPI_CANSparkMax;

public class Slider extends SubsystemBase {
    enum Mode {
        UP,
        DOWN
    }


    WPI_CANSparkMax m_SliderMotor;


    public Slider() {
        //TalonSRX requires Phoenix 
        m_SliderMotor = new WPI_CANSparkMax(Constants.Motors.SLIDER, MotorType.kBrushless);
    }


    public void start(Mode mode) {    

        switch (mode) {
            case UP:

                m_SliderMotor.set(1);

            case DOWN:

                m_SliderMotor.set(-1);
            default:
                break;
        }
    }

    public void stop() {
        m_SliderMotor.set(0);
    }
}
