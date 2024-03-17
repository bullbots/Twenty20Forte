package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.KillAll;
import frc.robot.commands.SetIntakeFront;
import frc.robot.commands.drivetrain.TurningRobotFuzzyLogic;
import frc.robot.commands.shooting.ShootInSpeaker;

public class BlueAmpSpeaker extends SequentialCommandGroup{
    public BlueAmpSpeaker() {
        addCommands(
            new WaitCommand(3.0), 
            new ParallelDeadlineGroup(new WaitCommand(3.0), new ShootInSpeaker()), 
            new KillAll(),
            new DriveForward(0.5),
            new TurningRobotFuzzyLogic(55),
            new ParallelCommandGroup(new SetIntakeFront(1, RobotContainer.m_intakeSensor::get), new DriveForward(1.3))
        );
    }
}
