package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.FrontMiddleIntake;
import frc.robot.subsystems.Stager;
import edu.wpi.first.wpilibj2.command.Command;


public class SetIntakeFront extends Command{

    FrontMiddleIntake m_Intake;
    int m_Direction;
    Stager m_Stager;
    public SetIntakeFront(FrontMiddleIntake intake, Stager stager, int direciton) {
        m_Direction = direciton;
        m_Intake = intake;
        m_Stager = stager;
        addRequirements(m_Intake);
    }

    @Override
    public void initialize(){
        m_Intake.setDirection(m_Direction);
        m_Intake.start();
        System.out.println("front intake at speed:" +m_Direction);
        if(m_Direction==0){
            m_Stager.stop();
        } else{
            m_Stager.start(Stager.Mode.HALF_SPEED);
        }

        System.out.println("back intake initialized");
    }

    @Override
    public boolean isFinished(){
        return true;
    }

}