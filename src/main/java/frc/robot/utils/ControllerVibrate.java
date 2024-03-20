package frc.robot.utils;

import java.time.Instant;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

public class ControllerVibrate extends WaitCommand {
    public ControllerVibrate(double seconds) {
        super(seconds);
    }

    @Override
    public void initialize() {
        super.initialize();
        RobotContainer.m_driverController.getHID().setRumble(RumbleType.kLeftRumble, 0.5);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        RobotContainer.m_driverController.getHID().setRumble(RumbleType.kLeftRumble, 0.0);
    }
}
