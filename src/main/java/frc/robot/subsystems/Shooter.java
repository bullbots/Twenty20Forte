// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

private static final WPI_CANSparkMax m_shooterMotorLeft = new WPI_CANSparkMax(Constants.Motor.SHOOTING_LEFT, MotorType.kBrushless);
private static final WPI_CANSparkMax m_shooterMotorRight = new WPI_CANSparkMax(Constants.Motor.SHOOTING_RIGHT, MotorType.kBrushless);  
}
