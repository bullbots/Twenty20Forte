// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.slider.SlideSliderToPosition;
import frc.robot.commands.stager.StagingYeet;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootInSpeaker extends SequentialCommandGroup {
  /** Creates a new ShootInSpeaker2. */
  public ShootInSpeaker() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new ParallelCommandGroup(new SlideSliderToPosition(RobotContainer.slider, 100),
                                         new SpinUpShooter(RobotContainer.shooter)),
                                         new StagingYeet(RobotContainer.stager){
                                          @Override
                                          public void end(boolean interrupted) {
                                              super.end(interrupted);
                                              RobotContainer.slider.stop();
                                              RobotContainer.shooter.stop();
                                              RobotContainer.stager.stop();
                                          }
                                         });
  }
}