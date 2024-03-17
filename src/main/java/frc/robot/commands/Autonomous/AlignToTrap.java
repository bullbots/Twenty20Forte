// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;


public class AlignToTrap extends Command {
  /** Creates a new AlignToTrap. */
  private DriveTrain drivetrain;
  private final double TOLERANCE = 0.2;
  public AlignToTrap() {
    drivetrain = RobotContainer.drivetrain;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    NetworkTableInstance.getDefault().getTable("limelight-limea").putValue("pipeline", NetworkTableValue.makeInteger(9));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double correction = NetworkTableInstance.getDefault().getTable("limelight-limea").getEntry("tx").getDouble(0);
    if (correction > 0){
      drivetrain.fromChassisSpeeds(ChassisSpeeds.fromFieldRelativeSpeeds(0, 1, 0, drivetrain.getPose2d().getRotation()));
    } else {
      drivetrain.fromChassisSpeeds(ChassisSpeeds.fromFieldRelativeSpeeds(0, -1, 0, drivetrain.getPose2d().getRotation()));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(NetworkTableInstance.getDefault().getTable("limelight-limea").getEntry("tx").getDouble(0)) < TOLERANCE;
  }
}
