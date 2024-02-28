// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class DriveForward extends Command {
  DriveTrain drivetrain;
  int wait;
  final int stop;
  public DriveForward(int feet) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain = RobotContainer.drivetrain;
    this.stop = feet * 24;
  }

  public DriveForward(int[] feet_inches) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain = RobotContainer.drivetrain;
    this.stop = feet_inches[0] * 24 + feet_inches[1] * 2;
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    wait = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.holonomicDrive(1.00, 0, 0, false);
    wait += 1;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return wait >= stop;
  }
}
