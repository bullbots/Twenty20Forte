package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BackIntake;
import frc.robot.subsystems.FrontMiddleIntake;


public class IntakeBackCommand  extends Command{

    BackIntake m_BackIntake;
    int m_Direction;
    FrontMiddleIntake m_FrontMiddleIntake;

    public IntakeBackCommand(BackIntake backIntake, FrontMiddleIntake frontMiddleIntake, int direction){
        m_BackIntake = backIntake;
        m_FrontMiddleIntake = frontMiddleIntake;
        m_Direction = direction;
        
    }
    
    @Override
    public void initialize(){

        // if(/*check whether or not the sensor is sensed here*/){
        //     end(true);
        // }
        m_BackIntake.setDirection(m_Direction);
        m_FrontMiddleIntake.setDirection(-m_Direction);
        m_BackIntake.enable();
        m_FrontMiddleIntake.enable();
    }
    
    
    @Override
    public boolean isFinished(){
        return true;
    }
}

