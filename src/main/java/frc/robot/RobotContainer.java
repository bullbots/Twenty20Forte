// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Lifting;
import frc.robot.commands.WindlassDirections;
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
import frc.robot.subsystems.Lifter;

import frc.robot.subsystems.Slider;
import frc.robot.subsystems.Windlass;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final Joystick m_controller = new Joystick(0);
  private final CommandJoystick m_guitarHero = new CommandJoystick(0);
  private final Windlass m_Windlass = new Windlass();
  private final DebouncedDigitalInput m_intakeSensor = new DebouncedDigitalInput(Constants.Sensors.INTAKE_SENSOR);

  private static final DriveTrain m_DriveTrain = new DriveTrain();

  public static final Lifter m_LiftLeft = new Lifter(Constants.Motors.LIFTER_LEFT);
  public static final Lifter m_LiftRight = new Lifter(Constants.Motors.LIFTER_RIGHT);
  public static final DriveTrain m_drivetrain = new DriveTrain();
  public static final Slider m_slide = new Slider();

  public static double setAngle = 0;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    // default driving code
    m_drivetrain.setDefaultCommand(
        new RunCommand(
            () -> {
              /**
               * DEAD_ZONE is the distance from the center of the joystick that still has no
               * robot function,
               * EXPONENT is the ramping effect: Higher Exponent means slower speed of ramping
               */
              final double DEAD_ZONE = .3;
              final double EXPONENT = 2;
              double x = m_controller.getLeftX();
              double y = m_controller.getLeftY();
              double z = m_controller.getRightX();
              // mathematical formula for adjusting the axis to a more usable number
              // ternary operators: (boolean) ? conditionIsTrue : conditionIsFalse
              x = (Math.abs(x) >= DEAD_ZONE) ? ((x > 0)
                  ? Math.pow((x - DEAD_ZONE) / (1 - DEAD_ZONE), EXPONENT)
                  : -Math.pow((x + DEAD_ZONE) / (1 - DEAD_ZONE), EXPONENT)) : 0;
              y = (Math.abs(y) >= DEAD_ZONE) ? ((y > 0)
                  ? Math.pow((y - DEAD_ZONE) / (1 - DEAD_ZONE), EXPONENT)
                  : -Math.pow((y + DEAD_ZONE) / (1 - DEAD_ZONE), EXPONENT)) : 0;
              z = (Math.abs(z) >= DEAD_ZONE) ? ((z > 0)
                  ? (z - DEAD_ZONE) / (1 - DEAD_ZONE)
                  : (z + DEAD_ZONE) / (1 - DEAD_ZONE)) : 0;

              m_drivetrain.holonomicDrive(
                  // All numbers are negative, due to the way WPI Motors handle rotation
                  -y,
                  -x,
                  -z,
                  true);
            }, m_drivetrain));
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {

    // Robot Up
    m_guitarHero.axisGreaterThan(1, -0.5).whileTrue(new Lifting(m_LiftLeft, 1));
    // Robot Down
    m_guitarHero.axisLessThan(1, 0.5).whileTrue(new Lifting(m_LiftRight, -1));
    m_driverController.button(9)
        .onTrue(new RunCommand(() -> m_drivetrain.setSpeed((m_drivetrain.maxMetersPerSecond == 10) ? 5 : 10)));
    // Buttons for co-driver moving the slider up and down
    // Slider up

    // m_guitarHero.povDown().whileTrue(m_slide.slide(Mode.DOWN));
    // m_guitarHero.povUp().whileTrue(m_slide.slide(Mode.UP));

    // Bindings for the windlass direction
    m_driverController.povLeft().whileTrue(new WindlassDirections(m_Windlass, -1));
    m_driverController.povRight().whileFalse(new WindlassDirections(m_Windlass, 1));

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
