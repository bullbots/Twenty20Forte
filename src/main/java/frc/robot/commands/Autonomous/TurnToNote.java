// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.drivetrain.TurningRobotFuzzyLogic;

public class TurnToNote extends TurningRobotFuzzyLogic {
  public TurnToNote() {
    super(()->NetworkTableInstance.getDefault().getTable("limelight-limeb").getEntry("tx").getDouble(0));
  }
}
