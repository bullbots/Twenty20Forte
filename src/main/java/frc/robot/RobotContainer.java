// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.BeanBurrito;
import frc.robot.commands.BumpShooter;
import frc.robot.commands.IntakeBackCommand;
import frc.robot.commands.KillAll;
import frc.robot.commands.Lifting;
import frc.robot.commands.Autonomous.Autos;
import frc.robot.commands.slider.SlideSlider;
import frc.robot.commands.slider.SlideSliderToPosition;
import frc.robot.commands.SetIntakeFront;
import frc.robot.commands.StageInShooter;
import frc.robot.commands.StrafeAndMoveForward;
import frc.robot.commands.shooting.ShootInAmp;
import frc.robot.commands.shooting.ShootInSpeaker;
import frc.robot.sensors.DebouncedDigitalInput;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.*;

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
        private final CommandXboxController m_driverController = new CommandXboxController(
                        OperatorConstants.kDriverControllerPort);
        private final CommandJoystick m_guitarHero = new CommandJoystick(OperatorConstants.kCopilotControllerPort);

        // Stand-alone sensors
        public static final DebouncedDigitalInput m_intakeSensor = new DebouncedDigitalInput(
                        Constants.Sensors.INTAKE_SENSOR);

        // The robot's subsystems...
        public static final DriveTrain drivetrain = new DriveTrain();
        // public static final Lifter liftLeft = new
        // Lifter(Constants.Motors.LIFTER_LEFT);
        // public static final Lifter liftRight = new
        // Lifter(Constants.Motors.LIFTER_RIGHT);
        public static final Slider slider = new Slider();
        public static final FrontMiddleIntake frontMiddleIntake = new FrontMiddleIntake();
        public static final BackIntake backIntake = new BackIntake();
        public static final Stager stager = new Stager();
        public static final Shooter shooter = new Shooter();

        // Global robot states
        public static double gyro_angle = 0;

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                // Configure the trigger bindings
                configureBindings();

                Autos.load();

                if (Robot.isSimulation()) {
                        DriverStation.silenceJoystickConnectionWarning(true);
                }

                // default driving code
                drivetrain.setDefaultCommand(
                                new RunCommand(
                                                () -> {
                                                        /**
                                                         * DEAD_ZONE is the distance from the center of the joystick
                                                         * that still has no
                                                         * robot function,
                                                         * EXPONENT is the ramping effect: Higher Exponent means slower
                                                         * speed of ramping
                                                         */
                                                        final double DEAD_ZONE = .1;
                                                        final double EXPONENT = 2;
                                                        double x = m_driverController.getLeftX();
                                                        double y = m_driverController.getLeftY();
                                                        double z = m_driverController.getRightX();
                                                        // mathematical formula for adjusting the axis to a more usable
                                                        // number
                                                        // ternary operators: (boolean) ? conditionIsTrue :
                                                        // conditionIsFalse
                                                        if (Math.abs(x) < DEAD_ZONE && Math.abs(y) < DEAD_ZONE) {
                                                                x = 0;
                                                                y = 0;
                                                        }
                                                        z = (Math.abs(z) >= DEAD_ZONE) ? ((z > 0)
                                                                        ? (z - DEAD_ZONE) / (1 - DEAD_ZONE)
                                                                        : (z + DEAD_ZONE) / (1 - DEAD_ZONE)) : 0;

                                                        drivetrain.holonomicDrive(
                                                                        // All numbers are negative, due to the way WPI
                                                                        // Motors handle rotation
                                                                        y,
                                                                        x,
                                                                        -z,
                                                                        true);
                                                }, drivetrain));
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
                m_driverController.rightTrigger(0.5).whileTrue(new ShootInSpeaker());
                m_driverController.rightTrigger(0.5).onFalse(new KillAll());
                m_driverController.leftTrigger(0.5).whileTrue(new ShootInAmp());
                // m_driverController.a().onTrue(new SlideSliderToPosition(slider, 1,
                // slider::isAtPosition));
                // m_driverController.b().onTrue(new SlideSliderToPosition(slider, -120,
                // slider::isAtPosition));
                m_driverController.leftStick().onTrue(new RunCommand(
                                () -> drivetrain.setMaxSpeed((DriveTrain.maxMetersPerSecond == 10) ? 5 : 10)));

                // Reset Gyro
                m_driverController.start().onTrue(new InstantCommand() {
                        @Override
                        public void initialize() {
                                drivetrain.resetGyro();
                                System.out.println("Resetting Gyro");
                        }
                });


                // Copilot controls

                m_driverController.povUp().whileTrue(new SlideSlider(slider, Slider.Mode.UP));
                m_driverController.povDown().whileTrue(new SlideSlider(slider, Slider.Mode.DOWN));

                // Robot Up
                // m_guitarHero.axisLessThan(1, -0.5).whileTrue(new Lifting(liftLeft, liftRight,
                // 1));
                // Robot Down
                // m_guitarHero.axisGreaterThan(1, 0.5).whileTrue(new Lifting(liftLeft,
                // liftRight, -1));

                // Buttons for co-driver moving the slider up and down
                m_guitarHero.povDown().whileTrue(new SlideSlider(slider, Slider.Mode.DOWN));
                m_guitarHero.povUp().whileTrue(new SlideSlider(slider, Slider.Mode.UP));

                m_guitarHero.button(1).onTrue(new SlideSliderToPosition(slider, 0.2, slider::isAtPosition));
                m_guitarHero.button(2).onTrue(new SlideSliderToPosition(slider, 9.3, slider::isAtPosition));

                m_guitarHero.button(7).onTrue(new StageInShooter());

                m_guitarHero.button(10)
                                .whileTrue(new SequentialCommandGroup(
                                                new SlideSliderToPosition(slider, 1, slider::isAtPosition),
                                                new IntakeBackCommand(1, m_intakeSensor::get)));
                m_guitarHero.button(9)
                                .whileTrue(new SequentialCommandGroup(
                                                new SlideSliderToPosition(slider, 1, slider::isAtPosition),
                                                new SetIntakeFront(1, m_intakeSensor::get)));

                // Bump notes into shooter
                m_guitarHero.button(3).onTrue(new BumpShooter(0.15));
                // Burrito shoots the notes out so they can't get stuck
                m_guitarHero.button(4).whileTrue(new BeanBurrito(-1));

               
        }

        /**
         * Use this to pass the autonomous command to the main {@link Robot} class.
         *
         * @return the command to run in autonomous
         */
        public Command getAutonomousCommand() {
                // An example command will be run in autonomous
                System.out.println("Found Autos!");
                return Autos.getSelected();
        }

        public void periodic() {
                SmartDashboard.putNumber("Intake Sensor", m_intakeSensor.get() ? 1 : 0);
        }
}
