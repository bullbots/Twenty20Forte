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
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.drivetrain.*;
import frc.robot.motors.WPI_CANSparkMax;
import frc.robot.sensors.SimNavX;


public class DriveTrain extends SwerveDrivetrain {

    private static final double TRANSLATIONAL_TOLERANCE = .02;
    private static final double ROTATIONAL_TOLERANCE = Math.toRadians(1);

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

        sim_gyro.setAngleAdjustment(0);
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

        sim_gyro.reset();
        if (Robot.isRedAlliance()) {
            resetOdometry(MirrorPoses.mirror(getPose2d()));
        } else {
            resetOdometry();
        }

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
    public void periodic() {
        super.periodic();
        for (int i = 0; i < moduleSub.length; i++) {
            DoubleSubscriber sub = moduleSub[i];
            double value = sub.get();
            if (value != prev[i]) {
                prev[i] = value;  // save previous value
                //configCANCoder(encoders[i], value);
            }
        }
        SmartDashboard.putNumber("robot pitch angle", sim_gyro.getPitch());
        SmartDashboard.putNumber("SwerveModule1 angle", frontRightEncoder.getAbsolutePosition().getValue());
        SmartDashboard.putNumber("SwerveModule2 angle", frontLeftEncoder.getAbsolutePosition().getValue());
        SmartDashboard.putNumber("SwerveModule3 angle", backLeftEncoder.getAbsolutePosition().getValue());
        SmartDashboard.putNumber("SwerveModule4 angle", backRightEncoder.getAbsolutePosition().getValue());
        //System.out.println("Gyro pitch: " + gyro.getPitch());
        //double[] visionPose = NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(new double[6]);

        // aprilTagOdometryUpdater.update(poseEstimator, this);

        // log the current
        //m_CurrentBLLogger.logEntry(backLeftDriveFalcon.getSupplyCurrent(), BullLogger.LogLevel.DEBUG);
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
}
