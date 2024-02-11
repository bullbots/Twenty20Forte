// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lift extends SubsystemBase {
  private static final TalonFX rightLiftMotor = new TalonFX(Constants.Motor.LIFTING_RIGHT);
  private static final TalonFX leftLiftMotor = new TalonFX(Constants.Motor.LIFTING_LEFT);

  /*Arms extending are positve, but doing a pull up is negative as arms are
being retracted. Use as reference while coding the two movements */
//moves the arms up to reach the chain
public void raising(){
  rightLiftMotor.set(-1);
  leftLiftMotor.set(1);
}
//moves the robot up in the air
  public void lowering() {
    rightLiftMotor.set(-1);
    leftLiftMotor.set(1);
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic(){
    super.periodic();
  }

  @Override
  public void simulationPeriodic() {
    //This method will be called once per scheduler run during simulation
  }
}
