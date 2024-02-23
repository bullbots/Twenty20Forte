// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Slide;

public class MoveSlide extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Slide m_slideSubsystem;
  
  private int m_direction;

  /**
   * Creates a new ExampleCommand.
   *
   * @param Shooter The subsystem used by this command.
   * @return 
   */
  public Sliding(Slide slide, int direction) {
    m_direction = direction;
    m_slideSubsystem = slide;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(slide);
    //enabled
    m_slideSubsystem.setDirection(Down);
    m_slideSubsystem.enable();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_slideSubsystem.setDirection(m_direction);
    m_slideSubsystem.enable();
  }
    
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_slideSubsystem.disable();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
