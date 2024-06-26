package frc.robot.subsystems;


import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.CircularBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.drivetrain.*;
import frc.robot.motors.WPI_CANSparkMax;
import frc.robot.sensors.SimNavX;


public class DriveTrain extends SwerveDrivetrain {

    private static final double TRANSLATIONAL_TOLERANCE = .02;
    private static final double ROTATIONAL_TOLERANCE = Math.toRadians(1);
    private boolean hasAppliedOperatorPerspective = false;
    private final CircularBuffer<Boolean> driverStationDisabledBuff = new CircularBuffer<>(2);

    /**
     * Returns a new PID controller that can be used to control the position of the robot chassis in one axis.
     *
     * @return a new {@link PIDController}
     */
    public static PIDController getTunedTranslationalPIDController() {
        PIDController controller = new PIDController(
                3, 0.5, 0
        );
        controller.setTolerance(TRANSLATIONAL_TOLERANCE);
        return controller;
    }

    /**
     * Returns a new PID controller that can be used to control the angle of the robot chassis.
     * The output will be in radians per second, representing the desired angular velocity.
     *
     * @return a new {@link ProfiledPIDController}
     */
    public static ProfiledPIDController getTunedRotationalPIDController() {
        ProfiledPIDController controller = new ProfiledPIDController(
                3, 1, 0,
                new TrapezoidProfile.Constraints(
                        10,
                        100
                )
        );
        controller.enableContinuousInput(0, 2 * Math.PI);
        controller.setTolerance(ROTATIONAL_TOLERANCE);
        return controller;
    }

    /**
     * Returns a new PID controller that can be used to control the angle of the robot chassis.
     * The output will be between -1 and 1, and is meant to be fed to {@link Drivetrain#holonomicDrive(double, double, double, boolean)}.
     *
     * @return a new {@link ProfiledPIDController}
     */
    public static ProfiledPIDController getTunedRotationalPIDControllerForHolonomicDrive() {
        ProfiledPIDController controller = new ProfiledPIDController(
                1.2, 0, 0,
                new TrapezoidProfile.Constraints(2.5, 5)
        );
        controller.enableContinuousInput(0, 2 * Math.PI);
        controller.setTolerance(ROTATIONAL_TOLERANCE);
        return controller;
    }

    public static double maxMetersPerSecond = 10;

    private static final ShuffleboardTab _shuffuleboardTab = Shuffleboard.getTab("Drivetrain");
    public static final DrivetrainConfig _config = new DrivetrainConfig(maxMetersPerSecond, .5, 7, 2, Units.inchesToMeters(2), 6.75, 2048);
    // public static final SimNavX _gyro = new SimNavX(SPI.Port.kMXP);
    public static final SimNavX sim_gyro = new SimNavX(SerialPort.Port.kUSB1);

    private static final TalonFX frontLeftDriveFalcon = new TalonFX(Constants.Drivetrain.FRONT_LEFT_DRIVE_CHANNEL);
    private static final WPI_CANSparkMax frontLeftSteerFalcon = new WPI_CANSparkMax(Constants.Drivetrain.FRONT_LEFT_STEER_CHANNEL, MotorType.kBrushless);
    private static final CANcoder frontLeftEncoder = new CANcoder(Constants.Drivetrain.FRONT_LEFT_CANCODER_CHANNEL);
    private static final SwerveModule frontLeft = SwerveModule.createFromDriveFalconAndSteeringNeo(frontLeftDriveFalcon, frontLeftSteerFalcon, frontLeftEncoder, _config, .1, 0, 0, .8, 0, 0, 1);

    private static final TalonFX frontRightDriveFalcon = new TalonFX(Constants.Drivetrain.FRONT_RIGHT_DRIVE_CHANNEL);
    private static final WPI_CANSparkMax frontRightSteerFalcon = new WPI_CANSparkMax(Constants.Drivetrain.FRONT_RIGHT_STEER_CHANNEL, MotorType.kBrushless);
    private static final CANcoder frontRightEncoder = new CANcoder(Constants.Drivetrain.FRONT_RIGHT_CANCODER_CHANNEL);
    private static final SwerveModule frontRight = SwerveModule.createFromDriveFalconAndSteeringNeo(frontRightDriveFalcon, frontRightSteerFalcon, frontRightEncoder, _config, .1, 0, 0, .8, 0, 0, 3);

    private static final TalonFX backLeftDriveFalcon = new TalonFX(Constants.Drivetrain.BACK_LEFT_DRIVE_CHANNEL);
    private static final WPI_CANSparkMax backLeftSteerFalcon = new WPI_CANSparkMax(Constants.Drivetrain.BACK_LEFT_STEER_CHANNEL, MotorType.kBrushless);
    private static final CANcoder backLeftEncoder = new CANcoder(Constants.Drivetrain.BACK_LEFT_CANCODER_CHANNEL);
    private static final SwerveModule backLeft = SwerveModule.createFromDriveFalconAndSteeringNeo(backLeftDriveFalcon, backLeftSteerFalcon, backLeftEncoder, _config, .1, 0, 0, .8, 0, 0, 2);

