package frc.robot.commands;


import frc.robot.RobotContainer;
import frc.robot.subsystems.BackIntake;
import frc.robot.subsystems.FrontMiddleIntake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stager;

import java.util.function.BooleanSupplier;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.wpilibj2.command.Command;


public class BeanBurrito extends Command {

    BackIntake m_backIntake;
    FrontMiddleIntake m_Intake;
    int m_Direction;
    Stager m_Stager;
    Shooter m_shooter;

    public BeanBurrito(int direction) {
        m_Direction = direction;
        m_Intake = RobotContainer.frontMiddleIntake;
        m_backIntake = RobotContainer.backIntake;
        m_Stager = RobotContainer.stager;
        m_shooter = RobotContainer.shooter;
        addRequirements(m_Intake, m_Stager, m_shooter);
    }

    @Override
    public void initialize() {
        System.out.println("Burritos Away!!!!");
        m_Intake.setDirection(m_Direction);
        m_Intake.start();
        m_backIntake.setDirection(m_Direction);
        m_backIntake.start();
        m_Stager.start(Stager.Mode.BURRITO);
        m_shooter.stageShoot();
        RobotContainer.slider.locked = false;
        
    }

    @Override
    public void end(boolean interrupted) {
        m_Intake.stop();
        m_backIntake.stop();
        m_Stager.stop();
        m_shooter.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
