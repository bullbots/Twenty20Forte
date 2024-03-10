// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.motors.WPI_CANSparkMax;

public class Shooter extends SubsystemBase {

    public boolean stagedInShooter = false;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

    private static final WPI_CANSparkMax m_shooterMotorLeft = new WPI_CANSparkMax(Constants.Motors.SHOOTER_LEFT,
            MotorType.kBrushless);
    private static final WPI_CANSparkMax m_shooterMotorRight = new WPI_CANSparkMax(Constants.Motors.SHOOTER_RIGHT,
            MotorType.kBrushless);

    public Shooter() {
        configureShooterMotor(m_shooterMotorLeft);
        configureShooterMotor(m_shooterMotorRight);
    }
    
    private void configureShooterMotor(WPI_CANSparkMax motor) {
        motor.restoreFactoryDefaults();
        motor.setSmartCurrentLimit(100);
//        motor.setIdleMode(IdleMode.kBrake);

        var pidController = motor.getPIDController();

        // PID coefficients
        kP = 6e-5;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0.000015;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 5700;

        // set PID coefficients
        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
        pidController.setFF(kFF);
        pidController.setOutputRange(kMinOutput, kMaxOutput);
    }

    public void set(double normalizedSpeed) {
        var setPoint = normalizedSpeed * maxRPM;

        m_shooterMotorLeft.getPIDController().setReference(setPoint, CANSparkMax.ControlType.kVelocity);
        m_shooterMotorRight.getPIDController().setReference(-setPoint, CANSparkMax.ControlType.kVelocity);
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
}