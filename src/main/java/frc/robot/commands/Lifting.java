// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lifter;

public class Lifting extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Lifter m_liftLeft;
  private final Lifter m_liftRight;
  
  private int m_direction;

  /**
   * Creates a new ExampleCommand.
   *
   * @param Lifter The subsystem used by this command.
   * @return 
   */
  public Lifting(Lifter liftLeft, Lifter liftRight, int direction) {
    m_direction = direction;
    m_liftLeft = liftLeft;
    m_liftRight = liftRight;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(liftLeft, liftRight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Lifting initialize");
    m_liftLeft.setDirection(-m_direction);
    m_liftLeft.start();
    m_liftRight.setDirection(m_direction);
    m_liftRight.start();
  }
    
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Lifting end");
    m_liftLeft.stop();
    m_liftRight.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
