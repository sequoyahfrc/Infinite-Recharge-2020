package frc.robot.handlers;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.util.RequiresTankDrive;

public class LimeLightHandler extends RequiresTankDrive implements IRobotEventHandler {

  private final XboxController driver1;

  public LimeLightHandler(DifferentialDrive robot, XboxController driver1) {
    super(robot);
    this.driver1 = driver1;
  }

  @Override
  public void teleopPeriodic() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    if (driver1.getAButton()) {
      final double Kp = -0.1;
      getRobot().tankDrive(tx * Kp, -tx * Kp);
    }
  }

}