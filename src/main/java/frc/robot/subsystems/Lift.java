// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lift extends SubsystemBase {
  private TalonFX m_liftMotor;
  private int m_motorID;
  private Lift instance = null;
  private int m_Direction = 1;

  public Lift(int motorID) {
    m_liftMotor = new TalonFX(motorID);
    m_motorID = motorID;
  }


  public Lift getInstance(){
      if(instance == null){
          instance = new Lift(m_motorID);
      }
      return instance;
  }
  /**
   * Arms extending are positive, but doing a pull up is negative as arms are
   * being retracted. Use as reference while coding the two movements 
   */
  
  //moves the arms up to reach the chain
  public void enable() { 
    
    m_liftMotor.set(-m_Direction);
  }

  //moves the robot up in the air
  public void disable() {
    m_liftMotor.set(0);
  }
  public void setDirection(int direction){
    m_Direction = direction;
  }
  public int getDirection(){
    return m_Direction;
  }
  public boolean isEnabled(){
    return m_liftMotor.get()!=0;
  }
}