    private static final TalonFX backRightDriveFalcon = new TalonFX(Constants.Drivetrain.BACK_RIGHT_DRIVE_CHANNEL);
    private static final WPI_CANSparkMax backRightSteerFalcon = new WPI_CANSparkMax(Constants.Drivetrain.BACK_RIGHT_STEER_CHANNEL, MotorType.kBrushless);
    private static final CANcoder backRightEncoder = new CANcoder(Constants.Drivetrain.BACK_RIGHT_CANCODER_CHANNEL);
    private static final SwerveModule backRight = SwerveModule.createFromDriveFalconAndSteeringNeo(backRightDriveFalcon, backRightSteerFalcon, backRightEncoder, _config, .1, 0, 0, .8, 0, 0, 4);

    final DoubleSubscriber[] moduleSub = new DoubleSubscriber[4];
    double[] prev = new double[4];
    CANcoder[] encoders;

    private final AprilTagOdometryUpdater aprilTagOdometryUpdater = new AprilTagOdometryUpdater();

    double savedX;

    NetworkTable limeNetworkTable = NetworkTableInstance.getDefault().getTable("limelight");

    public DriveTrain() {
        super(_shuffuleboardTab, _config, Units.inchesToMeters(32), Units.inchesToMeters(28), sim_gyro, frontLeft, frontRight, backLeft, backRight);
        encoders = new CANcoder[]{frontLeftEncoder, backLeftEncoder, frontRightEncoder, backRightEncoder};

//        sim_gyro.setAngleAdjustment(0);
        configDriveMotor(frontLeftDriveFalcon);
        configDriveMotor(frontRightDriveFalcon);
        configDriveMotor(backLeftDriveFalcon);
        configDriveMotor(backRightDriveFalcon);
        configSteerMotor(frontLeftSteerFalcon);
        configSteerMotor(frontRightSteerFalcon);
        configSteerMotor(backLeftSteerFalcon);
        configSteerMotor(backRightSteerFalcon);
        configCANCoder(frontLeftEncoder, Constants.Drivetrain.FRONT_LEFT_ENCODER_OFFSET);
        configCANCoder(frontRightEncoder, Constants.Drivetrain.FRONT_RIGHT_ENCODER_OFFSET);
        configCANCoder(backLeftEncoder, Constants.Drivetrain.BACK_LEFT_ENCODER_OFFSET);
        configCANCoder(backRightEncoder, Constants.Drivetrain.BACK_RIGHT_ENCODER_OFFSET);

        // get the default instance of NetworkTables
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        SmartDashboard.putNumber("module1Offset", 0);
        SmartDashboard.putNumber("module2Offset", 0);
        SmartDashboard.putNumber("module3Offset", 0);
        SmartDashboard.putNumber("module4Offset", 0);
        // get the subtable called "datatable"
        NetworkTable datatable = inst.getTable("SmartDashboard");

        // subscribe to the topic in "datatable" called "Y"
        moduleSub[0] = datatable.getDoubleTopic("module1Offset").subscribe(0.0);
        moduleSub[1] = datatable.getDoubleTopic("module2Offset").subscribe(0.0);
        moduleSub[2] = datatable.getDoubleTopic("module3Offset").subscribe(0.0);
        moduleSub[3] = datatable.getDoubleTopic("module4Offset").subscribe(0.0);

//        sim_gyro.reset();

        resetGyro();

        configureShuffleboard();
    }

    private static void configDriveMotor(TalonFX driveMotor) {
        var toConfigure = new TalonFXConfiguration();

        var motorOutputConfig = new MotorOutputConfigs();
        motorOutputConfig.NeutralMode = NeutralModeValue.Brake;

        toConfigure.MotorOutput = motorOutputConfig;

//        var currentLimitsConfig = new CurrentLimitsConfigs();
//        currentLimitsConfig.SupplyCurrentLimit = 1; // Limit to 1 amps
//        currentLimitsConfig.SupplyCurrentThreshold = 4; // If we exceed 4 amps
//        currentLimitsConfig.SupplyTimeThreshold = 1.0; // For at least 1 second
//        currentLimitsConfig.SupplyCurrentLimitEnable = true; // And enable it
//
//        currentLimitsConfig.StatorCurrentLimit = 20; // Limit stator to 20 amps
//        currentLimitsConfig.StatorCurrentLimitEnable = true; // And enable it
//
//        toConfigure.CurrentLimits = currentLimitsConfig;

        driveMotor.getConfigurator().apply(toConfigure);
    }

    private static void configSteerMotor(CANSparkMax steerMotor) {
        steerMotor.restoreFactoryDefaults();
        steerMotor.setIdleMode(IdleMode.kBrake);
        steerMotor.setInverted(true);
    }

