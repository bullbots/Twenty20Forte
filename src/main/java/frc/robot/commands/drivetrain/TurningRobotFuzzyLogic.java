// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class TurningRobotFuzzyLogic extends Command {
  /** Creates a new TurningRobotFuzzyLogic. */
  private double m_targetAngle;
  private static final double HIGHSPEED = 0.5;
  private static final double LOWSPEED = 0.2;
  private static final double angleThreshold = 30;
  public TurningRobotFuzzyLogic(double targetAngle) {
    addRequirements(RobotContainer.drivetrain);
    m_targetAngle = targetAngle;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.printf("Absolute Value", Math.abs(m_targetAngle - RobotContainer.gyro_angle));
    

    }
    // get the delta between the targetAngle and the gyro angle
    //var delta = 0;
    // if the absolute value is greater than a threshold, then highspeed
    //lower than its lowspeed with a second threshold

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}
  //stop the motor

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    //Check to see if it is in the range :)
  }
}
