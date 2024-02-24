package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Windlass;

private final Joystick m_controller = new Joystick(0);

private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

public class WindlassDirections {

  m_Windlass = new Windlass();

    // Creating left lift arm
    m_Windlass = new Windlass(Constants.Motors.WINDLASS);
}