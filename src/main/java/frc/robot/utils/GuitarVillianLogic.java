package frc.robot.utils;

import java.util.Arrays;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.Constants.OperatorConstants;

public class GuitarVillianLogic {
  private static int[] data;
  private static final CommandJoystick m_guitarHero = new CommandJoystick(OperatorConstants.kDriverControllerPort);
  private static enum Mode{
    LOSE,
    NEW_LEVEL,
    NORMAL
  }
  private static Mode state;
  private static double level;
  private static int spawned;
  private static boolean inLevel;
  public static void initialize(){
    data = new int[20];
    data[19] = 8;
    level = 40;
    spawned = 15;
    inLevel = false;
    state = Mode.NEW_LEVEL;
  }

  public static void update(){ 
    if (inLevel){
      state = Mode.NORMAL;
      if (spawned > 18){
        switch(data[18]){
        case 0:
          if (m_guitarHero.povUp().getAsBoolean() || m_guitarHero.povDown().getAsBoolean()){
            state = Mode.LOSE;
          }
          break;
        case 1:
          if (!((m_guitarHero.povUp().getAsBoolean() || m_guitarHero.povDown().getAsBoolean())&& m_guitarHero.button(4).getAsBoolean())){
            state = Mode.LOSE;
          }
          break;
        case 2:
          if (!((m_guitarHero.povUp().getAsBoolean() || m_guitarHero.povDown().getAsBoolean())&& m_guitarHero.button(3).getAsBoolean())){
            state = Mode.LOSE;
          }
          break;
        case 3:
          if (!((m_guitarHero.povUp().getAsBoolean() || m_guitarHero.povDown().getAsBoolean())&& m_guitarHero.button(2).getAsBoolean())){
            state = Mode.LOSE;
          }
          break;
        case 4:
          if (!((m_guitarHero.povUp().getAsBoolean() || m_guitarHero.povDown().getAsBoolean())&& m_guitarHero.button(1).getAsBoolean())){
            state = Mode.LOSE;
          }
          break;
        case 5:
          if (!((m_guitarHero.povUp().getAsBoolean() || m_guitarHero.povDown().getAsBoolean())&& m_guitarHero.button(7).getAsBoolean())){
            state = Mode.LOSE;
          }
          break;
      }
      }
    } else {
      if (state != Mode.LOSE) {
        state = Mode.NEW_LEVEL;
        switch(data[18]){
        case 0:
          if (m_guitarHero.povUp().getAsBoolean() || m_guitarHero.povDown().getAsBoolean()){
            state = Mode.LOSE;
          }
          break;
        case 1:
          if (!((m_guitarHero.povUp().getAsBoolean() || m_guitarHero.povDown().getAsBoolean())&& m_guitarHero.button(4).getAsBoolean())){
            state = Mode.LOSE;
          }
          break;
        case 2:
          if (!((m_guitarHero.povUp().getAsBoolean() || m_guitarHero.povDown().getAsBoolean())&& m_guitarHero.button(3).getAsBoolean())){
            state = Mode.LOSE;
          }
          break;
        case 3:
          if (!((m_guitarHero.povUp().getAsBoolean() || m_guitarHero.povDown().getAsBoolean())&& m_guitarHero.button(2).getAsBoolean())){
            state = Mode.LOSE;
          }
          break;
        case 4:
          if (!((m_guitarHero.povUp().getAsBoolean() || m_guitarHero.povDown().getAsBoolean())&& m_guitarHero.button(1).getAsBoolean())){
            state = Mode.LOSE;
          }
          break;
        case 5:
          if (!((m_guitarHero.povUp().getAsBoolean() || m_guitarHero.povDown().getAsBoolean())&& m_guitarHero.button(7).getAsBoolean())){
            state = Mode.LOSE;
          }
          break;
      }
      }
    }
    switch (state){
    case NORMAL:
      if (spawned >= 60-level){
        inLevel = false;
        spawned = 0;
      }
      for (int i = 18; i > 0; i--){
        data[i] = data[i-1];
      }
      data[0] = (int)(Math.random() * 6);
      spawned++;
      break;
    case NEW_LEVEL:
      if (spawned >= 20){
        inLevel = true;
        spawned = 0;
        level--;
      }
      for (int i = 18; i > 0; i--){
        data[i] = data[i-1];
      }
      data[0] = (spawned < 17) ? 0 : 9;
      spawned++;
      break;
    case LOSE:
      inLevel = false;
      level = 5;
      for (int i = 0; i < 19; i++){
        if (data[i+1] == 8) {
          data[i] = 8;
        }
      }
      break;
    }

  }

  public static int[] getData(){
    return data;
  }
  public static double getLevel(){
    return level;
  }
}
