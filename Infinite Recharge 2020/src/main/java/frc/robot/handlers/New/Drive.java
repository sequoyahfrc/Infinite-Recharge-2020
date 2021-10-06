package frc.robot.handlers.New;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.util.RequiresTankDrive;
import frc.robot.handlers.IRobotEventHandler;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Drive extends RequiresTankDrive implements IRobotEventHandler {
  
  private final XboxController drivePrime;

  public Drive(DifferentialDrive robot, XboxController drivePrime) {
    super(robot);
    this.drivePrime=drivePrime;
  }

  @Override
  public void teleopPeriodic() {
    double l = drivePrime.getY(Hand.kLeft);
    double r =drivePrime.getY(Hand.kRight);
    boolean lneg= l<0;
    boolean rneg=r<0;
    l*=l*(lneg ? -1 : 1);
    r=r*(rneg ? -1: 1 );
    getRobot().tankDrive(l, r, false);
  }
}