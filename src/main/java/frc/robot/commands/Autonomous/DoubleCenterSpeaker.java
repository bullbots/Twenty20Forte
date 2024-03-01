// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.KillAll;
import frc.robot.commands.SetIntakeFront;
import frc.robot.commands.shooting.ShootInSpeaker;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DoubleCenterSpeaker extends SequentialCommandGroup {
  /** Creates a new CenterSpeaker. */
  public DoubleCenterSpeaker() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new CenterSpeaker());
       // new SetIntakeFront(1, RobotContainer.m_intakeSensor::get));
        // new ParallelDeadlineGroup(new DriveBackward(1.5),
        //     new ParallelDeadlineGroup(new WaitCommand(3.0), new ShootInSpeaker())),
        // new KillAll());
  }
}
