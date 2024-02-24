// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Lifting;
import frc.robot.commands.slider.SlideSlider;
import frc.robot.sensors.DebouncedDigitalInput;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Slider;
import frc.robot.subsystems.Stager;

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
  public static Slider slider;
  public static Shooter shooter;
  public static Stager stager;

  public static double setAngle = 0;
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    if (Robot.isSimulation()){
    DriverStation.silenceJoystickConnectionWarning(true);
    }
    // Configure the trigger bindings
    configureBindings();

    //default driving code
  //   m_drivetrain.setDefaultCommand(
  //     new RunCommand(
  //       () -> {
  //       final double DEAD_ZONE = .3;
  //       final double EXPONENT = 2;
  //       double x = m_driverController.getLeftX();
  //       double y = m_driverController.getLeftY();
  //       double z = m_driverController.getRightX();
  //       //mathematical formula for adjusting the axis to a more usable number
  //       x = (Math.abs(x) >= DEAD_ZONE) ? (
  //         (x > 0)
  //          ? Math.pow((x-DEAD_ZONE)/(1-DEAD_ZONE),EXPONENT)
  //          : -Math.pow((x+DEAD_ZONE)/(1-DEAD_ZONE),EXPONENT)
  //       ) : 0;
  //       y = (Math.abs(y) >= DEAD_ZONE) ? (
  //         (y > 0)
  //          ? Math.pow((y-DEAD_ZONE)/(1-DEAD_ZONE),EXPONENT)
  //          : -Math.pow((y+DEAD_ZONE)/(1-DEAD_ZONE),EXPONENT)
  //       ) : 0;
  //       z = (Math.abs(z) >= DEAD_ZONE) ? (
  //         (z > 0)
  //         ? (z-DEAD_ZONE)/(1-DEAD_ZONE)
  //         : (z+DEAD_ZONE)/(1-DEAD_ZONE)
  //         ) : 0;

  //       m_drivetrain.holonomicDrive(
  //         -y,
  //         -x,
  //         -z,
  //         true);
  //       }, m_drivetrain));
  
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

    slider = new Slider();
    shooter = new Shooter();
    stager = new Stager();

    //Robot Up
    m_guitarHero.axisGreaterThan(1, -0.5).whileTrue(new Lifting(m_LiftLeft,1));
    //Robot Down
    m_guitarHero.axisLessThan(1, 0.5).whileTrue(new Lifting(m_LiftRight,-1));

    // SmartDashboard.putData("Test SlideSliderUp", new SlideSlider(slider, Slider.Mode.UP));


    //Buttons for co-driver moving the slider up and down
    //Slider up

    //m_guitarHero.povDown().whileTrue(new Slide(Down, 180));
    //m_guitarHero.povUp().whileTrue(new Slide(Up, 0));

    // Bindings for shooting into the Speaker
    
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
