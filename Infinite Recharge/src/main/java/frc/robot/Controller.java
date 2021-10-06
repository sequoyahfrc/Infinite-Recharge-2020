package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Controller {
  private final JoystickButton xButton, yButton, aButton, bButton, leftBumper, rightBumper;

  public Controller(XboxController c) {
    xButton = new JoystickButton(c, XboxController.Button.kX.value);
    yButton = new JoystickButton(c, XboxController.Button.kY.value);
    aButton = new JoystickButton(c, XboxController.Button.kA.value);
    bButton = new JoystickButton(c, XboxController.Button.kB.value);
    // Bumpers
    leftBumper = new JoystickButton(c, XboxController.Button.kBumperLeft.value);
    rightBumper = new JoystickButton(c, XboxController.Button.kBumperRight.value);
  }

  public JoystickButton getRightBumper() {
    return rightBumper;
  }

  public JoystickButton getLeftBumper() {
    return leftBumper;
  }

  public JoystickButton getBButton() {
    return bButton;
  }

  public JoystickButton getAButton() {
    return aButton;
  }

  public JoystickButton getYButton() {
    return yButton;
  }

  public JoystickButton getXButton() {
    return xButton;
  }
}