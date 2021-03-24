package frc.robot.util;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public abstract class RequiresTankDrive {

  private final DifferentialDrive robot;

  public RequiresTankDrive(DifferentialDrive robot) {
    this.robot = robot;

  }

  protected final DifferentialDrive getRobot() {
    return robot;
  }
  
}