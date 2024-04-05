// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.Optional;

public final class Autos {
    /**
     * Example static factory for an autonomous command.
     */
    private Autos() {
        throw new UnsupportedOperationException("This is a utility class!");
    }

    private static final SendableChooser<Pair<Command, Command>> commandChooser = new SendableChooser<>();

    public static void load() {
        commandChooser.setDefaultOption("Do Nothing", new Pair<>(
            new DoNothing("chill af"),
            new DoNothing("hot af")));

        commandChooser.addOption("Drive Forward", new Pair<>(
            new DriveForward(2),
            new DriveForward(2)));

        commandChooser.addOption("Just Shoot", new Pair<>(
            new JustShoot(),
            new JustShoot()));

        commandChooser.addOption("Center Speaker", new Pair<>(
            new CenterSpeaker(), 
            new CenterSpeaker()));

        commandChooser.addOption("DoubleCenterSpeaker", new Pair<>(
            new DoubleCenterSpeaker(),
            new DoubleCenterSpeaker()
        ));
        commandChooser.addOption("AmpSpeaker", new Pair<>(
            new AmpSourceSpeaker(AmpSourceSpeaker.SpeakerSide.BLUE, AmpSourceSpeaker.SpeakerSide.AMP),
            new AmpSourceSpeaker(AmpSourceSpeaker.SpeakerSide.RED, AmpSourceSpeaker.SpeakerSide.AMP)
        ));
        commandChooser.addOption("SourceSpeaker", new Pair<>(
                new AmpSourceSpeaker(AmpSourceSpeaker.SpeakerSide.BLUE, AmpSourceSpeaker.SpeakerSide.SOURCE),
                new AmpSourceSpeaker(AmpSourceSpeaker.SpeakerSide.RED, AmpSourceSpeaker.SpeakerSide.SOURCE)
        ));

        SmartDashboard.putData("Command Selected", commandChooser);
    }

    public static Command getSelected() {
        // SmartDashboard.putBoolean("Autonomous Finished", false);
        // return commandChooser.getSelected().andThen(() ->
        // SmartDashboard.putBoolean("Autonomous Finished", true));
        var blueOption = Optional.of(Alliance.Blue);
        if (DriverStation.getAlliance().equals(blueOption)) {
            System.out.println("INFO: Blue side selected");
            return commandChooser.getSelected().getFirst();
        }
        System.out.println("INFO: Red side selected");
        return commandChooser.getSelected().getSecond();
    }
}
