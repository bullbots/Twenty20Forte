// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.drivetrain.DriveTrainDefaultCommand;
import frc.robot.sensors.DebouncedDigitalInput;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
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

  private final DebouncedDigitalInput m_intakeSensor = new DebouncedDigitalInput(Constants.Sensors.INTAKE_SENSOR);

  private static final DriveTrain m_DriveTrain = new DriveTrain();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  Lift m_Lift;

  public static double setAngle = 0;
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    // Calling the lift 
    m_Lift = new Lift();

    m_DriveTrain.setDefaultCommand(new DriveTrainDefaultCommand(m_DriveTrain, m_driverController));
    // new RunCommand(
    //     () -> {
    //         // m_driverController.setRumble(RumbleType.kBothRumble, 0.0);
    //         final double DEADBAND = .15;
    //         double x = m_driverController.getLeftX();
    //         double y = m_driverController.getLeftY();
    //         double z = m_driverController.getRightX();
    //         if (Math.abs(x) > DEADBAND) {
    //           y = MathUtil.applyDeadband(y, DEADBAND*.6);
    //         } else {
    //           y = MathUtil.applyDeadband(y, DEADBAND);
    //         }
    //           if (Math.abs(y) > DEADBAND) {
    //           x = MathUtil.applyDeadband(x, DEADBAND*.6);
    //         } else {
    //           x = MathUtil.applyDeadband(x, DEADBAND);
    //         }
    //         z = MathUtil.applyDeadband(z, DEADBAND);

    //         m_DriveTrain.holonomicDrive(
    //             // I may be wrong, but I think all of these should be negative (not z), 
    //             // since forward y is negative, and on the x axes left is 
    //             // positive for the robot strafing and twisting.
    //             // It checks out in the simulator..
    //             //Z should not be negative, the simulator has reversed turning for some reason
    //             -y,
    //             -x,
    //             z,
    //             true);
    //     }, m_DriveTrain));
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
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
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
