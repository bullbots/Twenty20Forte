// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sensors;


import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;

public class NavX extends AHRS {
    /**
     * Returns the angle in degrees.
     * @return angle
     */
    public NavX(I2C.Port port){
        super(port);
    }
    public NavX(SPI.Port port){
        super(port);
    }

    public NavX(SerialPort.Port serial_port_id) {
        super(serial_port_id);
    }
    public NavX(){
        super();
    }
    public double getDegrees() {
            return getAngle();
    }

    /**
     * Returns the angle in degrees bound between 0 and 360.
     * @return angle
     */
    public double getDegreesLooped() {
        double angle = getDegrees() % 360.;
        angle = angle < 0 ? angle + 360 : angle;
        return angle;
    }

    /**
     * Returns the angle in radians.
     * @return angle
     */
    public double getRadians() {
        return Math.toRadians(getAngle());
    }

    /**
     * Returns the angle in radians bound between 0 and 2 PI.
     * @return angle
     */
    public double getRadiansLooped() {
        double angle = getRadians() % (2*Math.PI);
        angle = angle < 0 ? angle + (2*Math.PI) : angle;
        return angle;
    }
}
