// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public final class Autos {
  /** Example static factory for an autonomous command. */
  // public static Command exampleAuto(ExampleSubsystem subsystem) {
  //   return Commands.sequence(subsystem.exampleMethodCommand(), new ExampleCommand(subsystem));
  // }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
  
  private static SendableChooser<Pair<Command,Command>> commandChooser = new SendableChooser<>();

  public static void load(){
    System.out.println(DriveTrain.getInstance().getPose2d());
    commandChooser.setDefaultOption("Testing", new Pair<Command,Command>(
      new DriveToPos(DriveTrain.getInstance(), () -> new Pose2d(-1,0,new Rotation2d(0)), () -> false),
      new DriveToPos(DriveTrain.getInstance(), () -> new Pose2d(1,0,new Rotation2d(0)), () -> false)));

    SmartDashboard.putData("Command Selected", commandChooser);
  }

  public static Command getSelected() {
    // SmartDashboard.putBoolean("Autonomous Finished", false);
    // return commandChooser.getSelected().andThen(() -> SmartDashboard.putBoolean("Autonomous Finished", true));
    if (DriverStation.getAlliance().equals(Alliance.Blue)) {
        return commandChooser.getSelected().getFirst();
    }
    return commandChooser.getSelected().getSecond();
  }
}
