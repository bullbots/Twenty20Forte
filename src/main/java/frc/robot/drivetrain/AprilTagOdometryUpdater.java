// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.drivetrain;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.CircularBuffer;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

/** Add your docs here. */
public class AprilTagOdometryUpdater {

    int visionPeriodicTicker = 0;
    int staleVisionTicker = 0;
    CircularBuffer<Double> estimatePoseBuffer;

    public AprilTagOdometryUpdater() {
        estimatePoseBuffer = new CircularBuffer<Double>(8);
    }

    public void update(SwerveDrivePoseEstimator poseEstimator, SwerveDrivetrain drivetrain) {
        if (Robot.isSimulation()) {
            return;
        }
        boolean hasTarget = NetworkTableInstance.getDefault().getTable("limelight-limea").getEntry("tv")
                .getDouble(0) == 1;
        if (hasTarget) {
            double[] visionPose = NetworkTableInstance.getDefault().getTable("limelight-limea")
                    .getEntry("botpose_wpiblue").getDoubleArray(new double[6]);
            // estimatePoseBuffer.add(new Pose2d(visionPose[0], visionPose[1], new
            // Rotation2d(Math.toRadians(visionPose[5]))));
            double sum = 0;
            for (int i = 0; i < estimatePoseBuffer.size(); i++) {
                if (estimatePoseBuffer.get(i) != Double.MAX_VALUE)
                    sum += estimatePoseBuffer.get(i);
            }
            // count number of used entries in buffer
            int bufferSize = estimatePoseBuffer.size();
            for (int i = 0; i < estimatePoseBuffer.size(); i++) {
                if (estimatePoseBuffer.get(i) == Double.MAX_VALUE) {
                    bufferSize--;
                }
            }
            // get average
            double averageY = sum / bufferSize;
            if (Math.abs(averageY - visionPose[1]) < 1 || bufferSize == 0) {
                estimatePoseBuffer.addFirst(visionPose[1]);
            } else {
                // CameraLoging.logEntry(String.format("rejected estimate dif of: %f buffer
                // size: %d%n",(averageY - visionPose[1]), bufferSize));

                System.out.printf("rejected estimate dif of: %f buffer size: %d%n", (averageY - visionPose[1]),
                        bufferSize);
            }
            // if the the buffer has enough entries send it
            if (bufferSize >= 6) {
                poseEstimator.addVisionMeasurement(
                        new Pose2d(visionPose[0], visionPose[1], drivetrain.getPose2d().getRotation()),
                        Timer.getFPGATimestamp());
            }
            staleVisionTicker++;
            // System.out.println("buffer size: " + bufferSize);
        } else {
            // clear buffer if there are no targets for more than 10 frames
            visionPeriodicTicker++;
            if (visionPeriodicTicker > 10) {
                visionPeriodicTicker = 0;
                // reset the buffer by setting it to max value
                for (int i = 0; i < estimatePoseBuffer.size(); i++) {
                    estimatePoseBuffer.addFirst(Double.MAX_VALUE);
                }
                // System.out.println("cleared vision buffer");
            }
        }
        if (staleVisionTicker > 10) {
            staleVisionTicker = 0;
            for (int i = 0; i < estimatePoseBuffer.size(); i++) {
                estimatePoseBuffer.addFirst(Double.MAX_VALUE);
            }
            // System.out.println("cleared vision buffer");
        }

    }

}
