// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.drivetrain;

import java.util.function.Consumer;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.Kinematics;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.sensors.NavX;
import frc.robot.subsystems.DriveTrain;

/** Drivetrain base. */
public abstract class DrivetrainBase extends SubsystemBase {
    protected final Field2d field = new Field2d();

    protected final DrivetrainConfig config;
    protected final NavX gyro;
    protected final SwerveDriveKinematics kinematics;
        
    };

    DriveTrain m_DriveTrain = new DriveTrain();
      /** Creates a new Drivetrain. */
    public DrivetrainBase(
        ShuffleboardTab shuffleboardTab,
        DrivetrainConfig config,
        NavX gyro
    ) {
        super();
        this.config = config;
        // gyro.reset();
        this.gyro = gyro;

        SmartDashboard.putData("Robot (Field2d)", field);
        // field.setRobotPose(getPose2d());
        AutoBuilder.configureHolonomic(
        this::getPose2d, // Robot pose supplier
        this::resetOdometry, // Method to reset odometry (will be called if your auto has a starting pose)
        ()-> {kinematics.toChassisSpeeds();}, // Current ChassisSpeeds supplier
        //needs a consumer<ChassisSpeeds>, should fix the errors but not sure how that is applied into our robot.
        new  HolonomicPathFollowerConfig(DriveTrain.maxMetersPerSecond, .4, new ReplanningConfig()), // Default path replanning config. See the API for the options here
        () -> {
          // Boolean supplier that controls when the path will be mirrored for the red
          // alliance
          // This will flip the path being followed to the red side of the field.
          // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

          var alliance = DriverStation.getAlliance();
          if (alliance.isPresent()) {
            return alliance.get() == DriverStation.Alliance.Red;
          }
          return false;
        },
        this // Reference to this subsystem to set requirements
    );
    }

    public DrivetrainConfig getConfig() {
        return config;
    }

    public abstract Pose2d getPose2d();

    /**
     * Stops all drive motors.
     */
    public abstract void stop();

    /**
     * Sets the gyro angle to 0.
     */
    public void resetGyro() {
        gyro.reset();
        gyro.setAngleAdjustment(0);
    }

    /**
     * Sets the position of the odometry to 0, while maintaining the angle read from the gyro.
     */
    public void resetOdometry() {
        resetOdometry(new Pose2d());
    }

    /**
     * Sets the position of the odometry to the given {@link Pose2d}, while maintaining the angle read from the gyro.
     * @param pose2d
     */
    public abstract void resetOdometry(Pose2d pose2d);

    public abstract void updateOdometry();

    @Override
    public void periodic() {
        updateOdometry();
        field.setRobotPose(getPose2d());
    }
}
