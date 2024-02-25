// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands.slider;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Slider;

public class SlideSlider extends Command {

  private final Slider m_sliderSubsystem;

  private Slider.Mode m_direction;


  public SlideSlider(Slider slider, Slider.Mode direction) {
    m_direction = direction;
    m_sliderSubsystem = slider;
    addRequirements(slider);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("INFO: SlideSlider initialize");
    m_sliderSubsystem.slide(Slider.Mode.UP);
  }
    
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_sliderSubsystem.slide(m_direction);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_sliderSubsystem.stop();
    System.out.println("INFO: SlideSlider end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
