// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lift extends SubsystemBase {
  private static final TalonFX rightLiftMotor = new TalonFX(Constants.Motors.LIFTING_RIGHT);
  private static final TalonFX leftLiftMotor = new TalonFX(Constants.Motors.LIFTING_LEFT);
  private static Lift instance = null;
  public Lift getInstance(){
      if(instance ==null){
          instance = new Lift();
      }
      return instance;
  }
  /**
   * Arms extending are positve, but doing a pull up is negative as arms are
   * being retracted. Use as reference while coding the two movements 
   */
  
  //moves the arms up to reach the chain
  public void raising() { 
    rightLiftMotor.set(-1);
    leftLiftMotor.set(1);
  }

  //moves the robot up in the air
  public void lowering() {
    rightLiftMotor.set(1);
    leftLiftMotor.set(-1);
  }
}
