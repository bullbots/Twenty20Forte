// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class LoadInShooter extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_shooterSubsystem;
  
  private int m_direction;

  /**
   * Creates a new ExampleCommand.
   *
   * @param Shooter The subsystem used by this command.
   * @return 
   */
  public Loading(Shooter shoot, int direction) {
    m_direction = direction;
    m_shooterSubsystem = shoot;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shoot);
    //Robot arms retracting in
    m_shooterSubsystem.start();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooterSubsystem.setDirection(m_direction);
    m_shooterSubsystem.start();
  }
    
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooterSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
