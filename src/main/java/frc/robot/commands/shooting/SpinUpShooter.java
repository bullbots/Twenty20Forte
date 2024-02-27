// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SpinUpShooter extends WaitCommand {
   Shooter shooter1;

  public SpinUpShooter(Shooter shooter) {
    super(1.0);
    addRequirements(shooter);
    shooter1 = shooter;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter1.set(0.5);
  }

  @Override
  public void end(boolean interrupted) {
      shooter1.stop();
  }
}
