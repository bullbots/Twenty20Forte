// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;
public class DriveToPos extends Command {
  private DriveTrain drivetrain;
  private Supplier<Pose2d> targetPosSupplier;
  private Supplier<Boolean> interrupt;
  private Pose2d targetPos;

  private PIDController controlX, controlY;
  private ProfiledPIDController controlA;
  /** Creates a new DriveToPos. */
  public DriveToPos(DriveTrain drivetrain, Supplier<Pose2d> posSupplier, Supplier<Boolean> interrupt) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
    this.drivetrain = drivetrain;
    this.targetPosSupplier = posSupplier;
    this.targetPos = new Pose2d();
    this.interrupt = interrupt;
    controlX = DriveTrain.getTunedTranslationalPIDController();
    controlY = DriveTrain.getTunedTranslationalPIDController();
    controlA = DriveTrain.getTunedRotationalPIDController();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    controlA.reset(drivetrain.getPose2d().getRotation().getRadians());
    controlX.reset();
    controlY.reset();
    targetPos = targetPosSupplier.get();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    final double MAX = 2;
    final double MIN = -2;
    if (targetPos == null) return;
    Pose2d currentPos = drivetrain.getPose2d();
    double dx = controlX.calculate(currentPos.getX(),targetPos.getX());
    double dy = controlY.calculate(currentPos.getY(),targetPos.getY());
    double da = controlA.calculate(currentPos.getRotation().getRadians(), targetPos.getRotation().getRadians());
    dx = (dx > MAX) ? MAX : (dx < MIN) ? MIN : dx;
    dy = (dy > MAX) ? MAX : (dy < MIN) ? MIN : dy;
    drivetrain.fromChassisSpeeds(ChassisSpeeds.fromFieldRelativeSpeeds(dx, dy, da, currentPos.getRotation()));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }
  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (interrupt.get() || targetPos == null) return true;
    return controlX.atSetpoint() && controlA.atSetpoint() && controlY.atSetpoint();
  }
}
