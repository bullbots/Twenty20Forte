// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.stager;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Stager;

public class StagingYeet extends InstantCommand {
  /** Creates a new StagingYeet. */
  public StagingYeet(Stager stager) {
    addRequirements(stager);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

}
