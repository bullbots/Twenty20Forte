// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.hardware.DeviceIdentifier;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.motors.WPI_CANSparkMax;

public class Shooter extends SubsystemBase {

    public boolean stagedInShooter = false;

    private static final TalonFX m_shooterMotorTop = new TalonFX(Constants.Motors.SHOOTER_LEFT);
    private static final TalonFX m_shooterMotorBottom = new TalonFX(Constants.Motors.SHOOTER_RIGHT);
    private final CurrentLimitsConfigs limitsConfigs = new CurrentLimitsConfigs().withSupplyCurrentLimit(100).withStatorCurrentLimitEnable(true);
    private final TalonFXConfigurator topShooterConfig;
    private final TalonFXConfigurator bottomShooterConfig;
    public Shooter(){
        topShooterConfig = new TalonFXConfigurator(new DeviceIdentifier(Constants.Motors.SHOOTER_LEFT, "", ""));
        bottomShooterConfig = new TalonFXConfigurator(new DeviceIdentifier(Constants.Motors.SHOOTER_RIGHT, "", ""));
        topShooterConfig.apply(limitsConfigs);
        bottomShooterConfig.apply(limitsConfigs);

        m_shooterMotorTop.setNeutralMode(NeutralModeValue.Brake);
        m_shooterMotorBottom.setNeutralMode(NeutralModeValue.Brake);
    }

    public void set(double speed) {
        m_shooterMotorTop.set(-speed);
        m_shooterMotorBottom.set(-speed);
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
        set(0.35);
    }

    public void stop() {
        set(0);
    }

    public boolean isEnabled() {
        return m_shooterMotorTop.get() != 0;
    }
}