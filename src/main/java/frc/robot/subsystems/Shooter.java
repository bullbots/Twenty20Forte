// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.motors.WPI_CANSparkMax;

public class Shooter extends SubsystemBase {

private static final WPI_CANSparkMax m_shooterMotorLeft = new WPI_CANSparkMax(Constants.Motors.SHOOTING_LEFT, MotorType.kBrushless);
private static final WPI_CANSparkMax m_shooterMotorRight = new WPI_CANSparkMax(Constants.Motors.SHOOTING_RIGHT, MotorType.kBrushless); 

int m_MotorDirection = 0;


public void start() {
    m_shooterMotorLeft.set(1);
    m_shooterMotorRight.set(-1);
}

public void stop() {
    m_shooterMotorLeft.set(0);
    m_shooterMotorRight.set(0);
}
/**
 * Sets the direction of the front intake motor
 * 
 * @param direction 0 is forward, 1 is backward
 */
public void setDirection(int direction) {
    m_MotorDirection = direction;
}

public int getDirection() {
    return m_MotorDirection;
}
public boolean isEnabled(){
    return m_shooterMotorLeft.get()!=0;
}
}