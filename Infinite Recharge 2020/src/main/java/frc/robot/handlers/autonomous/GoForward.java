package frc.robot.handlers.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.handlers.IRobotEventHandler;
import frc.robot.util.RequiresTankDrive;

public class GoForward extends RequiresTankDrive implements IRobotEventHandler {
  
  private final Timer goForwardTimer = new Timer();
  private boolean wentForward = false;

  public GoForward(DifferentialDrive robot) {
    super(robot);
  }

  @Override
  public void disabledInit() {
    goForwardTimer.stop();
    goForwardTimer.reset();
  }

  @Override
  public void autonomousInit() {
    if (goForwardTimer.get() != 0) {
      goForwardTimer.stop();
      goForwardTimer.reset();
    }
    if (!wentForward) {
      goForwardTimer.start();
    }
  }

  @Override
  public void autonomousPeriodic() {
    if (goForwardTimer.hasPeriodPassed(1)) {
      getRobot().stopMotor();
      wentForward = true;
    } else if (!wentForward) {
      getRobot().tankDrive(-0.7, -0.7);
    }
  }
}