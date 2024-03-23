// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

import java.util.function.DoubleSupplier;

public class TurningRobotFuzzyLogic extends Command {
  /** Creates a new TurningRobotFuzzyLogic. */
  private final DoubleSupplier deltaSupplier;
  private final DriveTrain m_drivetrain;
  private static final double HIGHSPEED = 1.0;
  private static final double LOWSPEED = 0.7;
  private static final double angleThreshold = 30;
  private static final double TOLERANCE = 2;

  public TurningRobotFuzzyLogic(double targetAngle) {
    addRequirements(RobotContainer.drivetrain);
    m_drivetrain = RobotContainer.drivetrain;
    deltaSupplier = () -> MathUtil.inputModulus(targetAngle - DriveTrain.sim_gyro.getAngle(),
            -180,
            180);
  }

  public TurningRobotFuzzyLogic(DoubleSupplier deltaSupplier) {
    addRequirements(RobotContainer.drivetrain);
    m_drivetrain = RobotContainer.drivetrain;
    this.deltaSupplier = deltaSupplier;
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("INFO: TurningRobotFuzzyLogic.initialize");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    var delta = deltaSupplier.getAsDouble();
    var speed = 0.0;
    if (Math.abs(delta) > angleThreshold) {
      speed = HIGHSPEED;
    } else {
      speed = LOWSPEED;
    }
    System.out.printf("INFO: Delta %.3f; Speed %.3f; Gyro Angle %.3f %n", delta, speed, DriveTrain.sim_gyro.getAngle());

    SmartDashboard.putNumber("TurningRobotFuzzyLogic Delta", delta);
    SmartDashboard.putNumber("TurningRobotFuzzyLogic Speed", speed);
    SmartDashboard.putNumber("TurningRobotFuzzyLogic Delta", delta);

    m_drivetrain.holonomicDrive(
        // All numbers are negative, due to the way WPI
        // Motors handle rotation
        0,
        0,
        -Math.signum(delta) * speed,
        false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("INFO: TurningRobotFuzzyLogic.end");
    m_drivetrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    var delta = deltaSupplier.getAsDouble();
    return Math.abs(delta) <= TOLERANCE;
  }
}
