package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BackIntake;
import frc.robot.subsystems.FrontMiddleIntake;
import frc.robot.subsystems.Stager;


public class IntakeBackCommand extends Command {

    BackIntake m_BackIntake;
    int m_Direction;
    FrontMiddleIntake m_FrontMiddleIntake;
    Stager m_Stager;

    public IntakeBackCommand(BackIntake backIntake, FrontMiddleIntake frontMiddleIntake, Stager stager, int direction) {
        m_BackIntake = backIntake;
        m_FrontMiddleIntake = frontMiddleIntake;
        m_Direction = direction;
        m_Stager = stager;

    }

    @Override
    public void initialize() {

        m_BackIntake.setDirection(m_Direction);
        m_FrontMiddleIntake.setDirection(-m_Direction);

        m_BackIntake.start();
        m_FrontMiddleIntake.start();

        if (m_Direction == 0) {
            m_Stager.stop();
        } else {
            m_Stager.start(Stager.Mode.MAX_SPEED);
        }
        System.out.println("back intake initialized");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

