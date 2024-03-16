// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class TurningRobotFuzzyLogic extends Command {
  /** Creates a new TurningRobotFuzzyLogic. */
  private double m_targetAngle;
  private final DriveTrain m_drivetrain;
  private static final double HIGHSPEED = 0.5;
  private static final double LOWSPEED = 0.2;
  private static final double angleThreshold = 30;
  private static final double TOLERANCE = 2;

  public TurningRobotFuzzyLogic(double targetAngle) {
    addRequirements(RobotContainer.drivetrain);
    m_targetAngle = targetAngle;
    m_drivetrain = RobotContainer.drivetrain;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    var delta = MathUtil.angleModulus(m_targetAngle - RobotContainer.gyro_angle);
    var speed = 0.0;
    if (Math.abs(delta) > angleThreshold) {
      speed = HIGHSPEED;
    } else {
      speed = LOWSPEED;
    }
    System.out.printf("Delta %s %n", delta);
    System.out.printf("Speed %s %n", speed);

    // m_drivetrain.holonomicDrive(
    //     // All numbers are negative, due to the way WPI
    //     // Motors handle rotation
    //     0,
    //     0,
    //     Math.signum(-delta) * speed,
    //     false);

  }
  // get the delta between the targetAngle and the gyro angle
  // var delta = 0;
  // if the absolute value is greater than a threshold, then highspeed
  // lower than its lowspeed with a second threshold

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //m_drivetrain.stop();
    System.out.println("Info TurningRobotFuzzyLogic end");
  }
  // stop the motor

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    var delta = MathUtil.angleModulus(m_targetAngle - RobotContainer.gyro_angle);
    return Math.abs(delta)<= TOLERANCE;
    // Check to see if it is in the range :)
  }
}
