package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;

/*
While the ControllerSubsystem is a Subsystem,
each subsystem cann only be used by one command
at a time. If two commands need the Controllers and
this class is a a SubsystemBase passed to addRequirements(),
then only one command will run.

TLDR:
  - DO NOT UNCOMMENT "extends SubsystemBase"!!!!!!!!
  - DO NOT PASS THIS TO addRequirements()!!!!!!!!!!!!!
OR ELSE THINGS *WILL* BREAK!
*/
public class ControllerSubsystem /*extends SubsystemBase*/{

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