package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ControllerSubsystem extends SubsystemBase {

  private final XboxController driver1;
  private final XboxController driver2;

  public ControllerSubsystem() {
    driver1 = new XboxController(0);
    driver2 = new XboxController(1);
  }

  public XboxController getDriver1() {
    return driver1;
  }

  public XboxController getDriver2() {
    return driver2;
  }
}