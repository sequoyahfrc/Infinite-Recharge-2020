package frc.robot.handlers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.util.RequiresTankDrive;

public class TankDriveHandler extends RequiresTankDrive implements IRobotEventHandler {

  private final XboxController driver2;

  public TankDriveHandler(DifferentialDrive robot, XboxController driver2) {
    super(robot);
    this.driver2 = driver2;
  }

  @Override
  public void teleopPeriodic() {
    //drive
    double left = driver2.getY(Hand.kLeft);
    double right = driver2.getY(Hand.kRight);
    //square inputs so they arent touchy
    boolean lneg = left < 0;
    boolean rneg = right < 0;
    left *= left * (lneg ? -1 : 1);
    right *= right * (rneg ? -1 : 1);
    getRobot().tankDrive(left, right, false);
  }  
}