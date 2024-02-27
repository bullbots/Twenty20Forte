package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.motors.WPI_CANSparkMax;

public class Slider extends SubsystemBase {
    public enum Mode {
        UP,
        DOWN
    }


    WPI_CANSparkMax m_SliderMotor;
    private int m_position;
    private static final int TOLERANCE = 50;


    public Slider() {
        m_SliderMotor = new WPI_CANSparkMax(Constants.Motors.SLIDER, MotorType.kBrushless);
    }


    public void slide(Mode mode) {
        //I belive the motors the motors need to run in opposite directions to pull the note the same direciton

        switch (mode) {
            case DOWN:
                System.out.println("Moving DOWN");
                m_SliderMotor.set(0.3);
                break;
            case UP:
                System.out.println("Moving UP");
                m_SliderMotor.set(-0.3);
                break;
            default:
                break;
        }
    }

    public void moveToPosition(int position) {
        m_SliderMotor.pidController.setReference(position, CANSparkMax.ControlType.kSmartMotion);
        m_position = position;
    }

    public boolean isAtPosition() {
        return (m_position - TOLERANCE <= m_SliderMotor.encoder.getPosition()) && 
        (m_SliderMotor.encoder.getPosition() <= m_position + TOLERANCE); 
    }

    public void stop() {
        m_SliderMotor.set(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("slider encoder", m_SliderMotor.encoder.getPosition());
    }
}
