package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Slider extends SubsystemBase {

    public static final double DOWN_POS = 0.1;
    public static final double UP_POS = 9.3;

    public enum Mode {
        UP,
        DOWN
    }

    private TalonFX m_SliderMotor;
    private double m_position;
    private static final double TOLERANCE = 0.1;
    public boolean locked = false;
    private final MotionMagicVoltage m_mmReq = new MotionMagicVoltage(0);

    public Slider() {
        m_SliderMotor = new TalonFX(Constants.Motors.SLIDER);
        configSliderMotor(m_SliderMotor);
    }

    private void configSliderMotor(TalonFX driveMotor) {
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        /* Configure current limits */
        MotionMagicConfigs mm = config.MotionMagic;
        mm.MotionMagicCruiseVelocity = 25; // 5 rotations per second cruise
        mm.MotionMagicAcceleration = 50; // Take approximately 0.5 seconds to reach max vel
        // Take approximately 0.2 seconds to reach max accel
        mm.MotionMagicJerk = 50;

        Slot0Configs slot0 = config.Slot0;
        slot0.kP = 60;
        slot0.kI = 0;
        slot0.kD = 0.1;
        slot0.kV = 0.12;
        slot0.kS = 0.25; // Approximately 0.25V to get the mechanism moving

        FeedbackConfigs fdb = config.Feedback;
        fdb.SensorToMechanismRatio = 12.8;

        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = m_SliderMotor.getConfigurator().apply(config);
            if (status.isOK())
                break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }
    }

    public void slide(Mode mode) {
        // I believe the motors need to run in opposite directions to pull the note the
        // same direction
        if (locked) {
            System.out.println("Slider Locked");
            return;
        }
        switch (mode) {
            case DOWN:
                System.out.println("Moving DOWN");
                m_SliderMotor.set(-0.8);
                break;
            case UP:
                System.out.println("Moving UP");
                m_SliderMotor.set(0.8);
                break;
            default:
                break;
        }
    }

    public void moveToPosition(double position) {
        if (locked) {
            System.out.println("Slider Locked");
            return;
        }
        m_SliderMotor.setControl(m_mmReq.withPosition(position).withSlot(0));
        m_position = position;
    }

    public boolean isAtPosition() {
        return (m_position - TOLERANCE <= m_SliderMotor.getPosition().getValue()) &&
                (m_SliderMotor.getPosition().getValue() <= m_position + TOLERANCE);
    }

    public void stop() {
        m_SliderMotor.set(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Slider encoder", m_SliderMotor.getPosition().getValue());
    }
}
