// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stager;

public class BumpShooter extends WaitCommand {
  private Shooter m_shooter;
  private Stager m_stager;

  /** Creates a new BumpShooter. */
  public BumpShooter(double seconds) {
    super(seconds);
    m_shooter = RobotContainer.shooter;
    m_stager = RobotContainer.stager;
    addRequirements(m_shooter, m_stager);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    super.initialize();
    System.out.println("BumpShooter initialize");
    m_shooter.bumpShooter();
    m_stager.start(Stager.Mode.MAX_SPEED);
    m_shooter.stagedInShooter = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("BumpShooter end");
    m_shooter.stop();
    m_stager.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return super.isFinished();
  }
}
