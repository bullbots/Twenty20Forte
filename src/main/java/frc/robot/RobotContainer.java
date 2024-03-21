// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.CANifier.PWMChannel;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.BeanBurrito;
import frc.robot.commands.BumpShooter;
import frc.robot.commands.IntakeBackCommand;
import frc.robot.commands.KillAll;
import frc.robot.commands.Autonomous.Autos;
import frc.robot.commands.drivetrain.TurnTo;
import frc.robot.commands.drivetrain.TurningRobotFuzzyLogic;
import frc.robot.commands.slider.SlideSlider;
import frc.robot.commands.slider.SlideSliderToPosition;
import frc.robot.commands.SetIntakeFront;
import frc.robot.commands.StageInShooter;
import frc.robot.commands.StrafeAndMoveForward;
import frc.robot.commands.Autonomous.Autos;
import frc.robot.commands.shooting.ShootInAmp;
import frc.robot.commands.shooting.ShootInSpeaker;
import frc.robot.commands.slider.SlideSlider;
import frc.robot.commands.slider.SlideSliderToPosition;
import frc.robot.sensors.DebouncedDigitalInput;
import frc.robot.subsystems.BackIntake;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.FrontMiddleIntake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Slider;
import frc.robot.subsystems.Stager;
import frc.robot.utils.ControllerVibrate;
import frc.robot.utils.FieldOrientation;
import frc.robot.utils.SwapPipeline;

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
    public static final CommandXboxController m_driverController = new CommandXboxController(
            OperatorConstants.kDriverControllerPort);
    private final CommandJoystick m_guitarHero = new CommandJoystick(OperatorConstants.kCopilotControllerPort);
    public static boolean drivingTo = false;
    public static double targetAngle = 0;
    public static AddressableLED led = new AddressableLED(0);
    public static AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(40);


    // Stand-alone sensors
    public static final DebouncedDigitalInput m_intakeSensor = new DebouncedDigitalInput(
            Constants.Sensors.INTAKE_SENSOR);

    // The robot's subsystems...
    public static final DriveTrain drivetrain = new DriveTrain();
    public static final Slider slider = new Slider();
    public static final FrontMiddleIntake frontMiddleIntake = new FrontMiddleIntake();
    public static final BackIntake backIntake = new BackIntake();
    public static final Stager stager = new Stager();
    public static final Shooter shooter = new Shooter();
    private static final FieldOrientation fieldOrientation = new FieldOrientation();

    // Global robot states
    public static double gyro_angle = 0;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();

        //led stuff
        led.setLength(ledBuffer.getLength());

        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, 120, 0, 0);
        }

        led.setData(ledBuffer);

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
                            if (RobotContainer.drivingTo) {
                                z = drivetrain.rotateFuzzyLogic(targetAngle);
                            }
                            drivetrain.holonomicDrive(
                                    // All numbers are negative, due to the way WPI
                                    // Motors handle rotation
                                    -y,
                                    -x,
                                    -z,
                                    fieldOrientation.isFieldRelative());
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

        m_driverController.back().onTrue(Commands.runOnce(() -> {
            fieldOrientation.toggleOrientation();
            System.out.println("Field relative set to: " + fieldOrientation.isFieldRelative());
        }));

        m_driverController.rightBumper().whileTrue(new StrafeAndMoveForward(0.4, -0.1, drivetrain));
        m_driverController.leftBumper().whileTrue(new StrafeAndMoveForward(-0.4, -0.1, drivetrain));

        m_driverController.leftStick().onTrue(new RunCommand(
                () -> drivetrain.setMaxSpeed((DriveTrain.maxMetersPerSecond == 10) ? 5 : 10)));

        // Reset Gyro
        m_driverController.start().onTrue(new InstantCommand(() -> {
            drivetrain.resetGyro();
            System.out.println("Resetting Gyro");
        }));

        m_driverController.x().onTrue(new TurnTo(90));
        m_driverController.b().onTrue(new TurnTo(0));
        m_driverController.y().onTrue(new TurnTo(180));
        m_driverController.a().onTrue(new TurnTo(-90));
        m_driverController.povDown().onTrue(new TurningRobotFuzzyLogic(() ->
                NetworkTableInstance.getDefault()
                        .getTable("limelight-limeb").getEntry("tx").getDouble(0)
        ));
        m_driverController.povUp().onTrue(new SwapPipeline());
        // Copilot controls

        //m_driverController.povUp().whileTrue(new SlideSlider(slider, Slider.Mode.UP));
        //m_driverController.povDown().whileTrue(new SlideSlider(slider, Slider.Mode.DOWN));


        // Buttons for co-driver moving the slider up and down
        m_guitarHero.button(1).whileTrue(new SlideSlider(slider, Slider.Mode.DOWN));
        m_guitarHero.button(2).whileTrue(new SlideSlider(slider, Slider.Mode.UP));

        m_guitarHero.povDown().onTrue(new SlideSliderToPosition(slider, Slider.DOWN_POS, slider::isAtPosition));
        m_guitarHero.povUp().onTrue(new SlideSliderToPosition(slider, Slider.UP_POS, slider::isAtPosition));

        m_guitarHero.button(7).onTrue(new StageInShooter());

        m_guitarHero.button(10)
                .whileTrue(new SequentialCommandGroup(
                        new SlideSliderToPosition(slider, Slider.DOWN_POS, slider::isAtPosition),
                        new IntakeBackCommand(1, m_intakeSensor::get)));
        m_guitarHero.button(9)
                .whileTrue(new SequentialCommandGroup(
                        new SlideSliderToPosition(slider, Slider.DOWN_POS, slider::isAtPosition),
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
