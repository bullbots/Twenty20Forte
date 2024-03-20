package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class StrafeAndMoveForward extends Command {
    double forward;
    double sideways;
    DriveTrain m_driveTrain;
    boolean x;

    public StrafeAndMoveForward(double sideSpeed, double forwardSpeed, DriveTrain drivetrain) {
        forward = forwardSpeed;
        sideways = sideSpeed;
        m_driveTrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        var forwardMult = (RobotContainer.slider.sliderDown()) ? 1 : -1;
        m_driveTrain.holonomicDrive(forwardMult * forward, sideways, 0, false);
    }

    @Override
    public void end(boolean interrupted) {
        m_driveTrain.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
