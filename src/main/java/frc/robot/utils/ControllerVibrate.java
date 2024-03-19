package frc.robot.utils;

import java.util.TimerTask;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

public class ControllerVibrate extends WaitCommand{
    public ControllerVibrate(double seconds) {
        super(seconds);
    }
    /*
    private XboxController m_controller;
    private int m_mode;
    public static final int NOTE_LOADED = 1;
    public ControllerVibrate(XboxController controller, int mode) {
            m_controller = controller;
            m_mode = mode;
        }

    public class RoboTimerTask extends TimerTask {

        @Override 
        public void run() {
        m_controller.setRumble(RumbleType.kLeftRumble, 0.0);
        }
    }

    public void start() {
        m_controller.setRumble(RumbleType.kLeftRumble, 0.5);
        TimerTask timertask = new RoboTimerTask();
        java.util.Timer robotTimer = new java.util.Timer(true);
        robotTimer.schedule(timertask, 1000);
    */
   @Override
      public void initialize() {
          super.initialize();
          RobotContainer.m_driverController.getHID().setRumble(RumbleType.kLeftRumble, 0.5);
      }

    @Override
    public void end(boolean interrupted) {
            super.end(interrupted);
            RobotContainer.m_driverController.getHID().setRumble(RumbleType.kLeftRumble, 0.0);
    
    };
}

