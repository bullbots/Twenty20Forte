// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Shooter extends SubsystemBase {

    public boolean stagedInShooter = false;

    private static final TalonFX m_shooterMotorTop = new TalonFX(Constants.Motors.SHOOTER_LEFT);
    private static final TalonFX m_shooterMotorBottom = new TalonFX(Constants.Motors.SHOOTER_RIGHT);

    private final VelocityVoltage m_voltageVelocity = new VelocityVoltage(0, 0, true, 0, 0, false, false, false);

    public Shooter() {
        configureMotor(m_shooterMotorTop);
        configureMotor(m_shooterMotorBottom);
    }

    private void configureMotor(TalonFX motor) {
        var toConfigure = new TalonFXConfiguration();

        var motorOutputConfig = new MotorOutputConfigs();
        motorOutputConfig.NeutralMode = NeutralModeValue.Brake;
        toConfigure.MotorOutput = motorOutputConfig;

        // Uncomment to get current limiting
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

        // Uncomment to get Velocity Closed Loop control.
//        /* Voltage-based velocity requires a feed forward to account for the back-emf of the motor */
//        toConfigure.Slot0.kP = 0.11; // An error of 1 rotation per second results in 2V output
//        toConfigure.Slot0.kI = 0.5; // An error of 1 rotation per second increases output by 0.5V every second
//        toConfigure.Slot0.kD = 0.0001; // A change of 1 rotation per second squared results in 0.01 volts output
//        toConfigure.Slot0.kV = 0.12; // Falcon 500 is a 500kV motor, 500rpm per V = 8.333 rps per V, 1/8.33 = 0.12 volts / Rotation per second
//        // Peak output of 8 volts
//        toConfigure.Voltage.PeakForwardVoltage = 8;
//        toConfigure.Voltage.PeakReverseVoltage = -8;

        /* Retry config apply up to 5 times, report if failure */
        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = motor.getConfigurator().apply(toConfigure);
            if (status.isOK()) break;
        }
        if(!status.isOK()) {
            System.out.println("Could not apply configs, error code: " + status);
        }
    }

    public void set(double speed) {
        m_shooterMotorTop.set(-speed);
        m_shooterMotorBottom.set(-speed);

        // Uncomment to get Velocity Closed Loop control.
//        double desiredRotationsPerSecond = speed * 50; // Go for plus/minus 10 rotations per second
//        m_shooterMotorTop.setControl(m_voltageVelocity.withVelocity(-desiredRotationsPerSecond));
//        m_shooterMotorBottom.setControl(m_voltageVelocity.withVelocity(desiredRotationsPerSecond));
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

    public void burritoShoot() {
        set(-0.10);
    }

    public void bumpShooter() {
        set(0.35);
    }

    public void stop() {
        set(0);
    }

    public boolean isEnabled() {
        return m_shooterMotorTop.get() != 0;
    }
}