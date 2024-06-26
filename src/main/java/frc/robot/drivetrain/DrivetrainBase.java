// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.sensors.NavX;

/** Drivetrain base. */
public abstract class DrivetrainBase extends SubsystemBase {
    protected final Field2d field = new Field2d();

    protected final DrivetrainConfig config;
    protected final NavX gyro;

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
