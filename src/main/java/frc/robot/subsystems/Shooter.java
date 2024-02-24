// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.motors.WPI_CANSparkMax;

public class Shooter extends SubsystemBase {

    private static final WPI_CANSparkMax m_shooterMotorLeft = new WPI_CANSparkMax(Constants.Motors.SHOOTING_LEFT,
            MotorType.kBrushless);
    private static final WPI_CANSparkMax m_shooterMotorRight = new WPI_CANSparkMax(Constants.Motors.SHOOTING_RIGHT,
            MotorType.kBrushless);

    private void set(double speed) {
        m_shooterMotorLeft.set(speed);
        m_shooterMotorRight.set(-speed);
    }

    public void speakerShoot() {
        set(0.5);
    }

    public void stop() {
        set(0);
    }

    public boolean isEnabled() {
        return m_shooterMotorLeft.get() != 0;
    }
}