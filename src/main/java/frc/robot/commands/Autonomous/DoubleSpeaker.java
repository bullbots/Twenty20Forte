// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.SetIntakeFront;
import frc.robot.commands.shooting.ShootInSpeaker;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DoubleSpeaker extends SequentialCommandGroup {
  /** Creates a new DoubleSpeaker. */
  public DoubleSpeaker() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ParallelDeadlineGroup(new WaitCommand(1),new ShootInSpeaker()),
      new DriveForward(0.5),
      new TurnToNote(),
      new ParallelDeadlineGroup(new SetIntakeFront(1, RobotContainer.m_intakeSensor::get), new DriveForward(1.5)),
      new DriveBackward(1.5),
      new TurnToSpeaker(),
      new DriveBackward(0.5),
      new ParallelDeadlineGroup(new WaitCommand(1),new ShootInSpeaker())
    );
  }
}
