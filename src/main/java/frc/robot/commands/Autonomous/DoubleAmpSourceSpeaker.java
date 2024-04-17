package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.RobotContainer;
import frc.robot.commands.KillAll;
import frc.robot.commands.SetIntakeFront;
import frc.robot.commands.drivetrain.TurningRobotFuzzyLogic;
import frc.robot.commands.shooting.ShootInSpeaker;

public class DoubleAmpSourceSpeaker extends SequentialCommandGroup {

    public enum SpeakerSide {
        BLUE,
        RED,
        AMP,
        SOURCE
    }

    public DoubleAmpSourceSpeaker(SpeakerSide ampSide, SpeakerSide sourceSide) {

        var ampTurnSign = (ampSide == SpeakerSide.BLUE) ? 1 : -1;
        var sourceTurnSign = (sourceSide == SpeakerSide.AMP) ? 1 : -1;
        var driveTime = 0.5;

        addCommands(
                new WaitCommand(3.0),
                new ParallelDeadlineGroup(new WaitCommand(3.0), new ShootInSpeaker()),
                new KillAll(),
                new DriveForward(driveTime),
                new TurningRobotFuzzyLogic(ampTurnSign * sourceTurnSign * 50),
                new ParallelDeadlineGroup(
                        new WaitCommand(3.3),
                        new SetIntakeFront(1, RobotContainer.m_intakeSensor::get),
                        new DriveForward(1.3)),
                new DriveBackward(1.3),
                new TurningRobotFuzzyLogic(ampTurnSign * sourceTurnSign * -50),
                new DriveBackward(driveTime),
                new WaitCommand(0.5),
                new ShootInSpeaker()
        );
    }
}
