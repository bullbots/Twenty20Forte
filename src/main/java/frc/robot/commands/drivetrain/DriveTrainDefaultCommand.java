// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DriveTrain;

public class DriveTrainDefaultCommand extends Command {

  private static DriveTrain m_DriveTrain;
  private static CommandXboxController m_driverController;

  /** Creates a new DriveTrainDefaultCommand. */
  public DriveTrainDefaultCommand(DriveTrain drivetrain, CommandXboxController controller) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
    m_DriveTrain = drivetrain;
    m_driverController = controller;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     final double DEADBAND = .15;
      double x = m_driverController.getLeftX();
      double y = m_driverController.getLeftY();
      double z = m_driverController.getRightX();
      if (Math.abs(x) > DEADBAND) {
        y = MathUtil.applyDeadband(y, DEADBAND*.6);
      } else {
        y = MathUtil.applyDeadband(y, DEADBAND);
      }
        if (Math.abs(y) > DEADBAND) {
        x = MathUtil.applyDeadband(x, DEADBAND*.6);
      } else {
        x = MathUtil.applyDeadband(x, DEADBAND);
      }
      z = MathUtil.applyDeadband(z, DEADBAND);

      m_DriveTrain.holonomicDrive(
          // I may be wrong, but I think all of these should be negative (not z), 
          // since forward y is negative, and on the x axes left is 
          // positive for the robot strafing and twisting.
          // It checks out in the simulator..
          //Z should not be negative, the simulator has reversed turning for some reason
          -y,
          -x,
          z,
          true
      );
  }
}
