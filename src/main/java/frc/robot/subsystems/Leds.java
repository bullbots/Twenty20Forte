// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Leds extends SubsystemBase {
  /** Creates a new Leds. */
  private static AddressableLED led = new AddressableLED(0);
  private static AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(40);

  public Leds() {
    led.setLength(ledBuffer.getLength());

    for (int i = 0; i < ledBuffer.getLength(); i++) {
      ledBuffer.setRGB(i, 255, 255, 255);
    }

    led.setData(ledBuffer);
    led.start();
  }

  public void setLedColor(int ledNumber, int red, int blue, int green) {

    ledBuffer.setRGB(ledNumber, blue, red, green);

    led.setData(ledBuffer);
    
  }
}
