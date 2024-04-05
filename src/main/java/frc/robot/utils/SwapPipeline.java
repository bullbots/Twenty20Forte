// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.InstantCommand;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SwapPipeline extends InstantCommand {
  private double pipeline = 0.0;
  public SwapPipeline() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pipeline = (pipeline == 0.0) ? 9.0 : 0.0;
    NetworkTableInstance.getDefault().getTable("limelight-limea").getEntry("pipeline").setDouble(pipeline);
  }
  
  @Override
  public boolean runsWhenDisabled() {
      return true;
  }
}
