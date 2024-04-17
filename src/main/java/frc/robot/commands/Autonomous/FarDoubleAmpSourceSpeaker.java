package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.RobotContainer;
import frc.robot.commands.KillAll;
import frc.robot.commands.SetIntakeFront;
import frc.robot.commands.drivetrain.TurningRobotFuzzyLogic;
import frc.robot.commands.shooting.ShootInSpeaker;

public class FarDoubleAmpSourceSpeaker extends SequentialCommandGroup {

    public enum SpeakerSide {
        BLUE,
        RED,
        AMP,
        SOURCE
    }

    public FarDoubleAmpSourceSpeaker(SpeakerSide ampSide, SpeakerSide sourceSide) {

        var ampTurnSign = (ampSide == SpeakerSide.BLUE) ? 1 : -1;
        var sourceTurnSign = (sourceSide == SpeakerSide.AMP) ? 1 : -1;
        var driveTime = (sourceSide == SpeakerSide.AMP) ? 1 : 2.5;

        addCommands(
                new WaitCommand(1.5),
                new ParallelDeadlineGroup(new WaitCommand(1.5), new ShootInSpeaker()),
                new KillAll(),
                new DriveForward(driveTime),
                new TurningRobotFuzzyLogic(ampTurnSign * sourceTurnSign * 50),
                new ParallelDeadlineGroup(
                        new WaitCommand(6.5),
                        new SetIntakeFront(1, RobotContainer.m_intakeSensor::get),
                        new DriveForward(4.5)),
                new DriveBackward(4.5),
                new TurningRobotFuzzyLogic(ampTurnSign * sourceTurnSign * -50),
                new DriveBackward(driveTime),
                new WaitCommand(0.5),
                new ShootInSpeaker()
        );
    }
}
