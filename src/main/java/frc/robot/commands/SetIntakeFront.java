package frc.robot.commands;


import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.commands.Leds.SolidColor;
import frc.robot.subsystems.FrontMiddleIntake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stager;
import frc.robot.utils.ControllerVibrate;

import java.util.function.BooleanSupplier;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.wpilibj2.command.Command;


public class SetIntakeFront extends Command {

    FrontMiddleIntake m_Intake;
    int m_Direction;
    Stager m_Stager;
    Shooter m_shooter;
    BooleanSupplier m_Sensor;
    ControllerVibrate m_controllerVibrate;
    // SolidColor m_solidColor;

    public SetIntakeFront(int direction, BooleanSupplier sensor) {
        m_Direction = direction;
        m_Intake = RobotContainer.frontMiddleIntake;
        m_Stager = RobotContainer.stager;
        m_shooter = RobotContainer.shooter;
        m_Sensor = sensor;
        m_controllerVibrate = new ControllerVibrate(1);
        // m_solidColor = new SolidColor(RobotContainer.leds, Constants.LED_COUNT, 255, 50, 0);
        addRequirements(m_Intake, m_Stager, m_shooter);
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
        m_shooter.stageShoot();
        m_shooter.stagedInShooter = false;
    }

    @Override
    public void end(boolean interrupted) {
        m_Intake.stop();
        m_Stager.stop();
        m_shooter.stop();
        if (!interrupted) {
            m_controllerVibrate.schedule();
            // m_solidColor.schedule();
        }
    }

    @Override
    public boolean isFinished() {
        return m_Sensor.getAsBoolean();
    }

}
