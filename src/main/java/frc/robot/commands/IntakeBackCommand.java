package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BackIntake;
import frc.robot.subsystems.FrontMiddleIntake;
import frc.robot.subsystems.Stager;


public class IntakeBackCommand extends Command {

    BackIntake m_BackIntake;
    int m_Direction;
    FrontMiddleIntake m_FrontMiddleIntake;
    Stager m_Stager;
    BooleanSupplier m_Sensor;

    public IntakeBackCommand(BackIntake backIntake, FrontMiddleIntake frontMiddleIntake, Stager stager, int direction, BooleanSupplier sensor) {
        m_BackIntake = backIntake;
        m_FrontMiddleIntake = frontMiddleIntake;
        m_Direction = direction;
        m_Stager = stager;
        m_Sensor = sensor;
        addRequirements(m_BackIntake);
        addRequirements(m_FrontMiddleIntake);
    }

    @Override
    public void initialize() {

        if (m_Sensor.getAsBoolean()){
            System.out.println("note/ring/donut/donote/orange thingy sensed");
            return;
        }
        m_BackIntake.setDirection(m_Direction);
        m_FrontMiddleIntake.setDirection(-m_Direction);

        m_BackIntake.start();
        m_FrontMiddleIntake.start();
        m_Stager.start(Stager.Mode.MAX_SPEED);
        System.out.println("back intake initialized");

    }

    @Override
    public void execute(){

    }

    @Override
    public void end(boolean interrupted) {
        m_BackIntake.stop();
        m_FrontMiddleIntake.stop();
        m_Stager.stop();
    }

    @Override
    public boolean isFinished() {
        return m_Sensor.getAsBoolean();
    }
}

