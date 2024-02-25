// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.drivetrain;


import edu.wpi.first.math.kinematics.ChassisSpeeds;

/** Drivetrain base for any drivetrain with holonomic movement. */
public interface HolonomicDrivetrain {

    /**
     * Primary method of controlling the robot.
     * 
     * @param xSpeed forward speed
     * @param ySpeed leftward speed
     * @param rot rotational speed
     * @param fieldRelative field or robot relative
     */
    public void holonomicDrive(double xSpeed, double ySpeed, double rot, boolean fieldRelative);

    public void fromChassisSpeeds(ChassisSpeeds speeds);
}
