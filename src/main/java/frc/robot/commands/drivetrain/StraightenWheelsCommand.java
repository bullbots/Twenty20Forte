package frc.robot.commands.drivetrain;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;


public class StraightenWheelsCommand extends WaitCommand {

    private final DriveTrain driveTrain;

    public StraightenWheelsCommand(double seconds) {
        super(seconds);
        addRequirements(RobotContainer.drivetrain);
        driveTrain = RobotContainer.drivetrain;
    }

    @Override
    public void initialize() {
        super.initialize();
        System.out.println("StraightenWheelsCommand.initialize");
        var states = new SwerveModuleState[]{
                new SwerveModuleState(0, new Rotation2d()),
                new SwerveModuleState(0, new Rotation2d()),
                new SwerveModuleState(0, new Rotation2d()),
                new SwerveModuleState(0, new Rotation2d())
        };
        driveTrain.setSwerveModuleStates(states);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        driveTrain.stop();
        System.out.println("StraightenWheelsCommand.end");
    }
}
