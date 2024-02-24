// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// forward is negative and back is positive 
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.motors.WPI_CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Windlass extends SubsystemBase {


  private static final WPI_CANSparkMax m_WindlassMotor = new WPI_CANSparkMax(Constants.Motors.WINDLASS, MotorType.kBrushless); 
  int m_Direction = 1;

  private static Windlass m_instance = null;
  public Windlass getInstance(){
      if(m_instance ==null){
          m_instance = new Windlass();
      }
      return m_instance;
  }

  /** The constructor */
  public Windlass() {
    
  }


  public void enable() {
    // set it to a quarter speed
    m_WindlassMotor.set(m_Direction * 0.25);
  }

  public void disable() {
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

  public boolean isEnabled(){
      return false; 
  }
}


