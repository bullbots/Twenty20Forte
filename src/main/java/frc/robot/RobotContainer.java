// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.LeftLift;
import frc.robot.sensors.DebouncedDigitalInput;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Lift;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final Joystick m_controller = new Joystick(0);
  private final CommandJoystick m_guitarHero = new CommandJoystick(0);

  private final DebouncedDigitalInput m_intakeSensor = new DebouncedDigitalInput(Constants.Sensors.INTAKE_SENSOR);

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  Lift m_LiftLeft;
  Lift m_LiftRight;
  DriveTrain m_drivetrain;

  public static double setAngle = 0;
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    //default driving code
    m_drivetrain.setDefaultCommand(
      new RunCommand(
        () -> {
        final double DEAD_ZONE = .3;
        final double EXPONENT = 2;
        double x = m_driverController.getLeftX();
        double y = m_driverController.getLeftY();
        double z = m_driverController.getRightX();
        //mathematical formula for adjusting the axis to a more usable number
        x = Math.pow((x-DEAD_ZONE)/(1-DEAD_ZONE),EXPONENT);
        y = Math.pow((y-DEAD_ZONE)/(1-DEAD_ZONE),EXPONENT);
        z = (z-DEAD_ZONE)/(1-DEAD_ZONE);

        m_drivetrain.holonomicDrive(
          -y,
          -x,
          -z,
          true);
        }, m_drivetrain));
    
    
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Creating left lift arm
    m_LiftLeft = new Lift(Constants.Motors.LIFTING_LEFT);

    // Creating right lift arm
    m_LiftRight = new Lift(Constants.Motors.LIFTING_RIGHT);
    m_drivetrain = DriveTrain.getInstance();

    //Supplier for the Lift Directions command
    //Robot Up
    m_guitarHero.axisGreaterThan(1, -0.5).whileTrue(new LeftLift(m_LiftLeft,1));
    //Robot Down
    m_guitarHero.axisLessThan(1, 0.5).whileTrue(new LeftLift(m_LiftRight,-1));

    
  }
  

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return Autos.exampleAuto(m_exampleSubsystem);
    return null;
  }

  public void periodic() {
    SmartDashboard.putNumber("Intake Sensor", m_intakeSensor.get() ? 1 : 0);
  }
}
