// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.motors.WPI_CANSparkMax;

public class Shooter extends SubsystemBase {

    public boolean stagedInShooter = false;

    private static final WPI_CANSparkMax m_shooterMotorLeft = new WPI_CANSparkMax(Constants.Motors.SHOOTER_LEFT,
            MotorType.kBrushless);
    private static final WPI_CANSparkMax m_shooterMotorRight = new WPI_CANSparkMax(Constants.Motors.SHOOTER_RIGHT,
            MotorType.kBrushless);

    public Shooter(){
        m_shooterMotorLeft.setSmartCurrentLimit(40);
        m_shooterMotorRight.setSmartCurrentLimit(40);
        m_shooterMotorLeft.setIdleMode(IdleMode.kBrake);
        m_shooterMotorRight.setIdleMode(IdleMode.kBrake);
    }

    public void set(double speed) {
        m_shooterMotorLeft.set(speed);
        m_shooterMotorRight.set(-speed);
    }

    public void speakerShoot() {
        set(1.0);
    }

    public void ampShoot() {
        set(-0.75);
    }

    public void stageShoot() {
        set(0.10);
    }

    public void bumpShooter(){
        set(0.30);
    }

    public void stop() {
        set(0);
    }

    public boolean isEnabled() {
        return m_shooterMotorLeft.get() != 0;
    }
}