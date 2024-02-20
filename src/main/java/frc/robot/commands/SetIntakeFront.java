package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.FrontMiddleIntake;
import edu.wpi.first.wpilibj2.command.Command;


public class SetIntakeFront extends Command{

    FrontMiddleIntake m_Intake;
    int m_Direciton;
    public SetIntakeFront(FrontMiddleIntake intake, int direciton) {
        m_Direciton = direciton;
        m_Intake = intake;
        addRequirements(m_Intake);
    }

    @Override
    public void initialize(){
        m_Intake.setDirection(m_Direciton);
        m_Intake.enable();
    }

    @Override
    public boolean isFinished(){
        return true;
    }

}