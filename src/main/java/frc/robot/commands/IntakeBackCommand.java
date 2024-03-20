package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BackIntake;
import frc.robot.subsystems.FrontMiddleIntake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stager;
import frc.robot.utils.ControllerVibrate;

public class IntakeBackCommand extends Command {

    BackIntake m_backIntake;
    int m_direction;
    FrontMiddleIntake m_frontMiddleIntake;
    Stager m_stager;
    BooleanSupplier m_sensor;
    Shooter m_shooter;
    ControllerVibrate m_controllerVibrate;

    public IntakeBackCommand(int direction, BooleanSupplier sensor) {
        m_backIntake = RobotContainer.backIntake;
        m_frontMiddleIntake = RobotContainer.frontMiddleIntake;
        m_direction = direction;
        m_stager = RobotContainer.stager;
        m_sensor = sensor;
        m_shooter = RobotContainer.shooter;
        m_controllerVibrate = new ControllerVibrate(1);
        addRequirements(m_backIntake, m_frontMiddleIntake, m_stager, m_shooter);
    }

    @Override
    public void initialize() {

        if (m_sensor.getAsBoolean()) {
            System.out.println("note/ring/donut/donote/orange thingy sensed");
            return;
        }
        m_backIntake.setDirection(m_direction);
        m_frontMiddleIntake.setDirection(-m_direction);
        m_shooter.stageShoot();
        m_backIntake.start();
        m_frontMiddleIntake.start();
        m_stager.start(Stager.Mode.MAX_SPEED);
        System.out.println("back intake initialized");
        m_shooter.stagedInShooter = false;
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        m_backIntake.stop();
        m_frontMiddleIntake.stop();
        m_stager.stop();
        m_shooter.stop();
        if(!interrupted) {
            m_controllerVibrate.schedule();
        }
    }

    @Override
    public boolean isFinished() {
        return m_sensor.getAsBoolean();
    }
}
