// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class Motors {
    public static final int INTAKE_FRONT = 12;
    public static final int BELT = 10;
    public static final int STAGER = 20;
    public static final int SHOOTER_LEFT = 8;
    public static final int SHOOTER_RIGHT = 9;

    public static final int SLIDER = 14;

    
  }
  
  public static final class Sensors {
    public static final int INTAKE_SENSOR = 0;
  }

  public static final class Drivetrain{
    public static final int FRONT_LEFT_DRIVE_CHANNEL = 3;
    public static final int FRONT_LEFT_STEER_CHANNEL = 3;

    public static final int FRONT_RIGHT_DRIVE_CHANNEL = 4;
    public static final int FRONT_RIGHT_STEER_CHANNEL = 4;

    public static final int BACK_LEFT_DRIVE_CHANNEL = 2;
    public static final int BACK_LEFT_STEER_CHANNEL = 2;

    public static final int BACK_RIGHT_DRIVE_CHANNEL = 1;
    public static final int BACK_RIGHT_STEER_CHANNEL = 1;

    public static final int FRONT_LEFT_CANCODER_CHANNEL = 3;
    public static final int FRONT_RIGHT_CANCODER_CHANNEL = 4;

    public static final int BACK_LEFT_CANCODER_CHANNEL = 2;
    public static final int BACK_RIGHT_CANCODER_CHANNEL = 1;
    // ENCODER OFFSETS
    public static final double FRONT_LEFT_ENCODER_OFFSET = -22.1484375;
    public static final double FRONT_RIGHT_ENCODER_OFFSET = -308.4082;
    public static final double BACK_LEFT_ENCODER_OFFSET = -214.541016;
    public static final double BACK_RIGHT_ENCODER_OFFSET = -199.511719;
  }
  
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kCopilotControllerPort = 1;
  }
}