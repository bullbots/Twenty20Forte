// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SpinUpShooter extends WaitCommand {
  Shooter shooter;

  public SpinUpShooter(Shooter shooter) {
    super(0.5);
    addRequirements(shooter);
    this.shooter = shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    super.initialize();
    System.out.println("INFO: SpinUpShooter initialize");
    shooter.set(1.0);
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("INFO: SpinUpShooter end");
    // shooter.stop();
  }
}
