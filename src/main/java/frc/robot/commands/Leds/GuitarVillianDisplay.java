// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Leds;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Leds;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class GuitarVillianDisplay extends InstantCommand {
  private Leds leds;
  private boolean top;
  private boolean bottom;
  private int[] displays;
  public GuitarVillianDisplay(Leds _leds, int[] data, boolean topPositive, boolean bottomPositive) {
    addRequirements(_leds);
    leds = _leds;
    top = topPositive;
    bottom = bottomPositive;
    displays = data;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    for (int i = 0; i < 20; i++){
      int item = displays[i];
      switch (item){
      case 0:
        leds.setLedColor((top) ? i : 19-i, 0, 0, 0);
        leds.setLedColor((bottom) ? i+20 : 39-i, 0, 0, 0);
        break;
      case 1:
        leds.setLedColor((top) ? i : 19-i, 0, 255, 0);
        leds.setLedColor((bottom) ? i+20 : 39-i, 0, 255, 0);
        break;
      case 2:
        leds.setLedColor((top) ? i : 19-i, 255, 0, 0);
        leds.setLedColor((bottom) ? i+20 : 39-i, 255, 0, 0);
        break;
      case 3:
        leds.setLedColor((top) ? i : 19-i, 255, 255, 0);
        leds.setLedColor((bottom) ? i+20 : 39-i, 255, 255, 0);
        break;
      case 4:
        leds.setLedColor((top) ? i : 19-i, 0, 0, 255);
        leds.setLedColor((bottom) ? i+20 : 39-i, 0, 0, 255);
        break;
      case 5:
        leds.setLedColor((top) ? i : 19-i, 255, 127, 0);
        leds.setLedColor((bottom) ? i+20 : 39-i, 255, 127, 0);
        break;
      case 9:
        leds.setLedColor((top) ? i : 19-i, 255, 255, 255);
        leds.setLedColor((bottom) ? i+20 : 39-i, 255, 255, 255);
        break;
      case 8:
        leds.setLedColor((top) ? i : 19-i, 127, 0, 0);
        leds.setLedColor((bottom) ? i+20 : 39-i, 127, 0, 0);
        break;
      }
    }
  }
}
