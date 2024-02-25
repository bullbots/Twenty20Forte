// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.slider;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Slider;

public class SlideSliderToPosition extends Command {

  private int m_position;
  private Slider m_slider;

  /** Creates a new SliderSliderToPosition. */
  public SlideSliderToPosition(Slider slider, int position){
    addRequirements(slider);
    m_position = position;
    m_slider = slider;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //m_slider.moveToPosition(m_position);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return m_slider.isAtPosition();
    return false;
  }
}
