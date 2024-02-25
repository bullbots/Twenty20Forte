// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.IntakeBackCommand;
import frc.robot.commands.Lifting;
import frc.robot.commands.SetIntakeFront;
import frc.robot.commands.WindlassDirections;
import frc.robot.sensors.DebouncedDigitalInput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.BackIntake;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.FrontMiddleIntake;
import frc.robot.subsystems.Lifter;

import frc.robot.subsystems.Slider;
import frc.robot.subsystems.Stager;
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
    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController m_driverController =
            new CommandXboxController(OperatorConstants.kDriverControllerPort);
    private final CommandJoystick m_guitarHero = new CommandJoystick(OperatorConstants.kCopilotControllerPort);


    // Stand-alone sensors
    private final DebouncedDigitalInput m_intakeSensor = new DebouncedDigitalInput(Constants.Sensors.INTAKE_SENSOR);


    // The robot's subsystems...
    private static final DriveTrain m_driveTrain = new DriveTrain();
    private final Windlass m_Windlass = new Windlass();
    public static final Lifter m_liftLeft = new Lifter(Constants.Motors.LIFTER_LEFT);
    public static final Lifter m_liftRight = new Lifter(Constants.Motors.LIFTER_RIGHT);
    public static final Slider m_slide = new Slider();
    public static final FrontMiddleIntake m_frontMiddleIntake = new FrontMiddleIntake();
    public static final BackIntake m_backIntake = new BackIntake();
    public static final Stager m_stager = new Stager();
    public static double setAngle = 0;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();

        // default driving code
        m_driveTrain.setDefaultCommand(
                new RunCommand(
                        () -> {
                            /**
                             * DEAD_ZONE is the distance from the center of the joystick that still has no
                             * robot function,
                             * EXPONENT is the ramping effect: Higher Exponent means slower speed of ramping
                             */
                            final double DEAD_ZONE = .3;
                            final double EXPONENT = 2;
                            double x = m_driverController.getLeftX();
                            double y = m_driverController.getLeftY();
                            double z = m_driverController.getRightX();
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

                            m_driveTrain.holonomicDrive(
                                    // All numbers are negative, due to the way WPI Motors handle rotation
                                    -y,
                                    -x,
                                    -z,
                                    true);
                        }, m_driveTrain));
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
        // Driver controls

        m_driverController.button(9).onTrue(new RunCommand(() ->
                m_driveTrain.setSpeed((DriveTrain.maxMetersPerSecond == 10) ? 5 : 10)));

        //Bindings for the windlass direction
        m_driverController.povLeft().whileTrue(new WindlassDirections(m_Windlass, -1));
        m_driverController.povRight().whileFalse(new WindlassDirections(m_Windlass, 1));

        // Copilot controls

        //Robot Up
        m_guitarHero.axisGreaterThan(1, -0.5).whileTrue(new Lifting(m_liftLeft, 1));
        //Robot Down
        m_guitarHero.axisLessThan(1, 0.5).whileTrue(new Lifting(m_liftRight, -1));

        //Buttons for co-driver moving the slider up and down
        //Slider up
        m_guitarHero.button(10).onTrue(new IntakeBackCommand(m_backIntake, m_frontMiddleIntake, m_stager, 1));
        m_guitarHero.button(10).onFalse(new IntakeBackCommand(m_backIntake, m_frontMiddleIntake, m_stager, 0));
        m_guitarHero.button(9).onTrue(new SetIntakeFront(m_frontMiddleIntake, m_stager, 1));
        m_guitarHero.button(9).onFalse(new SetIntakeFront(m_frontMiddleIntake, m_stager, 0));
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
