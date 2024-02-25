package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.motors.WPI_CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

public class Windlass extends SubsystemBase {

    private static final WPI_CANSparkMax m_WindlassMotor = new WPI_CANSparkMax(Constants.Motors.WINDLASS, MotorType.kBrushless);
    int m_Direction = 1;

  /** The constructor */
  public Windlass() {
    
    m_WindlassMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

  }


  public void start() {
    // set it to a quarter speed
    m_WindlassMotor.set(m_Direction * 0.25);
  }

    public void stop() {
        m_WindlassMotor.set(0);
    }

    /**
     * Sets the direction of the front intake motor
     *
     * @param direction 0 is forward, 1 is backward
     */
    public void setDirection(int direction) {
        m_Direction = direction;
    }

    public int getDirection() {
        return m_Direction;
    }


}


