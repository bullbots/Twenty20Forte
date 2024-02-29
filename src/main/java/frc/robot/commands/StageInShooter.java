// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stager;

public class StageInShooter extends WaitCommand {
  /** Creates a new StageInShooter. */
  
  private Stager m_stager;
  private Shooter m_shooter;

  public StageInShooter() {
    this(0.5);

  }

  public StageInShooter(double seconds) {
    super(seconds);
    addRequirements(RobotContainer.stager, RobotContainer.shooter);
    m_stager = RobotContainer.stager;
    m_shooter = RobotContainer.shooter;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (m_shooter.stagedInShooter){
      cancel();
    }
    else {
      super.initialize();
      m_stager.start(Stager.Mode.MAX_SPEED);
      m_shooter.stageShoot();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_stager.stop();
    m_shooter.stop();
    m_shooter.stagedInShooter = true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
   
    return  super.isFinished() && RobotContainer.m_intakeSensor.get();
  }
}
