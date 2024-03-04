// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lifter extends SubsystemBase {
    private final TalonFX m_liftMotor;
    private int m_Direction = 1;

    private static final double LIMIT = 16000.0;

    public Lifter(int motorID) {
        m_liftMotor = new TalonFX(motorID);
        m_liftMotor.setNeutralMode(NeutralModeValue.Brake);
        var cfg = m_liftMotor.getConfigurator();
        var toApply = new TalonFXConfiguration();
        toApply.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        toApply.SoftwareLimitSwitch.ReverseSoftLimitThreshold = LIMIT;
        cfg.apply(toApply);
    }

    /**
     * Arms extending are positive, but doing a pull up is negative as arms are
     * being retracted. Use as reference while coding the two movements
     */

    // moves the arms up to reach the chain
    public void start() {
        // m_liftMotor.set(-m_Direction);
        m_liftMotor.set(0.5 * m_Direction);
    }

    // moves the robot up in the air
    public void stop() {
        m_liftMotor.set(0);
    }

    public void setDirection(int direction) {
        m_Direction = direction;
    }

    public int getDirection() {
        return m_Direction;
    }

    @Override
    public void periodic() {
        var forwardLimit = m_liftMotor.getForwardLimit();
        if (forwardLimit.getValue() == ForwardLimitValue.ClosedToGround) {
            m_liftMotor.setPosition(0);
        }
    }

}