    private static void configCANCoder(CANcoder encoder, double encoderOffset) {
        //encoder.configFactoryDefault();
        //encoder.configMagnetOffset(encoderOffset);
    }

    @Override
    public void simulationPeriodic() {
        ChassisSpeeds simSpeeds = kinematics.toChassisSpeeds(
                frontLeft.getDesiredState(),
                frontRight.getDesiredState(),
                backLeft.getDesiredState(),
                backRight.getDesiredState()
        );

        sim_gyro.setRadians(sim_gyro.getRadians() - simSpeeds.omegaRadiansPerSecond * .02);
        Pose2d newPose = poseEstimator.getEstimatedPosition().plus(
                new Transform2d(
                        new Translation2d(
                                simSpeeds.vxMetersPerSecond * .02,
                                simSpeeds.vyMetersPerSecond * .02
                        ),
                        new Rotation2d()
                )
        );

        poseEstimator.resetPosition(sim_gyro.getRotation2d(), getSwerveModulePositions(), newPose);
    }

    public void resetAngle(double deg) {
        poseEstimator.resetPosition(sim_gyro.getRotation2d(), getSwerveModulePositions(), new Pose2d(getPose2d().getTranslation(), Rotation2d.fromDegrees(deg)));
        System.out.println("Reseting Gyro");
    }

    public void moduleXConfiguration() {
        setSwerveModuleStates(new SwerveModuleState[]{
                        new SwerveModuleState(0, Rotation2d.fromDegrees(45)),
                        new SwerveModuleState(0, Rotation2d.fromDegrees(-45)),
                        new SwerveModuleState(0, Rotation2d.fromDegrees(-45)),
                        new SwerveModuleState(0, Rotation2d.fromDegrees(45))
                }
        );
    }

    public void setMaxSpeed(double speed) {
        maxMetersPerSecond = speed;
    }

    public double rotateFuzzyLogic(double target) {
        System.out.println("Target angle: " + target);
        double HIGHSPEED = 1.0;
        double LOWSPEED = 0.7;
        double TOLERANCE = 2;
        double angleThreshold = 30;
        double delta = MathUtil.inputModulus(target - getPose2d().getRotation().getDegrees(),
                -180,
                180);
        System.out.println("Delta: " + delta);
        if (Math.abs(delta) < TOLERANCE) {
            RobotContainer.drivingTo = false;
            return 0;
        } else if (Math.abs(delta) < angleThreshold) {
            return Math.signum(-delta) * LOWSPEED;
        } else {
            return Math.signum(-delta) * HIGHSPEED;
        }
    }

    @Override
    public void periodic() {
        super.periodic();

        driverStationDisabledBuff.addLast(DriverStation.isDisabled());

        if (!driverStationDisabledBuff.getFirst() && driverStationDisabledBuff.getLast()) {
            hasAppliedOperatorPerspective = false;
        }

        /* Periodically try to apply the operator perspective */
        /* If we haven't applied the operator perspective before, then we should apply it regardless of DS state */
        /* This allows us to correct the perspective in case the robot code restarts mid-match */
        /* Otherwise, only check and apply the operator perspective if the DS is disabled */
        /* This ensures driving behavior doesn't change until an explicit disable event occurs during testing*/
        if (!hasAppliedOperatorPerspective) {
            DriverStation.getAlliance().ifPresent((allianceColor) -> {
                var newPose = (allianceColor == DriverStation.Alliance.Red) ? MirrorPoses.mirror(new Pose2d()) : new Pose2d();
                resetOdometry(newPose);
                var teamColor = allianceColor == DriverStation.Alliance.Red ? "Red" : "Blue";
                System.out.println("Alliance color detected: " + teamColor);
                hasAppliedOperatorPerspective = true;
            });
        }

//        for (int i = 0; i < moduleSub.length; i++) {
//            DoubleSubscriber sub = moduleSub[i];
//            double value = sub.get();
//            if (value != prev[i]) {
//                prev[i] = value;  // save previous value
//                configCANCoder(encoders[i], value);
//            }
//        }

        // Returns the current pitch value (in degrees, from -180 to 180) reported by the sensor.
        // Pitch is a measure of rotation around the X Axis. Why do we need this information?
//        SmartDashboard.putNumber("Robot Pitch Angle", sim_gyro.getPitch());

        SmartDashboard.putNumber("SwerveModule1 Angle", backRightEncoder.getAbsolutePosition().getValue());
        SmartDashboard.putNumber("SwerveModule2 Angle", backLeftEncoder.getAbsolutePosition().getValue());
        SmartDashboard.putNumber("SwerveModule3 Angle", frontLeftEncoder.getAbsolutePosition().getValue());
        SmartDashboard.putNumber("SwerveModule4 Angle", frontRightEncoder.getAbsolutePosition().getValue());

        //double[] visionPose = NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(new double[6]);

        // aprilTagOdometryUpdater.update(poseEstimator, this);
    }
}
