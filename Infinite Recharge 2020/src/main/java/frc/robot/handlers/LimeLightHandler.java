package frc.robot.handlers;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.util.RequiresTankDrive;

public class LimeLightHandler extends RequiresTankDrive implements IRobotEventHandler {

  private NetworkTable table;
  private final XboxController driver1;

  public LimeLightHandler(DifferentialDrive robot, XboxController driver1) {
    super(robot);
    this.driver1 = driver1;
  }

  @Override
  public void robotInit() {
    table = NetworkTableInstance.getDefault().getTable("limelight");
    if (table != null) {
      // For some reason, the limelight's lights will blink whenever it
      // finds a target.
      // ledMode = 3 -> "force on" 
      table.getEntry("ledMode").setNumber(3);
    }
  }

  @Override
  public void teleopPeriodic() {
    if (table == null) {
      robotInit(); // Intilization failed, try again
      return;
    }
    double tx = table.getEntry("tx").getDouble(0);
    final double Kp = 0.1;
    if (driver1.getAButton()) {
      getRobot().tankDrive(tx * Kp, tx * Kp * -1);
    }
  }

}