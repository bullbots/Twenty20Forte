// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stager;
import frc.robot.utils.ControllerVibrate;

public class StageInShooter extends WaitCommand {
  /** Creates a new StageInShooter. */
  
  private Stager m_stager;
  private Shooter m_shooter;
  private boolean m_first;
  private ControllerVibrate m_controllerVibrate;


  public StageInShooter() {
    this(0.5);
  }

  public StageInShooter(double seconds) {
    super(seconds);
    addRequirements(RobotContainer.stager, RobotContainer.shooter);
    m_stager = RobotContainer.stager;
    m_shooter = RobotContainer.shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("StageInShooter initialize");
    if (m_shooter.stagedInShooter){
      System.out.println("StageInShooter already staged canceling");
      cancel();
    }
    else {
      System.out.println("StageInShooter initialize");
      super.initialize();
      RobotContainer.slider.locked = true;
      m_shooter.stageShoot();
      m_first = true;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (super.isFinished()&&m_first) {
      m_first = false;
      m_stager.start(Stager.Mode.HALF_SPEED);
      m_timer.restart();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_stager.stop();
    m_shooter.stop();
    m_shooter.stagedInShooter = true;
    RobotContainer.slider.locked = false;
    // m_controllerVibrate = new ControllerVibrate(RobotContainer.m_controller, ControllerVibrate.NOTE_LOADED);
    // m_controllerVibrate.start();
    // var newCommand = new WaitCommand(1) {
    //   @Override
    //   public void initialize() {
    //       super.initialize();
    //       RobotContainer.m_controller.setRumble(RumbleType.kLeftRumble, 0.5);
    //   }

    //   @Override
    //       public void end(boolean interrupted) {
    //           super.end(interrupted);
    //           RobotContainer.m_controller.setRumble(RumbleType.kLeftRumble, 0.0);
    //       }
    // };
    // newCommand.schedule();
    System.out.println("StageInShooter end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
   
    return  super.isFinished() && RobotContainer.m_intakeSensor.get();
  }
}
