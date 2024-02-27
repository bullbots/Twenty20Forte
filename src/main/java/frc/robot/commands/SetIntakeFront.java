package frc.robot.commands;


import frc.robot.subsystems.FrontMiddleIntake;
import frc.robot.subsystems.Stager;

import java.util.function.BooleanSupplier;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.wpilibj2.command.Command;


public class SetIntakeFront extends Command {

    FrontMiddleIntake m_Intake;
    int m_Direction;
    Stager m_Stager;
    BooleanSupplier m_Sensor;

    public SetIntakeFront(FrontMiddleIntake intake, Stager stager, int direction, BooleanSupplier sensor) {
        m_Direction = direction;
        m_Intake = intake;
        m_Stager = stager;
        m_Sensor = sensor;
        addRequirements(m_Intake);
    }

    @Override
    public void initialize() {

        if (m_Sensor.getAsBoolean()){
            System.out.println("note/ring/donut/donote/orange thingy sensed");
            return;
        }

        m_Intake.setDirection(m_Direction);
        m_Intake.start();
        m_Stager.start(Stager.Mode.MAX_SPEED);
        
    }

    @Override
    public void end(boolean interrupted) {
        m_Intake.stop();
        m_Stager.stop();
    }

    @Override
    public boolean isFinished() {
        return m_Sensor.getAsBoolean();
    }

}
