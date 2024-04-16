// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Leds;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Leds;

public class SolidColor extends InstantCommand {

  int lengthOfLeds;
  Leds ledss;
  int r;
  int b;
  int g;

  public SolidColor(Leds leds, int ledLength, int red, int green, int blue) {
    lengthOfLeds = ledLength;

    ledss = leds;

    r = red;
    b = blue;
    g = green;

    addRequirements(leds);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    for (int i = 0; i < lengthOfLeds; i++) {
      ledss.setLedColor(i, r, b, g);
    }
    System.out.println("finishing solid color");
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
