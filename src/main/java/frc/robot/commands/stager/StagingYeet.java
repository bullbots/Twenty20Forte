// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.stager;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Stager;

public class StagingYeet extends InstantCommand {
  /** Creates a new StagingYeet. */
  Stager m_stager;
  public StagingYeet(Stager stager) {
    addRequirements(stager);
    m_stager = stager;
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_stager.start(Stager.Mode.MAX_SPEED);
    System.out.println("StagingYeetInitialize");
  }

  @Override
  public void end(boolean interrupted) {
      System.out.println("StagingYeetEnd interuppted:" + interrupted);
      m_stager.stop();
  }
  @Override
  public boolean isFinished() {
      return false;
  }

}
