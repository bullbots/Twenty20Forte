package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.RobotContainer;
import frc.robot.commands.KillAll;
import frc.robot.commands.SetIntakeFront;
import frc.robot.commands.drivetrain.TurningRobotFuzzyLogic;
import frc.robot.commands.shooting.ShootInSpeaker;

public class AmpSourceSpeaker extends SequentialCommandGroup {

    public enum SpeakerSide {
        BLUE,
        RED,
        AMP,
        SOURCE
    }

    public AmpSourceSpeaker(SpeakerSide ampSide, SpeakerSide sourceSide) {

        var ampTurnSign = (ampSide == SpeakerSide.BLUE) ? 1 : -1;
        var sourceTurnSign = (sourceSide == SpeakerSide.AMP) ? 1 : -1;
        var driveTime = (sourceSide == SpeakerSide.AMP) ? 0.5 : 1.0;

        addCommands(
                new WaitCommand(3.0),
                new ParallelDeadlineGroup(new WaitCommand(3.0), new ShootInSpeaker()),
                new KillAll(),
                new DriveForward(driveTime),
                new TurningRobotFuzzyLogic(ampTurnSign * sourceTurnSign * 50),
                new ParallelRaceGroup(
                        new SetIntakeFront(1, RobotContainer.m_intakeSensor::get),
                        new DriveForward(1.0))
        );
    }
}
