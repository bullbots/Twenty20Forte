// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class Lifting extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Lift m_liftSubsystem;
  
  private int m_direction;

  /**
   * Creates a new ExampleCommand.
   *
   * @param Lift The subsystem used by this command.
   * @return 
   */
  public Lifting(Lift lift, int direction) {
    m_direction = direction;
    m_liftSubsystem = lift;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(lift);
    //Robot arms retracting in
    m_liftSubsystem.setDirection(-1);
    m_liftSubsystem.enable();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_liftSubsystem.setDirection(m_direction);
    m_liftSubsystem.enable();
  }
    
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_liftSubsystem.disable();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
